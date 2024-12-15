package com.example.techtrackr.ui.home

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.techtrackr.data.model.Product
import com.example.techtrackr.data.model.SearchResponse
import com.example.techtrackr.data.repository.CategoryRepository
import com.example.techtrackr.data.remote.NetworkModule
import com.example.techtrackr.utils.ALLOWED_CATEGORY_IDS
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val repository = CategoryRepository(NetworkModule.apiService)

    // Search State
    private var _searchQuery = mutableStateOf("")
    val searchQuery: String get() = _searchQuery.value

    // Deals State
    var deals by mutableStateOf<List<Product>>(emptyList())
        private set

    // Hot Products State
    var hotProducts by mutableStateOf<List<Product>>(emptyList())
        private set

    var searchResults by mutableStateOf<SearchResponse>()
        private set



    init {
        loadDeals()
        loadHotProducts()
    }

    fun onSearchQueryChanged(newQuery: String) {
        _searchQuery.value = newQuery
        Log.d("HomeViewModel", "Search query changed: $newQuery")

        // Trigger search when query changes
        performSearch()
    }

    fun performSearch() {
        val query = _searchQuery.value.lowercase()

        viewModelScope.launch {
            try {
                val response = repository.getSearch(searchQuery)
                searchResults = response  // Assign SearchResponse from repository
                Log.d("HomeViewModel", "Search query: $searchQuery, Found ${response.products?.size} products")
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("HomeViewModel", "Error performing search: ${e.message}")
            }
        }
    }

    private fun loadDeals() {
        Log.d("HomeViewModel", "LOADING DEALS")
        viewModelScope.launch {
            try {
                val response = repository.getDeals()
                Log.d("HomeViewModel", "Response: $response")
                // Filter the products by allowed category IDs
                val filteredDeals = response.products.filter { product ->
                    ALLOWED_CATEGORY_IDS.contains(product.category.id)
                }
                deals = filteredDeals
            } catch (e: Exception) {
                e.printStackTrace()
                deals = emptyList()
            }
        }
    }

    private fun loadHotProducts() {
        viewModelScope.launch {
            try {
                val response = repository.getHotProducts()
                // Filter by allowed categories
                val filteredHot = response.products.filter { product ->
                    ALLOWED_CATEGORY_IDS.contains(product.category.id)
                }
                hotProducts = filteredHot
            } catch (e: Exception) {
                e.printStackTrace()
                hotProducts = emptyList()
            }
        }
    }
}
