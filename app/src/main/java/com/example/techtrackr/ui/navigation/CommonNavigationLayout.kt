package com.example.techtrackr.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.example.techtrackr.data.shared.LocalSharedDataViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonNavigationLayout(
    title: String = "Techtrackr",
    content: @Composable (PaddingValues) -> Unit
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val navController = LocalNavController.current
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val drawerWidth = screenWidth * 0.8f

    val sharedDataViewModel = LocalSharedDataViewModel.current

    ModalNavigationDrawer(
        drawerContent = {
            DrawerContent(
                onHomeClick = {
                    navController.navigate("home")
                    scope.launch { drawerState.close() }
                },
                sharedDataViewModel = sharedDataViewModel,
                modifier = Modifier.width(drawerWidth)
            )
        },
        drawerState = drawerState,
        gesturesEnabled = drawerState.isOpen,
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = title) },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(
                                imageVector = Icons.Filled.Menu,
                                contentDescription = "Open Drawer"
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = {
                            navController.navigate("profile")
                            scope.launch { drawerState.close() }
                        }) {
                            Icon(
                                imageVector = Icons.Filled.AccountCircle,
                                contentDescription = "Profile"
                            )
                        }
                    }
                )
            },
            content = { paddingValues ->
                content(paddingValues)
            }
        )
    }
}
