package com.example.techtrackr.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.techtrackr.data.shared.SharedDataViewModel
import com.example.techtrackr.ui.category.MainCategoryCard
import com.example.techtrackr.utils.MAIN_CATEGORIES

@Composable
fun DrawerContent(
    onHomeClick: () -> Unit,
    sharedDataViewModel: SharedDataViewModel,
    modifier: Modifier = Modifier
) {
    val categoriesState by sharedDataViewModel.categoriesState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxHeight()
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(48.dp))

        Text(
            text = "Categories",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.CenterHorizontally)
                .padding(bottom = 24.dp)
        )

        Divider(
            thickness = 1.dp,
            color = Color.Gray,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onHomeClick() }
                .padding(vertical = 12.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Home,
                contentDescription = "Home",
                tint = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "Home",
                style = MaterialTheme.typography.bodyLarge
            )
        }

        Divider(
            thickness = 1.dp,
            color = Color.Gray,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        if (categoriesState.isEmpty()) {
            // Show loading indicator
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            MAIN_CATEGORIES.keys.forEach { id ->
                val categoryResponse = categoriesState[id]
                if (categoryResponse != null) {
                    MainCategoryCard(
                        category = categoryResponse,
                        onCategoryClick = { /* handle clicks */ }
                    )
                }
            }
        }
    }
}
