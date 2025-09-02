package com.example.pantrywise.view

import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pantrywise.model.dataclass.Product
import com.example.pantrywise.model.enums.ProductCategory
import com.example.pantrywise.model.enums.ProductUnit
import com.example.pantrywise.ui.extensions.getIcon
import com.example.pantrywise.util.DateTimeHelper
import com.example.pantrywise.viewmodel.ProductListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListViewContent(
    viewModel: ProductListViewModel,
    navController: NavController
) {
    val products by viewModel.products.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    // Show error message if any
    errorMessage?.let { message ->
        Toast.makeText(navController.context, message, Toast.LENGTH_LONG).show()
        viewModel.clearError()
    }

    ProductListViewContent(
        products = products,
        onEditProduct = { product ->
            val updatedProduct = product.copy(quantity = product.quantity + 1)
            viewModel.updateProduct(updatedProduct)
        },
        onRemoveProduct = { product ->
            viewModel.deleteProduct(product)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProductListViewContent(
    products: List<Product>,
    onEditProduct: (Product) -> Unit,
    onRemoveProduct: (Product) -> Unit
) {
    val groupedProducts = products.groupBy { it.category }
    val expandedCategories = remember { mutableStateSetOf<ProductCategory>() }

    ProductListViewWithHorizontalGridContent(
        groupedProducts = groupedProducts,
        expandedCategories = expandedCategories,
        onEditProduct = onEditProduct,
        onRemoveProduct = onRemoveProduct,
        onToggleExpanded = { category ->
            if (expandedCategories.contains(category)) {
                expandedCategories.remove(category)
            } else {
                expandedCategories.add(category)
            }
        }
    )

}

@Composable
fun ProductListViewWithHorizontalGridContent(
    groupedProducts: Map<ProductCategory, List<Product>>,
    expandedCategories: MutableSet<ProductCategory>,
    onEditProduct: (Product) -> Unit,
    onRemoveProduct: (Product) -> Unit,
    onToggleExpanded: (ProductCategory) -> Unit,
    paddingValues: PaddingValues = PaddingValues(0.dp)
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        groupedProducts.forEach { (category, categoryProducts) ->
            item {
                Card(
                    modifier = Modifier.fillMaxWidth().animateContentSize(
                        spring(
                            dampingRatio = Spring.DampingRatioLowBouncy,
                            stiffness = Spring.StiffnessMediumLow
                        )
                    ),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    ProductCategoryHeader(
                        category = category,
                        productCount = categoryProducts.size,
                        isExpanded = expandedCategories.contains(category),
                        onToggleExpanded = { onToggleExpanded(category) }
                    )
                    if (expandedCategories.contains(category)) {
                        ProductsGrid(categoryProducts)
                    }
                }
            }
        }
    }
}

@Composable
fun ProductsGrid(products: List<Product>) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        modifier = Modifier.fillMaxWidth().padding(8.dp).heightIn(max = 300.dp),
        contentPadding = PaddingValues(4.dp),
        verticalItemSpacing = 12.dp,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(products) { item ->
            ProductCard(item) // Replace with your item composable
        }
    }
}


@Composable
fun ProductCard(product: Product, modifier: Modifier = Modifier) {
    ElevatedCard(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(text = product.name, style = MaterialTheme.typography.titleMedium)
            Text(
                text = "${product.quantity} ${getUnitShortLabel(product.productUnit)}",
                style = MaterialTheme.typography.bodySmall
            )
            product.expirationDate?.let {
                ExpirationDateInfo(it)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProductListPreview() {
    ProductListViewWithHorizontalGridContent(
        groupedProducts = listOf(
            Product(
                name = "Milk",
                quantity = 2.0,
                productUnit = ProductUnit.LITER,
                category = ProductCategory.BEVERAGES
            ),
            Product(
                name = "Bread",
                quantity = 1.0,
                productUnit = ProductUnit.PIECE,
                category = ProductCategory.BAKERY
            ),
            Product(
                name = "Apples",
                quantity = 6.0,
                productUnit = ProductUnit.PIECE,
                category = ProductCategory.PRODUCE
            ),
            Product(
                name = "Chicken Breast",
                quantity = 500.0,
                productUnit = ProductUnit.GRAM,
                category = ProductCategory.MEAT_SEAFOOD
            ),
            Product(
                name = "Rice",
                quantity = 1.0,
                productUnit = ProductUnit.KILOGRAM,
                category = ProductCategory.GRAINS_CEREALS
            ),
            Product(
                name = "Olive Oil",
                quantity = 250.0,
                productUnit = ProductUnit.MILLILITER,
                category = ProductCategory.OILS_FATS
            ),
            Product(
                name = "Salt",
                quantity = 100.0,
                productUnit = ProductUnit.GRAM,
                category = ProductCategory.SPICES_SEASONINGS
            ),
            Product(
                name = "Eggs",
                quantity = 12.0,
                productUnit = ProductUnit.PIECE,
                category = ProductCategory.DAIRY_EGGS
            ),
            Product(
                name = "Cheese",
                quantity = 200.0,
                productUnit = ProductUnit.GRAM,
                category = ProductCategory.DAIRY_EGGS
            ),
            Product(
                name = "Tomatoes",
                quantity = 4.0,
                productUnit = ProductUnit.PIECE,
                category = ProductCategory.PRODUCE
            )
        ).groupBy { it.category },
        mutableSetOf(ProductCategory.BAKERY),
        {},
        {},
        {}
    )
}
@Composable
fun ExpirationDateInfo(expirationTimeStamp: Long) {
    val daysToExpiration = DateTimeHelper.daysBetweenTimestampAndNow(expirationTimeStamp)
    val text = when {
        daysToExpiration < 0 -> "$daysToExpiration after expiration"
        daysToExpiration == 0 -> "Expires Today!"
        else -> "$daysToExpiration to expiration date"
    }
    Row(verticalAlignment = Alignment.CenterVertically) {
        if (daysToExpiration < 0) {
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .background(color = MaterialTheme.colorScheme.error, shape = CircleShape)
            )
            Spacer(modifier = Modifier.width(6.dp))
        }
        Text(text = text, style = MaterialTheme.typography.bodySmall)
    }
}


@Composable
fun ProductCategoryHeader(
    category: ProductCategory,
    productCount: Int,
    isExpanded: Boolean,
    onToggleExpanded: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onToggleExpanded() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Category icon (smaller and more subtle)
        Icon(
            imageVector = category.getIcon(),
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = MaterialTheme.colorScheme.onPrimaryContainer
        )

        Spacer(modifier = Modifier.width(12.dp))

        // Category name and count
        Column(
            modifier = Modifier.weight(1f)
        ) {
            val productsCountText = " [$productCount]"
            Text(
                text = stringResource(category.labelResId) + productsCountText,
                style = MaterialTheme.typography.titleMedium
            )
        }

        // Expand/collapse icon
        Icon(
            imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
            contentDescription = if (isExpanded) "Collapse" else "Expand",
            tint = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}

@Composable
fun getUnitShortLabel(unit: ProductUnit): String {
    return stringResource(unit.shortLabelResId ?: unit.labelResId)
}