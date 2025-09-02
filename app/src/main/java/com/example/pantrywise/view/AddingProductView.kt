import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pantrywise.model.dataclass.Product
import com.example.pantrywise.model.enums.ProductCategory
import com.example.pantrywise.model.enums.ProductUnit
import com.example.pantrywise.util.DateTimeHelper
import com.example.pantrywise.view.ProductInputState
import com.example.pantrywise.viewmodel.AddingProductViewModel
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date

@Preview(showBackground = true)
@Composable
private fun AddingProductViewPreview() {
    ProductInputView({}, {})
}

@Composable
fun AddingProductScreen(
    addingProductViewModel: AddingProductViewModel
) {
    val selectedProducts by addingProductViewModel.selectedProducts.collectAsState()
    val productSuggestions by addingProductViewModel.suggestions.collectAsState()
    val inputText by addingProductViewModel.queryText.collectAsState()
    AddingProductScreenContent(
        selectedProducts = selectedProducts,
        productSuggestions = productSuggestions,
        inputText = inputText,
        onProductInputStateChange = addingProductViewModel::onProductInputStateChange,
        onRemoveProduct = addingProductViewModel::handleRemoveProductInput,
        onCancelAdding = addingProductViewModel::handleCancelAdding,
        onSubmitAdding = addingProductViewModel::handleAddProducts,
        onInputTextChanged = addingProductViewModel::onQueryUpdate,
        onSuggestionSelected = addingProductViewModel::onSuggestionSelected
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddingProductScreenContent(
    selectedProducts: List<ProductInputState>,
    productSuggestions: List<String>,
    inputText: String,
    onProductInputStateChange: (Int, ProductInputState) -> Unit,
    onRemoveProduct: (Int) -> Unit,
    onCancelAdding: () -> Unit,
    onSubmitAdding: () -> Unit,
    onInputTextChanged: (String) -> Unit,
    onSuggestionSelected: (String) -> Unit,
) {
    val scaffoldState = rememberBottomSheetScaffoldState()
    val containerHeight = LocalWindowInfo.current.containerSize.height.dp
    val maxSheetHeight = containerHeight * .5f
    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {
            if (selectedProducts.isNotEmpty())
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 2.dp)
                        .heightIn(max = maxSheetHeight),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LazyColumn(
                        modifier = Modifier.weight(1f, fill = false).fillMaxWidth()
                    ) {
                        itemsIndexed(selectedProducts) { index, state ->
                            ProductInputRow(
                                state = state,
                                onStateChange = { updated ->
                                    onProductInputStateChange(
                                        index,
                                        updated
                                    )
                                },
                                showRemove = true,
                                onRemove = { onRemoveProduct(index) }
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = { onCancelAdding() },
                            modifier = Modifier
                        ) {
                            Text("Cancel")
                        }
                        Button(
                            onClick = { onSubmitAdding() },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Add Products")
                        }
                    }
                }
        },
        sheetPeekHeight = if (selectedProducts.isEmpty()) 0.dp else 56.dp,
        content = {
            ProductSuggestionSection(
                productSuggestions = productSuggestions,
                inputText = inputText,
                onInputTextChanged = onInputTextChanged,
                onSuggestionSelected = onSuggestionSelected
            )
        }
    )
}

@Composable
fun ProductInputView(
    onAdd: (List<Product>) -> Unit,
    onCancel: () -> Unit
) {
    var productInputs by remember { mutableStateOf(listOf(ProductInputState())) }

    Column(
        modifier = Modifier.fillMaxSize().background(Color.Transparent),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn(modifier = Modifier.weight(1f).padding(horizontal = 12.dp, vertical = 4.dp)) {
            itemsIndexed(productInputs) { index, inputState ->
                ProductInputRow(
                    state = inputState,
                    onStateChange = { updated ->
                        productInputs =
                            productInputs.toMutableList().also { it[index] = updated }
                    },
                    onRemove = {
                        if (productInputs.size > 1) {
                            productInputs =
                                productInputs.toMutableList().also { it.removeAt(index) }
                        }
                    },
                    showRemove = productInputs.size > 1
                )
            }
        }
        Row(modifier = Modifier.padding(8.dp)) {
            Button(onClick = {
                productInputs = productInputs + ProductInputState()
            }) {
                Text("Add another")
            }
        }
        Row(modifier = Modifier.padding(16.dp)) {
            Button(onClick = {
                onAdd(productInputs.map { it.toProduct() })
            }, enabled = productInputs.all { it.isValid() }) {
                Text("Add Product(s)")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = onCancel) {
                Text("Cancel")
            }
        }
    }
}

@Preview
@Composable
fun AddingProductBottomDialogPreview() {
    AddingProductBottomDialog(
        listOf(ProductInputState()),
        {}, {}, { _, _ -> }, {}, {}
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddingProductBottomDialog(
    selectedProducts: List<ProductInputState>,
    onCancel: () -> Unit,
    onAddingProducts: () -> Unit,
    onProductChange: (Int, ProductInputState) -> Unit,
    onProductRemove: (Int) -> Unit,
    onDismiss: () -> Unit
) {
    if (selectedProducts.isNotEmpty()) {
        val sheetState = rememberModalBottomSheetState(
            skipPartiallyExpanded = false
        )
        ModalBottomSheet(
            onDismissRequest = onDismiss,
            sheetState = sheetState,
            dragHandle = {},
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LazyColumn(
                    modifier = Modifier.weight(1f, fill = false).fillMaxWidth()
                ) {
                    itemsIndexed(selectedProducts) { index, state ->
                        ProductInputRow(
                            state = state,
                            onStateChange = { updated -> onProductChange(index, updated) },
                            showRemove = true,
                            onRemove = { onProductRemove(index) }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = onCancel,
                        modifier = Modifier
                    ) {
                        Text("Cancel")
                    }
                    Button(
                        onClick = onAddingProducts,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Add Products")
                    }
                }
            }
        }
    }
}

@Composable
fun ProductInputRow(
    state: ProductInputState,
    onStateChange: (ProductInputState) -> Unit,
    showRemove: Boolean = false,
    onRemove: () -> Unit = {}
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Row 2: Category (60%) + Date (40%)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ProductCategoryDropdown(
                    selected = state.category,
                    modifier = Modifier.fillMaxWidth(.6f),
                    onSelected = { onStateChange(state.copy(category = it)) }
                )
                ProductExpirationDateField(
                    timestamp = state.expirationDate,
                    modifier = Modifier,
                    onValueChange = { onStateChange(state.copy(expirationDate = it)) }
                )
            }

            // Row 3: Quantity (50%) + Unit (50%)
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                ProductQuantityField(
                    value = state.quantity,
                    modifier = Modifier.fillMaxWidth(.5f),
                    onValueChange = { onStateChange(state.copy(quantity = it)) }
                )
                ProductUnitDropdown(
                    selected = state.unit,
                    modifier = Modifier,
                    onSelected = { onStateChange(state.copy(unit = it)) }
                )
            }

            // Row 1: Name field (fills max width) + Delete button (minimal space)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ProductNameField(
                    value = state.name,
                    modifier = Modifier.fillMaxWidth(),
                    onValueChange = { onStateChange(state.copy(name = it)) }
                )
                if (showRemove) {
                    IconButton(onClick = onRemove) {
                        Icon(Icons.Default.Delete, contentDescription = "Remove")
                    }
                }
            }
        }
    }
}

@Composable
fun ProductNameField(
    value: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text("Name") },
        modifier = modifier,
        shape = RoundedCornerShape(12.dp)
    )
}

@Composable
fun ProductQuantityField(
    value: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text("Quantity") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        maxLines = 1,
        modifier = modifier,
        shape = RoundedCornerShape(12.dp)
    )
}

@Composable
private fun ClickableDropdownField(
    fieldContent: @Composable (Boolean, () -> Unit) -> Unit,
    dropdownContent: @Composable (Boolean, () -> Unit) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    Box(modifier = modifier.fillMaxWidth()) {
        fieldContent(expanded) { expanded = true }
        Box(
            Modifier
                .matchParentSize()
                .clickable { expanded = true }
        )
        dropdownContent(expanded) { expanded = false }
    }
}

@Composable
fun ProductUnitDropdown(
    selected: ProductUnit,
    modifier: Modifier = Modifier,
    onSelected: (ProductUnit) -> Unit
) {
    ClickableDropdownField(
        fieldContent = { expanded, onExpand ->
            OutlinedTextField(
                value = stringResource(selected.labelResId),
                onValueChange = {},
                readOnly = true,
                label = { Text("Unit") },
                trailingIcon = {
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Select unit")
                },
                maxLines = 1,
                shape = RoundedCornerShape(12.dp)
            )
        },
        dropdownContent = { expanded, onDismiss ->
            DropdownMenu(expanded = expanded, onDismissRequest = { onDismiss() }) {
                ProductUnit.entries.forEach { unit ->
                    DropdownMenuItem(
                        text = { Text(unit.name) },
                        onClick = {
                            onSelected(unit)
                            onDismiss()
                        }
                    )
                }
            }
        },
        modifier = modifier.fillMaxWidth()
    )
}

@Composable
fun ProductCategoryDropdown(
    selected: ProductCategory,
    modifier: Modifier = Modifier,
    onSelected: (ProductCategory) -> Unit
) {
    ClickableDropdownField(
        fieldContent = { expanded, onExpand ->
            OutlinedTextField(
                value = selected.name,
                onValueChange = {},
                readOnly = true,
                label = { Text("Category") },
                trailingIcon = {
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Select category")
                },
                maxLines = 1,
                shape = RoundedCornerShape(12.dp)
            )
        },
        dropdownContent = { expanded, onDismiss ->
            DropdownMenu(expanded = expanded, onDismissRequest = { onDismiss() }) {
                ProductCategory.entries.forEach { category ->
                    DropdownMenuItem(
                        text = { Text(category.name) },
                        onClick = {
                            onSelected(category)
                            onDismiss()
                        }
                    )
                }
            }
        },
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductExpirationDateField(
    timestamp: Long?,
    modifier: Modifier = Modifier,
    onValueChange: (Long?) -> Unit
) {
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = timestamp ?: System.currentTimeMillis()
    )
    val daysToExpiration = timestamp?.let { DateTimeHelper.daysBetweenTimestampAndNow(it) } ?: 0
    OutlinedTextField(
        value = daysToExpiration.toString(),
        onValueChange = {},
        readOnly = true,
        label = {
            Text(
                text = when {
                    daysToExpiration > 0 -> "Days to expiration"
                    daysToExpiration == 0 -> "Expired today"
                    else -> "Days after expiration"
                }
            )
        },
        trailingIcon = {
            IconButton(onClick = { showDatePicker = true }) {
                Icon(Icons.Default.DateRange, contentDescription = "Select date")
            }
        },
        modifier = modifier.clickable { showDatePicker = true },
        maxLines = 1,
        shape = RoundedCornerShape(12.dp),
    )
    if (showDatePicker) {
        val currentDate = LocalDate.now()
        val initialDate = timestamp?.let {
            Date(it).toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        } ?: currentDate

        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let { selectedTimestamp ->
                            onValueChange(selectedTimestamp)
                        }
                        showDatePicker = false
                    }
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDatePicker = false }
                ) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(
                state = datePickerState,
                title = { Text("Select Expiration Date") },
                headline = { Text("Choose when this product expires") },
                showModeToggle = false
            )
        }
    }
}

@Composable
private fun ProductSuggestionSection(
    productSuggestions: List<String>,
    inputText: String,
    onInputTextChanged: (String) -> Unit,
    onSuggestionSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        OutlinedTextField(
            value = inputText,
            onValueChange = onInputTextChanged,
            label = { Text("Product Name") },
            trailingIcon = {
                if (inputText.isNotEmpty()) {
                    IconButton(onClick = { onInputTextChanged("") }) {
                        Icon(Icons.Default.Clear, contentDescription = "Clear")
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
        if (productSuggestions.isNotEmpty()) {
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(productSuggestions) { suggestion ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp, horizontal = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            suggestion,
                            modifier = Modifier.weight(0.8f)
                        )
                        Button(
                            onClick = { onSuggestionSelected(suggestion) },
                            modifier = Modifier.weight(0.2f)
                        ) {
                            Text("Add")
                        }
                    }
                }
            }
        }
    }
}
