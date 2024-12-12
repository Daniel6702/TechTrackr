// AuthenticationScreen.kt
package com.example.techtrackr

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun AuthenticationScreen(
    uiState: AuthUIState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onToggleMode: () -> Unit,
    onLoginClick: () -> Unit,
    onSignUpClick: () -> Unit,
    onLoginAsGuestClick: () -> Unit,
    modifier: Modifier = Modifier
) {
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
                onValueChange = onEmailChange,
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
                                modifier = Modifier
                                    .align(Alignment.CenterStart)
                                    .padding(vertical = 0.dp) // Ensure no extra vertical padding
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
                onValueChange = onPasswordChange,
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
                                modifier = Modifier
                                    .align(Alignment.CenterStart)
                                    .padding(vertical = 0.dp) // Ensure no extra vertical padding
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
                    onLoginClick()
                } else {
                    onSignUpClick()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = if (uiState.isLogin) "Login" else "Sign Up")
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Toggle between login and signup
        TextButton(onClick = onToggleMode) {
            Text(text = if (uiState.isLogin) "Don't have an account? Sign up" else "Already have an account? Login")
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Login as Guest
        OutlinedButton(
            onClick = onLoginAsGuestClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login as Guest")
        }
    }
}
