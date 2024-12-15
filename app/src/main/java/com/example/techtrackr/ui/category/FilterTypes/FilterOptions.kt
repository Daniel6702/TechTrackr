package com.example.techtrackr.ui.category.FilterTypes

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
fun FilterOptions(
    facet: FilterFacet?,
    parameterValue: String,
    onParameterChange: (String) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }

    fun toggleManufacturer(manufacturerId: String) {
        // Split the parameterValue into a list of manufacturers
        val manufacturers = parameterValue.split(",").toMutableList()

        // Check if the manufacturer is already in the list
        if (manufacturers.contains(manufacturerId)) {
            // Remove the manufacturer if it exists
            manufacturers.remove(manufacturerId)
        } else {
            // Add the manufacturer if it doesn't exist
            manufacturers.add(manufacturerId)
        }

        onParameterChange(manufacturers.joinToString(","))
        Log.d("FilterItem", "Modified $manufacturerId in parameters: $manufacturers")
    }

    fun isCheckedManufacturer(manufacturerId: String): Boolean {
        val manufacturers = parameterValue.split(",").toMutableList()
        if (manufacturers.contains(manufacturerId)) {
            return true
        }
        return false
    }

    if (facet != null && facet.counts != null) {

        val options = if (!expanded) {
            facet.counts.sortedByDescending { it.count }.take(5)
        } else {
            facet.counts
        }

        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp))
        {

            options.forEach { count ->
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                )
                {
                    Checkbox(
                        checked = isCheckedManufacturer(count.optionId.toString()),
                        onCheckedChange = { toggleManufacturer(count.optionId.toString()) },
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = count.optionValue.toString(),
                            modifier = Modifier.padding(start = 8.dp)
                        )

                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = !expanded }
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (expanded) "Minimer" else "Udvid",
                    style = MaterialTheme.typography.bodyMedium
                )
                Icon(
                    imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                    contentDescription = if (expanded) "Collapse" else "Expand"
                )
            }

        }
    }
}