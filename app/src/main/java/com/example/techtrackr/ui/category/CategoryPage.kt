package com.example.techtrackr.ui.category

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.techtrackr.data.model.CategoryItem
import com.example.techtrackr.data.model.CategoryResponse
import com.example.techtrackr.data.shared.LocalSharedDataViewModel
import com.example.techtrackr.ui.navigation.CommonNavigationLayout
import com.example.techtrackr.ui.navigation.LocalNavController
import kotlinx.coroutines.launch

@Composable
fun CategoryPage(
    categoryId: String,
    sharedDataViewModel: com.example.techtrackr.data.shared.SharedDataViewModel = LocalSharedDataViewModel.current
) {
    val navController = LocalNavController.current
    val categoriesMap by sharedDataViewModel.categoriesState.collectAsState()

    // Load the category if needed
    LaunchedEffect(categoryId) {
        sharedDataViewModel.loadCategoryIfNeeded(categoryId)
    }

    val category = categoriesMap[categoryId]

    CommonNavigationLayout(title = category?.name ?: "Category") { paddingValues ->
        if (category == null) {
            // Still loading category data
            Box(
                modifier = Modifier.fillMaxSize().padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            // Display the children categories
            LazyColumn(contentPadding = paddingValues) {
                items(category.categories) { child ->
                    ChildCategoryCard(
                        childCategory = child,
                        onClick = { clickedCategory ->
                            if (clickedCategory.id.startsWith("t")) {
                                // It's a main category
                                navController.navigate("category/${clickedCategory.id}")
                            } else if (clickedCategory.id.startsWith("cl")) {
                                // It's a subcategory
                                // TODO: Navigate to product page (not implemented)
                                // navController.navigate("products/${clickedCategory.id}")
                            }
                        }
                    )
                }
            }
        }
    }
}
