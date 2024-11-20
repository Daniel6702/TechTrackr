package com.example.techtrackr.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.techtrackr.data.model.Category
import com.example.techtrackr.utils.BASE_URL

@Composable
fun CategoryItem(category: Category, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Load image using Coil
        category.image?.path?.let { imagePath ->
            val imageUrl = "$BASE_URL$imagePath" // Replace with your actual base URL
            Image(
                painter = rememberAsyncImagePainter(imageUrl),
                contentDescription = category.image.description,
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
        }

        // Display the category name
        Text(
            text = category.name,
            style = MaterialTheme.typography.titleMedium
        )
    }
}
