package com.example.pantrywise.view

import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.staggeredgrid.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pantrywise.model.dataclass.Product
import com.example.pantrywise.model.enums.ProductCategory
import com.example.pantrywise.ui.extensions.getIcon
import com.example.pantrywise.ui.theme.PantryWiseTheme
import com.example.pantrywise.util.DateTimeHelper
import com.example.pantrywise.viewmodel.MockDataHelper
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
        },
        onMoveProductToShoppingList = { product ->
            viewModel.moveProductToShoppingList(product)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProductListViewContent(
    products: List<Product>,
    onEditProduct: (Product) -> Unit,
    onRemoveProduct: (Product) -> Unit,
    onMoveProductToShoppingList: (Product) -> Unit
) {
    val groupedProducts = products.groupBy { it.category }
    val expandedCategories = remember { mutableStateSetOf<ProductCategory>() }
    var selectedProduct by remember { mutableStateOf<Product?>(null) }

    ProductListViewWithHorizontalGridContent(
        groupedProducts = groupedProducts,
        expandedCategories = expandedCategories,
        onToggleExpanded = { category ->
            if (expandedCategories.contains(category)) {
                expandedCategories.remove(category)
            } else {
                expandedCategories.add(category)
            }
        },
        onProductSelected = { selectedProduct = it }
    )
    selectedProduct?.let {
        ProductActionMenu(
            it,
            onDismiss = { selectedProduct = null },
            onRemove = onRemoveProduct,
            onMove = onMoveProductToShoppingList,
            onEdit = onEditProduct
        )
    }

}

@Composable
fun ProductListViewWithHorizontalGridContent(
    groupedProducts: Map<ProductCategory, List<Product>>,
    expandedCategories: MutableSet<ProductCategory>,
    onToggleExpanded: (ProductCategory) -> Unit,
    onProductSelected: (Product) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(12.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
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
                        ProductsGrid(categoryProducts, onProductSelected = onProductSelected)
                    }
                }
            }
        }
    }

}

@Composable
fun ProductsGrid(products: List<Product>, onProductSelected: (Product) -> Unit) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        modifier = Modifier.fillMaxWidth().padding(8.dp).heightIn(max = 300.dp),
        contentPadding = PaddingValues(4.dp),
        verticalItemSpacing = 12.dp,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(products) { product ->
            ProductCard(
                product = product,
                onProductSelected = { onProductSelected(product) }
            )
        }
    }
}

@Composable
fun ProductCard(product: Product, onProductSelected: () -> Unit) {
    ElevatedCard(
        modifier = Modifier.combinedClickable(onClick = {}, onLongClick = onProductSelected),
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
                text = product.details,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = getProductAmountText(product),
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
    PantryWiseTheme {
        ProductListViewWithHorizontalGridContent(
            groupedProducts = MockDataHelper.getMockProductList().groupBy { it.category },
            mutableSetOf(ProductCategory.BAKERY),
            {},
            {}
        )
    }
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