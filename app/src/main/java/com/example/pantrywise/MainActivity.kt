package com.example.pantrywise

import AddingProductScreen
import android.os.Bundle
import androidx.activity.*
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.*
import com.example.pantrywise.ui.theme.PantryWiseTheme
import com.example.pantrywise.view.ProductListViewContent
import com.example.pantrywise.viewmodel.AddingProductViewModel
import com.example.pantrywise.viewmodel.ProductListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val productListViewModel: ProductListViewModel by viewModels()
    private val addingProductViewModel: AddingProductViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PantryWiseTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        topBar = { MainTopBar(currentRoute) },
                        floatingActionButton = { MainFab(currentRoute, navController) }
                    ) { innerPadding ->
                        NavHost(
                            navController = navController,
                            startDestination = "productList",
                            modifier = Modifier.padding(innerPadding)
                        ) {
                            composable("productList") {
                                ProductListViewContent(
                                    viewModel = productListViewModel,
                                    navController = navController
                                )
                            }
                            composable("addProduct") {
                                AddingProductScreen(addingProductViewModel = addingProductViewModel)
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopBar(currentRoute: String?) {
    TopAppBar(
        title = {
            Text(
                text = when (currentRoute) {
                    "addProduct" -> "Add new product"
                    else -> "My Inventory"
                }
            )
        }
    )
}

@Composable
fun MainFab(currentRoute: String?, navController: NavController) {
    if (currentRoute == "productList") {
        FloatingActionButton(onClick = { navController.navigate("addProduct") }) {
            Icon(Icons.Default.Add, contentDescription = "Add Product")
        }
    }
}