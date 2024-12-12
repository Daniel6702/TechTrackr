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

@Composable
fun AuthenticationScreen(
    viewModel: AuthViewModel,
    onNavigateToHome: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = if (uiState.isLogin) "Login" else "Sign Up",
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
                                text = "Password",
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
                    viewModel.loginUser(onNavigateToHome)
                } else {
                    viewModel.signUpUser(onNavigateToHome)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = if (uiState.isLogin) "Login" else "Sign Up")
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Toggle between login and signup
        TextButton(onClick = { viewModel.toggleLoginMode() }) {
            Text(text = if (uiState.isLogin) "Don't have an account? Sign up" else "Already have an account? Login")
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Login as Guest
        OutlinedButton(
            onClick = { viewModel.loginAsGuest(onNavigateToHome) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login as Guest")
        }
    }
}
