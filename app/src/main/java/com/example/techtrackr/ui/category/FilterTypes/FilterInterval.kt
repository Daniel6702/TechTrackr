package com.example.techtrackr.ui.category.FilterTypes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.techtrackr.data.model.FilterFacet


@Composable
fun FilterInterval(
    facet: FilterFacet?,
    parameterValue: String,
    onParameterChange: (String) -> Unit,
) {
    if (facet != null && facet.intervalCounts != null) {

        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp))
        {
            facet.intervalCounts.forEach { count ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    RadioButton(
                        selected = parameterValue == count.interval.toString(),
                        onClick = {
                            onParameterChange(count.interval.toString())
                        }
                    )

                    var intervalText = count.optionValue.toString()
                    when (facet.id) {
                        "RATING" -> intervalText = "Op til ${count.optionValue} stjerner"
                    }

                    Text(
                        text = intervalText,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        }
    }
}