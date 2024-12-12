package com.example.techtrackr

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(onLogout: () -> Unit, modifier: Modifier = Modifier) {
    // Simple Home Screen UI
    Column(modifier = modifier) {
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = {
                onLogout()
            },
            modifier = modifier
        ) {
            Text(text = "Log out")
        }
    }
}
