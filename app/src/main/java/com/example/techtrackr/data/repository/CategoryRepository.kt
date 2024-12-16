package com.example.techtrackr.data.repository

import android.util.Log
import com.example.techtrackr.data.model.CategoryProductsResponse
import com.example.techtrackr.data.model.CategoryResponse
import com.example.techtrackr.data.model.FilterFacetResponse
import com.example.techtrackr.data.model.FiltersResponse
import com.example.techtrackr.data.model.PopularProductsResponse
import com.example.techtrackr.data.model.ProductsResponse
import com.example.techtrackr.data.model.SearchResponse
import com.example.techtrackr.data.remote.ApiService
import com.example.techtrackr.utils.CATEGORY_DATA_URL
import com.example.techtrackr.utils.DEALS_URL
import com.example.techtrackr.utils.FILTER_FACETS
import com.example.techtrackr.utils.HOT_PRODUCTS_URL
import com.example.techtrackr.utils.POPULAR_PRODUCTS_URL
import com.example.techtrackr.utils.SEARCH_URL
import com.example.techtrackr.utils.SUBCATEGORY_FILTERS
import com.example.techtrackr.utils.SUBCATEGORY_PRODUCTS
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
        val url = SEARCH_URL(searchQuery)
        Log.d("CategoryRepository", "URL $url")
        retryOperation {
            apiService.getSearch(url)
        }
    }

    suspend fun getCategoryDeals(categoryId: String, parameters: String): CategoryProductsResponse = withContext(Dispatchers.IO) {
        val url = SUBCATEGORY_PRODUCTS(categoryId, parameters)
        Log.d("CategoryRepository", "URL $url")
        retryOperation {
            apiService.getCategoryDeals(url)
        }
    }

    suspend fun getCategoryFilters(categoryId: String): FiltersResponse = withContext(Dispatchers.IO) {
        val url = SUBCATEGORY_FILTERS(categoryId)
        Log.d("CategoryRepository", "URL $url")
        retryOperation {
            apiService.getCategoryFilters(url)
        }
    }

    suspend fun getFilterFacet(categoryId: String, filterId: String): FilterFacetResponse = withContext(Dispatchers.IO) {
        val url = FILTER_FACETS(categoryId, filterId)
        Log.d("CategoryRepository", "URL $url")
        retryOperation {
            apiService.GetFilterFacets(url)
        }
    }
}