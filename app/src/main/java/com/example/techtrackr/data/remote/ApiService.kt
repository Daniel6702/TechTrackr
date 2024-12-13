package com.example.techtrackr.data.remote

import com.example.techtrackr.data.model.CategoryResponse
import com.example.techtrackr.data.model.PopularProductsResponse
import com.example.techtrackr.data.model.ProductDetailsResponse
import com.example.techtrackr.data.model.ProductListingsResponse
import com.example.techtrackr.data.model.ProductsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Url

interface ApiService {
    @GET
    suspend fun getCategoryData(@Url url: String): CategoryResponse

    @GET
    suspend fun getPopularProducts(@Url url: String): PopularProductsResponse

    @GET
    suspend fun getDeals(@Url url: String): ProductsResponse

    @GET
    suspend fun getHotProducts(@Url url: String): ProductsResponse

    @Headers(
        "Accept: application/json",
        "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0.0.0 Safari/537.36"
    )
    @GET
    suspend fun getProductDetails(@Url url: String): Response<ProductDetailsResponse>

    @GET
    suspend fun getProductListings(@Url url: String): ProductListingsResponse
}