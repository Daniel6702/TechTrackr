package com.example.techtrackr.ui.category

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.techtrackr.data.model.Filter
import com.example.techtrackr.data.model.FilterFacet
import com.example.techtrackr.ui.category.FilterTypes.FilterInterval
import com.example.techtrackr.ui.category.FilterTypes.FilterOptions
import com.example.techtrackr.ui.category.FilterTypes.FilterRange

@Composable
fun FilterItem(
    filter: Filter,
    facet: FilterFacet?,
    parameterValue: String,
    onParameterChange: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp) // vertical padding inside the LazyRow item
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = filter.name,
                style = MaterialTheme.typography.headlineSmall
            )

            if (facet != null) {

                when (facet.type) {
                    "OPTIONS" -> FilterOptions(
                        facet = facet,
                        parameterValue = parameterValue,
                        onParameterChange = onParameterChange,
                    )


                    "RANGE" -> {
                        FilterRange(
                            facet = facet,
                            parameterValue = parameterValue,
                            onParameterChange = onParameterChange,
                        )
                    }

                    "INTERVAL" -> {
                        FilterInterval(
                            facet = facet,
                            parameterValue = parameterValue,
                            onParameterChange = onParameterChange,
                        )
                    }

                    else -> Text(
                        text = filter.type,
                    )
                }
            }
        }
    }
}
