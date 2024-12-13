package com.example.techtrackr.utils

import com.example.techtrackr.data.model.Product
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

object JsonUtils {
    private val moshi = Moshi.Builder()
        .add(com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory())
        .build()

    private val productListType = Types.newParameterizedType(List::class.java, Product::class.java)
    private val productListAdapter = moshi.adapter<List<Product>>(productListType)

    fun toJson(products: List<Product>): String = productListAdapter.toJson(products)
    fun fromJson(json: String): List<Product>? = productListAdapter.fromJson(json)
}
