package com.example.techtrackr.data.shared

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.techtrackr.data.repository.CategoryRepository
import com.example.techtrackr.data.model.CategoryResponse
import com.example.techtrackr.data.model.PopularProductItem
import com.example.techtrackr.data.remote.NetworkModule
import com.example.techtrackr.utils.MAIN_CATEGORIES
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class SharedDataViewModel : ViewModel() {

    private val repository = CategoryRepository(NetworkModule.apiService)

    // StateFlow for categories
    private val _categoriesState = MutableStateFlow<Map<String, CategoryResponse>>(emptyMap())
    val categoriesState: StateFlow<Map<String, CategoryResponse>> get() = _categoriesState

    private val _popularProductsState = MutableStateFlow<Map<String, List<PopularProductItem>>>(emptyMap())
    val popularProductsState: StateFlow<Map<String, List<PopularProductItem>>> get() = _popularProductsState


    // If you have other data sets, create StateFlows and methods similarly
    // e.g., private val _userPreferences = MutableStateFlow<UserPreferences?>(null)
    // val userPreferences: StateFlow<UserPreferences?> get() = _userPreferences

    // Called after user logs in or when the app starts
    fun preloadData() {
        preloadCategories()
    }

    private fun preloadCategories() {
        viewModelScope.launch {
            val resultMap = mutableMapOf<String, CategoryResponse>()
            for ((id, _) in MAIN_CATEGORIES) {
                try {
                    val response = repository.getCategoryById(id)
                    resultMap[id] = response
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            _categoriesState.value = resultMap
        }
    }

    fun loadPopularProducts(categoryId: String) {
        viewModelScope.launch {
            try {
                val response = repository.getPopularProductsByCategoryId(categoryId)
                val updatedMap = _popularProductsState.value.toMutableMap()
                updatedMap[categoryId] = response.productsCards
                _popularProductsState.value = updatedMap
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    fun loadCategoryIfNeeded(categoryId: String) {
        if (!categoriesState.value.containsKey(categoryId)) {
            viewModelScope.launch {
                try {
                    val response = repository.getCategoryById(categoryId)
                    val updatedMap = _categoriesState.value.toMutableMap()
                    updatedMap[categoryId] = response
                    _categoriesState.value = updatedMap
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}
