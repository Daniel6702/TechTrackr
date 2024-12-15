package com.example.techtrackr.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.techtrackr.data.shared.SharedDataViewModel
import com.example.techtrackr.ui.category.CategoryList

@Composable
fun DrawerContent(
    onHomeClick: () -> Unit,
    sharedDataViewModel: SharedDataViewModel,
    modifier: Modifier = Modifier
) {
    val categoriesState by sharedDataViewModel.categoriesState.collectAsState()
    val navController = LocalNavController.current

    Column(
        modifier = modifier
            .fillMaxHeight()
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(48.dp))

        Text(
            text = "Kategorier",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface,
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

        // Home Button Centered Horizontally
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center, // Center contents horizontally
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onHomeClick()
                }
                .padding(vertical = 12.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Home,
                contentDescription = "Hjem",
                tint = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "Home",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        Divider(
            thickness = 1.dp,
            color = Color.Gray,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        CategoryList(
            categoriesState = categoriesState,
            navController = navController
        )

        Spacer(modifier = Modifier.weight(1f))

        Divider(
            thickness = 1.dp,
            color = Color.Gray,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    navController.navigate("watchlist")
                }
                .padding(vertical = 12.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.List,
                contentDescription = "Watchlist",
                tint = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "Watchlist",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}
