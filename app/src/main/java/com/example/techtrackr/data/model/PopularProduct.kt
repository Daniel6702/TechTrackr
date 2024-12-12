package com.example.techtrackr.data.model

data class PopularProductsResponse(
    val productsCards: List<PopularProductItem>
)

data class PopularProductItem(
    val id: String,
    val name: String,
    val path: String,
    val category: PopularProductCategory,
    val brand: PopularProductBrand?,
    val image: PopularProductImage,
    val lowestPrice: PopularProductPrice?
)

data class PopularProductCategory(
    val id: String,
    val path: String,
    val name: String
)

data class PopularProductBrand(
    val id: String,
    val name: String,
    val image: PopularProductImage?,
    val showLogo: Boolean
)

data class PopularProductImage(
    val id: String,
    val path: String,
    val description: String?
)

data class PopularProductPrice(
    val amount: String,
    val currency: String
)
