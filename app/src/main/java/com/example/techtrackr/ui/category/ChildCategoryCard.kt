package com.example.techtrackr.ui.category

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.techtrackr.data.model.CategoryItem

@Composable
fun ChildCategoryCard(
    childCategory: CategoryItem,
    onClick: (CategoryItem) -> Unit
) {
    androidx.compose.material3.Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick(childCategory) }
    ) {
        Row(
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            val imageUrl = childCategory.image?.path ?: ""
            if (imageUrl.isNotBlank()) {
                coil.compose.AsyncImage(
                    model = com.example.techtrackr.utils.BASE_URL + imageUrl,
                    contentDescription = childCategory.image?.description,
                    modifier = Modifier.size(48.dp)
                )
            } else {
                // If no image, you could show a placeholder or nothing
                Spacer(modifier = Modifier.size(48.dp))
            }
            Spacer(modifier = Modifier.width(16.dp))
            androidx.compose.material3.Text(
                text = childCategory.name,
                style = androidx.compose.material3.MaterialTheme.typography.bodyLarge
            )
        }
    }
}
