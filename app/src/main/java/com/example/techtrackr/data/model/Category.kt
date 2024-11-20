package com.example.techtrackr.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Category(
    val id: String,
    val name: String,
    val parentName: String?,
    val url: String,
    val products: List<Product> = emptyList(),
    val image: Image?,
    val title: String
)

@Serializable
data class Image(
    val id: String?,
    val url: String?,
    val path: String,
    val description: String
)


