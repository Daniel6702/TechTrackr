package com.example.techtrackr.ui.category

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun FilterDialog(
    viewModel: CategoryViewModel,
    closeDialog: () -> Unit
) {
    val isDialogOpen = remember { mutableStateOf(false) }

    Dialog(
        onDismissRequest = { isDialogOpen.value = false }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Black.copy(alpha = 0.0f)),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.9f) // Limit dialog height to 90% of the screen
                    .background(color = Color.White, shape = RoundedCornerShape(16.dp))
                    .padding(16.dp)
                    .align(Alignment.Center) // Center the dialog content
            ) {
                // Title
                Text(
                    text = "Filters",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // LazyColumn for filters
                LazyColumn(
                    modifier = Modifier
                        .weight(1f) // Let LazyColumn take all available vertical space
                        .fillMaxWidth()
                ) {
                    items(viewModel.filters) { filter ->
                        val facet = viewModel.facets[filter.id]
                        val parameterValue = viewModel.parameters[filter.id] ?: ""
                        Log.d("CategoryScreen", "Calling FilterItem: ${filter.name} ${filter.type}, ${facet?.type}")

                        FilterItem(
                            filter = filter,
                            facet = facet,
                            parameterValue = parameterValue,
                            onParameterChange = { value ->
                                viewModel.setParameters(filter.id, value)
                            }
                        )
                    }
                }

                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 8.dp),
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
                )

                // Sticky Buttons
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextButton(
                        onClick = {
                            closeDialog()
                            viewModel.resetParameters()
                            viewModel.loadDeals()
                        }
                    ) {
                        Text("Nulstil")
                    }

                    Button(
                        onClick = {
                            closeDialog()
                            viewModel.loadDeals()
                        }
                    ) {
                        Text("Anvend")
                    }
                }
            }
        }
    }
}