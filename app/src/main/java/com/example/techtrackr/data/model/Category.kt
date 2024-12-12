package com.example.techtrackr.data.model

import com.squareup.moshi.Json

data class CategoryResponse(
    val id: String,
    val name: String,
    val shortName: String?,
    val path: String,
    val image: CategoryImage,
    val categories: List<CategoryItem>
)

data class CategoryItem(
    val id: String,
    val name: String,
    @Json(name = "originalName")
    val originalName: String,
    val path: String,
    val externalUrl: String?,
    val image: CategoryImage?,
    val children: List<CategoryItem>
)

data class CategoryImage(
    val path: String,
    val description: String?
)
