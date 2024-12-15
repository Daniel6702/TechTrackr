package com.example.techtrackr.ui.category

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.techtrackr.data.shared.LocalSharedDataViewModel
import com.example.techtrackr.ui.navigation.CommonNavigationLayout
import com.example.techtrackr.ui.navigation.LocalNavController
import com.example.techtrackr.ui.product.PopularProductCard

@Composable
fun CategoryPage(
    categoryId: String,
    sharedDataViewModel: com.example.techtrackr.data.shared.SharedDataViewModel = LocalSharedDataViewModel.current
) {
    val navController = LocalNavController.current
    val categoriesMap by sharedDataViewModel.categoriesState.collectAsState()
    val popularProductsMap by sharedDataViewModel.popularProductsState.collectAsState()

    LaunchedEffect(categoryId) {
        sharedDataViewModel.loadCategoryIfNeeded(categoryId)

        if (categoryId.startsWith("t")) {
            sharedDataViewModel.loadPopularProducts(categoryId)
        }
    }

    val category = categoriesMap[categoryId]
    val popularProducts = popularProductsMap[categoryId]

    CommonNavigationLayout(title = category?.name ?: "Category") { paddingValues ->
        if (category == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Column(modifier = Modifier.padding(paddingValues)) {
                Text(
                    text = "Under Kategorier",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontSize = MaterialTheme.typography.titleLarge.fontSize
                    ),
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 4.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp)
                ) {
                    items(category.categories) { child ->
                        ChildCategoryCard(
                            childCategory = child,
                            onClick = { clickedCategory ->
                                navController.navigate("category/${clickedCategory.id}")
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Populære Produkter",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontSize = MaterialTheme.typography.titleLarge.fontSize
                    ),
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 4.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))

                if (popularProducts == null) {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                } else {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp)
                    ) {
                        items(popularProducts) { product ->
                            PopularProductCard(product = product) {
                                navController.navigate("product/$categoryId/${product.id}")
                            }
                        }
                    }
                }
            }
        }
    }
}
