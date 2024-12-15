package com.example.techtrackr.data.remote

import com.example.techtrackr.data.model.CategoryResponse
import com.example.techtrackr.data.model.PopularProductsResponse
import com.example.techtrackr.data.model.PriceHistoryResponse
import com.example.techtrackr.data.model.ProductDetailsResponse
import com.example.techtrackr.data.model.ProductListingsResponse
import com.example.techtrackr.data.model.ProductsResponse
import com.example.techtrackr.data.model.SearchResponse
import retrofit2.Response
import retrofit2.http.GET
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

    @GET
    suspend fun getProductDetails(@Url url: String): Response<ProductDetailsResponse>

    @GET
    suspend fun getProductListings(@Url url: String): ProductListingsResponse

    @GET
    suspend fun getPriceHistory(@Url url: String): Response<PriceHistoryResponse>

    @GET
    suspend fun getSearch(@Url url: String): SearchResponse

}