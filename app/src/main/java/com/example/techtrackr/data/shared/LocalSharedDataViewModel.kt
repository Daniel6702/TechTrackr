package com.example.techtrackr.data.shared

import androidx.compose.runtime.compositionLocalOf

val LocalSharedDataViewModel = compositionLocalOf<SharedDataViewModel> {
    error("No SharedDataViewModel provided")
}
