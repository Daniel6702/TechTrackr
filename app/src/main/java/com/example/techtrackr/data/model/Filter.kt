package com.example.techtrackr.data.model

data class FiltersResponse(
    val groups: List<FilterGroup>
)

data class FilterFacetsResponse(
    val facet: FilterFacet
)

data class FilterGroup(
    val name: String,
    val filters: List<Filter>
)

data class Filter (
    val name: String,
    val id: String,
    val unit: String?,
    val type: String,
    val description: String?,
)

data class FilterFacet (
    val categoryId: String,
    val maximum: Long?,
    val minimum: Long?,
    val rangeType: String?,
    val id: String,
    val name: String,
    val facetType: String?,
    val unit: String?,
    val counts: List<Count>?,
    val groups: List<Group>?,
    val intervalCounts: List<IntervalCount>?,
    val type: String
)

data class IntervalCount (
    val count: Int,
    val interval: String,
    val optionValue: String
)

data class Group (
    val name: String?,
    val from: String?,
    val to: String?,
    val count: Float
)

data class Count (
    val key: String?,
    val optionId: Long?,
    val optionValue: String?,
    val optionImage: Image?,
    val from: Float?,
    val to: Float?,
    val count: Int
)
