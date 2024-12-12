package com.example.techtrackr.ui.category

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import coil.compose.AsyncImage
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.techtrackr.data.model.CategoryResponse
import com.example.techtrackr.utils.BASE_URL

@Composable
fun MainCategoryCard(
    category: CategoryResponse,
    onCategoryClick: (CategoryResponse) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onCategoryClick(category) }
    ) {
        Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically, modifier = Modifier.padding(8.dp)) {
            AsyncImage(
                model = BASE_URL + category.image.path,
                contentDescription = category.image.description,
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = category.name,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
