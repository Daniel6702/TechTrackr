package com.example.techtrackr.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.techtrackr.ui.navigation.CommonNavigationLayout
import com.example.techtrackr.ui.product.ProductCard
import kotlinx.coroutines.runBlocking

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = viewModel()
) {
    CommonNavigationLayout(
        title = "Home"
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {

            Spacer(modifier = Modifier.height(16.dp))

            // Search Bar
            SearchBar(
                query = homeViewModel.searchQuery,
                onQueryChange = { query -> homeViewModel.onSearchQueryChanged(query) },
                onSearch = {
                    runBlocking {
                        homeViewModel.performSearch()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Deals Section
            Text(
                text = "Deals",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))

            val deals = homeViewModel.deals
            if (deals.isEmpty()) {
                Text(text = "No deals available.", style = MaterialTheme.typography.bodyMedium)
            } else {
                LazyRow {
                    items(deals) { dealProduct ->
                        ProductCard(product = dealProduct) {
                            // Handle product click
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Hot Products Section
            Text(
                text = "Hot Products",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))

            val hotProducts = homeViewModel.hotProducts
            if (hotProducts.isEmpty()) {
                Text(text = "No hot products available.", style = MaterialTheme.typography.bodyMedium)
            } else {
                LazyRow {
                    items(hotProducts) { hotProduct ->
                        ProductCard(product = hotProduct) {
                            // Handle product click
                        }
                    }
                }
            }

        }
    }
}
