package com.example.techtrackr.data.remote

import com.example.techtrackr.data.model.CategoryResponse
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiService {
    @GET
    suspend fun getCategoryData(@Url url: String): CategoryResponse
}