package com.example.techtrackr.utils

const val BASE_URL = "https://www.pricerunner.dk/"

const val BASE_API_URL  = BASE_URL + "dk/api/search-compare-gateway/public/"

val CATEGORY_DATA_URL: (String) -> String = { id -> "$BASE_API_URL/navigation/menu/DK/hierarchy/$id" }

val POPULAR_PRODUCTS_URL: (String) -> String = { id -> "$BASE_API_URL/popularproducts/v2/DK/$id" }

val MAIN_CATEGORIES = mapOf(
    "t1" to "Lyd & Billede",
    "t2" to "Computer & Software",
    "t4" to "Telefoni & Wearables",
    "t17" to "Foto & Video",
    "t19" to "Gaming & Underholdning",
)

//https://www.pricerunner.dk/dk/api/search-compare-gateway/public/navigation/menu/DK/hierarchy/t23


