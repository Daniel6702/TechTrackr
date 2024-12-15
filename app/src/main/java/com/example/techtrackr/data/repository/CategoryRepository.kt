package com.example.techtrackr.data.repository

import com.example.techtrackr.data.model.CategoryResponse
import com.example.techtrackr.data.model.PopularProductsResponse
import com.example.techtrackr.data.model.ProductsResponse
import com.example.techtrackr.data.model.SearchResponse
import com.example.techtrackr.data.remote.ApiService
import com.example.techtrackr.utils.CATEGORY_DATA_URL
import com.example.techtrackr.utils.DEALS_URL
import com.example.techtrackr.utils.HOT_PRODUCTS_URL
import com.example.techtrackr.utils.POPULAR_PRODUCTS_URL
import com.example.techtrackr.utils.SEARCH_URL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CategoryRepository(private val apiService: ApiService) {

    suspend fun getCategoryById(id: String): CategoryResponse = withContext(Dispatchers.IO) {
        val url = CATEGORY_DATA_URL(id)
        retryOperation {
            apiService.getCategoryData(url)
        }
    }

    suspend fun getPopularProductsByCategoryId(categoryId: String): PopularProductsResponse = withContext(Dispatchers.IO) {
        val url = POPULAR_PRODUCTS_URL(categoryId)
        retryOperation {
            apiService.getPopularProducts(url)
        }
    }

    suspend fun getDeals(): ProductsResponse = withContext(Dispatchers.IO) {
        retryOperation {
            apiService.getDeals(DEALS_URL)
        }
    }

    suspend fun getHotProducts(): ProductsResponse = withContext(Dispatchers.IO) {
        retryOperation {
            apiService.getHotProducts(HOT_PRODUCTS_URL)
        }
    }

    suspend fun getSearch(searchQuery: String): SearchResponse = withContext(Dispatchers.IO) {
        retryOperation {
            apiService.getSearch(SEARCH_URL(searchQuery))
        }
    }
}