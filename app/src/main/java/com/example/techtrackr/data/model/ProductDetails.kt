package com.example.techtrackr.data.model

data class ProductDetailsResponse(
    val product: ProductInfo?,
    val productGroup: ProductGroup?,
    val brand: ProductBrand?,
    val specification: ProductSpecification?,
    val productReviewSummary: ProductReviewSummary?,
    val category: ProductCategory?,
)

data class ProductInfo(
    val id: String?,
    val url: String?,
    val name: String?,
    val description: String?,
    val article: String?,
    val images: List<ProductImage>?,
    val ribbon: ProductRibbon?
)

data class ProductImage(
    val id: String?,
    val path: String?,
    val description: String?
)

data class ProductRibbon(
    val type: String?,
    val value: String?
)

data class ProductGroup(
    val members: List<ProductGroupMember>?,
    val displayMode: String?,
    val attributeName: String?
)

data class ProductGroupMember(
    val id: String?,
    val name: String?,
    val image: ProductImage?,
    val price: ProductPrice?,
    val attributeValues: List<String>?,
    val url: String?
)

data class ProductPrice(
    val amount: String?,
    val currency: String?
)

data class ProductBrand(
    val id: String?,
    val name: String?,
    val logo: ProductImage?,
    val image: ProductImage?,
    val certified: Boolean?
)

data class ProductSpecification(
    val sections: List<SpecificationSection>?,
    val creationTime: String?
)

data class SpecificationSection(
    val sectionName: String?,
    val attributes: List<SpecificationAttribute>?
)

data class SpecificationAttribute(
    val name: String?,
    val description: String?,
    val values: List<SpecificationValue>?,
    val volume: Boolean?,
    val unit: String?
)

data class SpecificationValue(
    val name: String?,
    val link: String?
)

data class ProductReviewSummary(
    val count: Int?,
    val average: String?,
    val distribution: ReviewDistribution?
)

data class ReviewDistribution(
    val one: Int?,
    val two: Int?,
    val three: Int?,
    val four: Int?,
    val five: Int?
)

data class ProductCategory(
    val id: String?,
    val name: String?,
    val url: String?
)
