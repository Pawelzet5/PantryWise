package com.example.pantrywise.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.pantrywise.model.dataclass.Product


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductActionMenu(
    product: Product,
    onDismiss: () -> Unit,
    onRemove: (Product) -> Unit,
    onMove: (Product) -> Unit,
    onEdit: (Product) -> Unit,
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss
    ) {
        Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 4.dp, bottom = 16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text(
                text = product.name,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            ActionMenuItem(
                label = "Remove",
                icon = Icons.Default.Delete,
                onClick = {
                    onRemove(product)
                    onDismiss()
                }
            )
            ActionMenuItem(
                label = "Move to Shopping List",
                icon = Icons.Default.ShoppingCart,
                onClick = {
                    onMove(product)
                    onDismiss()
                }
            )
            ActionMenuItem(
                label = "Edit",
                icon = Icons.Default.Edit,
                onClick = {
                    onEdit(product)
                    onDismiss()
                }
            )
        }
    }
}

@Composable
fun ActionMenuItem(label: String, icon: ImageVector, onClick: () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable { onClick() }
    ) {
        Icon(imageVector = icon, contentDescription = label)
        Spacer(modifier = Modifier.size(24.dp))
        Text(
            text = label,
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}