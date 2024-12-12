package com.example.techtrackr.utils

const val BASE_URL = "https://www.pricerunner.dk/"

const val BASE_API_URL  = BASE_URL + "dk/api/search-compare-gateway/public/"

val CATEGORY_DATA_URL: (String) -> String = { id -> "$BASE_API_URL/navigation/menu/DK/hierarchy/$id" }

val MAIN_CATEGORIES = mapOf(
    "t23" to "Computerudstyr",
    "t22" to "Computere",
    "t9" to "Computer-hardware",
    "t20" to "Netvaerk",
    "t24" to "Lagring",
    "t1" to "Mobiltelefoner",
    "t1604" to "Hi-Fi-udstyr",
    "t17" to "Foto-Video"
)

//https://www.pricerunner.dk/dk/api/search-compare-gateway/public/navigation/menu/DK/hierarchy/t23


