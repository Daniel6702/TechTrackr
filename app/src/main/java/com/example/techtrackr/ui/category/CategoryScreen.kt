package com.example.techtrackr.ui.category

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.techtrackr.data.shared.LocalSharedDataViewModel
import com.example.techtrackr.data.shared.SharedDataViewModel
import com.example.techtrackr.ui.navigation.CommonNavigationLayout
import com.example.techtrackr.ui.navigation.LocalNavController

@Composable
fun CategoryScreen(
    categoryViewModel: CategoryViewModel = viewModel(),
    categoryId: String,
    sharedDataViewModel: SharedDataViewModel = LocalSharedDataViewModel.current
) {
    val navController = LocalNavController.current
    val categoriesMap by sharedDataViewModel.categoriesState.collectAsState()
    val category = categoriesMap[categoryId]

    val isDialogOpen = remember { mutableStateOf(false) }
    fun closeDialog() { isDialogOpen.value = false }
    if (isDialogOpen.value) {
        FilterDialog(categoryViewModel, ::closeDialog)
    }

    LaunchedEffect(categoryId) {
        sharedDataViewModel.loadCategoryIfNeeded(categoryId)
        categoryViewModel.setCategoryId(categoryId)
    }

    CommonNavigationLayout(title = category?.name ?: "Kategori") { paddingValues ->
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(modifier = Modifier.padding(paddingValues)) {
                items(categoryViewModel.deals) { deal ->
                    CategoryProductCard(product = deal) {
                        navController.navigate("product/$categoryId/${deal.id}")
                    }
                }
            }

            FloatingActionButton(
                onClick = { isDialogOpen.value = true },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    Icon(Icons.Filled.Tune, contentDescription = "Add Filter")
                    Text(
                        text = "Filtrer",
                        modifier = Modifier
                            .padding(start = 16.dp)
                    )
                }
            }
        }
    }
}
