package com.example.pantrywise.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = product.name,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            ActionMenuItem(
                label = "Remove",
                onClick = {
                    onRemove(product)
                    onDismiss()
                }
            )
            ActionMenuItem(
                label = "Move to Shopping List",
                onClick = {
                    onMove(product)
                    onDismiss()
                }
            )
            ActionMenuItem(
                label = "Edit",
                onClick = {
                    onEdit(product)
                    onDismiss()
                }
            )
        }
    }
}

@Composable
fun ActionMenuItem(label: String, onClick: () -> Unit) {
    Text(
        text = label,
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            }
            .padding(vertical = 8.dp)
    )
    HorizontalDivider()
}