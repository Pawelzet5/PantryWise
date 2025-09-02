package com.example.pantrywise.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pantrywise.R
import com.example.pantrywise.model.dataclass.Product
import com.example.pantrywise.model.enums.ProductCategory
import com.example.pantrywise.ui.extensions.getIcon

// Standard design for display grouped product list
@Composable
fun ProductListViewStandardContent(
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
                CategoryHeader(
                    category = category,
                    productCount = categoryProducts.size,
                    isExpanded = expandedCategories.contains(category),
                    onToggleExpanded = { onToggleExpanded(category) }
                )
            }

            if (expandedCategories.contains(category)) {
                items(categoryProducts) { product ->
                    ProductRow(
                        product = product,
                        onEdit = { onEditProduct(product) },
                        onRemove = { onRemoveProduct(product) }
                    )
                }
            }
        }
    }
}

// Product Row for standard list display without grid
@Composable
fun ProductRow(
    product: Product,
    onEdit: () -> Unit,
    onRemove: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showMenu by remember { mutableStateOf(false) }

    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Product name and quantity (no icon for individual items)
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = product.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "${product.quantity} ${getUnitShortLabel(product.productUnit)}",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // 3-dot menu
            Box {
                IconButton(onClick = { showMenu = true }) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = stringResource(com.example.pantrywise.R.string.more_options),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = { showMenu = false }
                ) {
                    DropdownMenuItem(
                        text = { Text(stringResource(com.example.pantrywise.R.string.edit)) },
                        leadingIcon = {
                            Icon(Icons.Default.Edit, contentDescription = null)
                        },
                        onClick = {
                            showMenu = false
                            onEdit()
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(stringResource(R.string.remove)) },
                        leadingIcon = {
                            Icon(Icons.Default.Delete, contentDescription = null)
                        },
                        onClick = {
                            showMenu = false
                            onRemove()
                        }
                    )
                }
            }
        }
    }
}



@Composable
fun CategoryHeader(
    category: ProductCategory,
    productCount: Int,
    isExpanded: Boolean,
    onToggleExpanded: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
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
                Text(
                    text = stringResource(category.labelResId),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Text(
                    text = "$productCount ${
                        if (productCount == 1) stringResource(R.string.item_count_single) else stringResource(
                            R.string.item_count_plural
                        )
                    }",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                )
            }

            // Expand/collapse icon
            IconButton(onClick = onToggleExpanded) {
                Icon(
                    imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = if (isExpanded) "Collapse" else "Expand",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
    }
}
