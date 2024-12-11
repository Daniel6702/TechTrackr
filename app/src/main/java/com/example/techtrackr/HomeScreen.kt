// HomeScreen.kt
package com.example.techtrackr

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun HomeScreen(onLogout: () -> Unit, modifier: Modifier = Modifier) {
    // Simple Home Screen UI
    Button(
        onClick = {
            // Handle logout logic here
            onLogout()
        },
        modifier = modifier
    ) {
        Text(text = "Log out")
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen(onLogout = {})
}
