package com.example.techtrackr.ui.category

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.techtrackr.data.model.CategoryResponse
import com.example.techtrackr.ui.category.MainCategoryCard
import com.example.techtrackr.utils.MAIN_CATEGORIES

@Composable
fun CategoryList(
    categoriesState: Map<String, CategoryResponse>, // Replace CategoryResponse with your actual data type
    navController: NavController
) {
    if (categoriesState.isEmpty()) {
        // Show loading indicator
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        MAIN_CATEGORIES.keys.forEach { id ->
            val categoryResponse = categoriesState[id]
            if (categoryResponse != null) {
                MainCategoryCard(
                    category = categoryResponse,
                    onCategoryClick = { clickedCategoryResponse ->
                        navController.navigate("category/${clickedCategoryResponse.id}")
                    }
                )
            }
        }
    }
}
