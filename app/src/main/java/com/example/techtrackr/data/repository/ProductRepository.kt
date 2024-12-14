package com.example.techtrackr.data.repository

import android.util.Log
import com.example.techtrackr.data.model.PriceHistoryResponse
import com.example.techtrackr.data.model.ProductDetailsResponse
import com.example.techtrackr.data.model.ProductListingsResponse
import com.example.techtrackr.data.remote.ApiService
import com.example.techtrackr.utils.PRICE_HISTORY
import com.example.techtrackr.utils.PRODUCT_DETAILS
import com.example.techtrackr.utils.PRODUCT_LISTINGS
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProductRepository(private val apiService: ApiService) {

    suspend fun getProductDetails(subcategoryId: String, productId: String): ProductDetailsResponse? = withContext(Dispatchers.IO) {
        val url = PRODUCT_DETAILS(subcategoryId, productId)
        Log.d("ProductRepository", "Fetching product details from URL: $url")

        val response = retryOperation {
            apiService.getProductDetails(url)
        }

        if (response.isSuccessful) {
            Log.d("ProductRepository", "Product details fetched successfully with response: $response")
            response.body()
        } else {
            Log.e("ProductRepository", "Error fetching product details: ${response.code()}")
            null
        }
    }

    suspend fun getProductListings(productId: String): ProductListingsResponse = withContext(Dispatchers.IO) {
        val url = PRODUCT_LISTINGS(productId)
        retryOperation {
            apiService.getProductListings(url)
        }
    }

    suspend fun getPriceHistory(productId: String): PriceHistoryResponse? = withContext(Dispatchers.IO) {
        val url = PRICE_HISTORY(productId)
        Log.d("ProductRepository", "Fetching price history from URL: $url")
        val response = retryOperation {
            apiService.getPriceHistory(url)
        }
        if (response.isSuccessful) {
            Log.d("ProductRepository", "Price history fetched successfully: ${response.body()}")
            response.body()
        } else {
            Log.e("ProductRepository", "Error fetching price history: ${response.code()}")
            null
        }
    }

}
