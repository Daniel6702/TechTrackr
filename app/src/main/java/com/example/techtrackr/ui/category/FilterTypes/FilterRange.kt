package com.example.techtrackr.ui.category.FilterTypes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.techtrackr.data.model.FilterFacet

@Composable
fun FilterRange(
    facet: FilterFacet?,
    parameterValue: String,
    onParameterChange: (String) -> Unit,
) {
    if (facet != null) {
        // Handle case where maximum is not set
        val range = if (facet.maximum != null && facet.maximum > 0) {
            0.0f..facet.maximum.toFloat()
        } else {
            0.0f..100.0f // Default range
        }

        val sliderValue = (if (parameterValue.isNotEmpty())
            parameterValue
                .removePrefix("0_")
                .toFloat()
        else 0.0f)
            .coerceIn(range.start, range.endInclusive)

        Text(
            text = "${sliderValue.toLong()} ${facet.unit}",
        )

        Slider(
            value = sliderValue,
            onValueChange = { onParameterChange("0_${it.toLong().toString()}") },
            valueRange = range,
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "${range.start.toInt()} ${facet.unit}")
            Text(text = "${range.endInclusive.toInt()} ${facet.unit}")
        }
    }
}