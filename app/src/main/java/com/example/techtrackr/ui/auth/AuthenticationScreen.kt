// AuthenticationScreen.kt
package com.example.techtrackr

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.techtrackr.ui.navigation.LocalNavController

@Composable
fun AuthenticationScreen(
    viewModel: AuthViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val navController = LocalNavController.current

    fun navigateToHome() {
        navController.navigate("home") {
            popUpTo("auth") { inclusive = true }
        }

    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = if (uiState.isLogin) "Log ind" else "Tilmeld dig",
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Email Input
        Box(modifier = Modifier.fillMaxWidth()) {
            BasicTextField(
                value = uiState.email,
                onValueChange = { viewModel.updateEmail(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color.Gray, shape = MaterialTheme.shapes.small)
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                singleLine = true,
                cursorBrush = SolidColor(Color.Black),
                textStyle = MaterialTheme.typography.bodyMedium.copy(color = Color.Black),
                decorationBox = { innerTextField ->
                    Box {
                        if (uiState.email.isEmpty()) {
                            Text(
                                text = "Email",
                                color = Color.Gray,
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.align(Alignment.CenterStart)
                            )
                        }
                        innerTextField()
                    }
                }
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Password Input
        Box(modifier = Modifier.fillMaxWidth()) {
            BasicTextField(
                value = uiState.password,
                onValueChange = { viewModel.updatePassword(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color.Gray, shape = MaterialTheme.shapes.small)
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                cursorBrush = SolidColor(Color.Black),
                textStyle = MaterialTheme.typography.bodyMedium.copy(color = Color.Black),
                decorationBox = { innerTextField ->
                    Box {
                        if (uiState.password.isEmpty()) {
                            Text(
                                text = "Adgangskode",
                                color = Color.Gray,
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.align(Alignment.CenterStart)
                            )
                        }
                        innerTextField()
                    }
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Loading indicator
        if (uiState.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Action Button (Login or Sign Up)
        Button(
            onClick = {
                if (uiState.isLogin) {
                    viewModel.loginUser { success ->
                        if (success) {
                            navigateToHome()
                        } else {
                            viewModel.updatePassword("")
                        }
                    }
                } else {
                    viewModel.signUpUser { success ->
                        if (success) navigateToHome()
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = if (uiState.isLogin) "Log ind" else "Tilmeld dig")
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Toggle between login and signup
        TextButton(onClick = { viewModel.toggleLoginMode() }) {
            Text(text = if (uiState.isLogin) "Har du ikke en konto? Tilmeld dig" else "Har du allerede en konto? Log ind\n")
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Login as Guest
        OutlinedButton(
            onClick = { viewModel.loginAsGuest { navigateToHome() } },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Log ind som Gæst")
        }
    }


}
