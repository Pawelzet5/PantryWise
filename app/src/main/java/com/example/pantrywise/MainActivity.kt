package com.example.pantrywise

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.pantrywise.ui.theme.PantryWiseTheme
import com.example.pantrywise.view.NavigationRoot
import com.example.pantrywise.view.navigation.*
import com.example.pantrywise.viewmodel.MainScaffoldAction
import com.example.pantrywise.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PantryWiseTheme {
                AppRoot()
            }
        }
    }
}

@Composable
fun AppRoot(mainViewModel: MainViewModel = hiltViewModel()) {
    val scaffoldState by mainViewModel.scaffoldState.collectAsStateWithLifecycle()
    Scaffold(
        topBar = {
            scaffoldState.topBarTextResId?.let {
                MainTopBar(
                    showBackButton = scaffoldState.showBackButton,
                    onBackClick = { mainViewModel.onAction(MainScaffoldAction.OnBackClick) },
                    text = stringResource(it)
                )
            }
        },
        floatingActionButton = {
            MainFab(
                showButton = scaffoldState.showAddButton,
                onClick = { mainViewModel.onAction(MainScaffoldAction.OnAddClick(it)) }
            )
        }
    ) { innerPadding ->
        NavigationRoot(Modifier.padding(innerPadding))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopBar(
    showBackButton: Boolean,
    onBackClick: () -> Unit,
    text: String
) {
    TopAppBar(
        title = { Text(text = text) },
        navigationIcon = {
            if (showBackButton)
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
        }
    )
}

@Composable
fun MainFab(
    showButton: Boolean,
    onClick: (AppNavKey) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    SpeedDialFab(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        },
        onAddManual = {
            expanded = false
            onClick(AddingProductScreenKey)

        },
        onAddCamera = {
            expanded = false
            onClick(AddingProductWithCameraScreenKey)
        },
        visible = showButton
    )
}