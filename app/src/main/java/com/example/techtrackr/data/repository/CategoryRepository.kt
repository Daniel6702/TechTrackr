package com.example.techtrackr.data.repository

import com.example.techtrackr.data.model.CategoryResponse
import com.example.techtrackr.data.model.PopularProductsResponse
import com.example.techtrackr.data.remote.ApiService
import com.example.techtrackr.utils.CATEGORY_DATA_URL
import com.example.techtrackr.utils.POPULAR_PRODUCTS_URL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CategoryRepository(private val apiService: ApiService) {

    suspend fun getCategoryById(id: String): CategoryResponse = withContext(Dispatchers.IO) {
        val url = CATEGORY_DATA_URL(id)
        apiService.getCategoryData(url)
    }

    suspend fun getPopularProductsByCategoryId(categoryId: String): PopularProductsResponse {
        val url = POPULAR_PRODUCTS_URL(categoryId)
        return apiService.getPopularProducts(url)
    }
}
