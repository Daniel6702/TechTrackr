package com.example.techtrackr.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.techtrackr.data.model.Product
import com.example.techtrackr.data.model.SearchProduct
import com.example.techtrackr.data.shared.LocalSharedDataViewModel
import com.example.techtrackr.ui.navigation.CommonNavigationLayout
import com.example.techtrackr.ui.navigation.LocalNavController
import com.example.techtrackr.ui.product.ProductCard
import kotlinx.coroutines.runBlocking
import kotlin.reflect.KProperty

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = viewModel()
) {
    val navController = LocalNavController.current
    val sharedDataViewModel = LocalSharedDataViewModel.current
    val recentlyViewed by sharedDataViewModel.recentlyViewed.collectAsState()

    CommonNavigationLayout(
        title = "Hjem"
    ) { paddingValues ->
        // Use a LazyColumn for scrolling
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Search Bar
            item {
                SearchBar(
                    query = homeViewModel.searchQuery,
                    onQueryChange = { query -> homeViewModel.onSearchQueryChanged(query) },
                    onSearch = { homeViewModel.performSearch() },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // Display Search Results
            item {
                val products = homeViewModel.searchProducts

                if (products.isEmpty()) {
                } else {
                    Text(text = "Search Results", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(8.dp))
                    LazyRow {
                        items(products) { product ->
                            SearchProductCard(searchProduct = product) { clickedProduct ->
                                val subcategoryId = clickedProduct.category?.id?.removePrefix("cl") ?: ""
                                val productId = clickedProduct.id ?: ""
                                navController.navigate("product/$subcategoryId/$productId")
                            }
                        }
                    }
                }
            }



            // Deals Section
            item {
                Text(text = "Dagens tilbud", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))
                val deals = homeViewModel.deals
                if (deals.isEmpty()) {
                    Text(text = "No deals available.", style = MaterialTheme.typography.bodyMedium)
                } else {
                    LazyRow {
                        items(deals) { dealProduct ->
                            ProductCard(product = dealProduct) {
                                val subcategoryId = dealProduct.category.id.removePrefix("cl")
                                navController.navigate("product/$subcategoryId/${dealProduct.id}")
                            }
                        }
                    }
                }
            }

            // Hot Products Section
            item {
                Text(text = "Populære produkter", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))
                val hotProducts = homeViewModel.hotProducts
                if (hotProducts.isEmpty()) {
                    Text(text = "No hot products available.", style = MaterialTheme.typography.bodyMedium)
                } else {
                    LazyRow {
                        items(hotProducts) { hotProduct ->
                            ProductCard(product = hotProduct) {
                                val subcategoryId = hotProduct.category.id.removePrefix("cl")
                                navController.navigate("product/$subcategoryId/${hotProduct.id}")
                            }
                        }
                    }
                }
            }

            // Recently Looked At Section
            item {
                Text(text = "Senest besøgte produkter", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))
                if (recentlyViewed.isEmpty()) {
                    Text(
                        text = "You haven't looked at any products yet.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                } else {
                    LazyRow {
                        items(recentlyViewed) { product ->
                            ProductCard(product = product) {
                                val subcategoryId = product.category.id.removePrefix("cl")
                                navController.navigate("product/$subcategoryId/${product.id}")
                            }
                        }
                    }
                }
            }
        }
    }
}
