package com.example.techtrackr.data.model

data class ProductsResponse(
    val totalHits: Int?,
    val products: List<Product>
)

data class Product(
    val id: String,
    val name: String,
    val url: String?,
    val description: String?,
    val lowestPrice: PdPrice?,
    val category: Category,
    val image: Image?,
    val rating: Rating?,
    val priceDrop: PriceDrop?,
    val brand: Brand?,
    val ribbon: Ribbon?,
    val classification: String?,
    val previewMerchants: PreviewMerchants?
)

data class PdPrice(val amount: String, val currency: String)
data class Category(val id: String, val name: String, val url: String?)
data class Image(val id: String?, val url: String?, val path: String, val description: String?)
data class Rating(val numberOfRatings: Int?, val averageRating: String?, val count: Int?, val average: String?)
data class PriceDrop(val oldPrice: PdPrice?, val percent: String?)
data class Brand(val id: String?, val name: String?, val image: Image?)
data class Ribbon(val type: String?, val value: String?, val description: String?)
data class PreviewMerchants(val count: Int, val merchants: List<PdMerchant>)
data class PdMerchant(
    val id: String?,
    val name: String?,
    val image: Image?,
    val clickable: Boolean
)
