package com.example.techtrackr.ui.category

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.techtrackr.data.model.CategoryItem
import com.example.techtrackr.utils.BASE_URL

@Composable
fun ChildCategoryCard(
    childCategory: CategoryItem,
    onClick: (CategoryItem) -> Unit
) {
    Card(
        modifier = Modifier
            .width(200.dp)
            .padding(vertical = 8.dp) // vertical padding inside the LazyRow item
            .clickable { onClick(childCategory) }
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            val imageUrl = childCategory.image?.path ?: ""
            if (imageUrl.isNotBlank()) {
                AsyncImage(
                    model = BASE_URL + imageUrl,
                    contentDescription = childCategory.image?.description,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                )
            } else {
                Spacer(modifier = Modifier.height(100.dp))
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = childCategory.name,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
