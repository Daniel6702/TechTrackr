package com.example.techtrackr.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.techtrackr.ui.navigation.CommonNavigationLayout
import kotlinx.coroutines.runBlocking

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = viewModel()
) {
    CommonNavigationLayout(
        title = "Home"
    ) { paddingValues ->
        // Content unique to the Home page
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Add SearchBar under main navigation
            SearchBar(
                query = homeViewModel.searchQuery,
                onQueryChange = { query -> homeViewModel.onSearchQueryChanged(query) },
                onSearch = {
                    // Perform search with coroutine since onSearch can be suspend
                    runBlocking {
                        homeViewModel.performSearch()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Home Screen Content", style = MaterialTheme.typography.bodyLarge)
            // Add more home screen content as needed
        }
    }
}
