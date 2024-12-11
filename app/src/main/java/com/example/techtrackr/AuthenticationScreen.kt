// AuthenticationScreen.kt
package com.example.techtrackr

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth

@Composable
fun AuthenticationScreen(
    modifier: Modifier = Modifier,
    auth: FirebaseAuth,
    context: Context,
    onLoginSuccess: () -> Unit // Callback to navigate to home
) {
    // State variables to hold the email, password, visibility, and loading state
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) } // For toggling password visibility
    var isLogin by remember { mutableStateOf(true) } // To switch between login and sign-up modes
    var isLoading by remember { mutableStateOf(false) } // To show the loading indicator when processing login/signup

    // The main container for  UI, which fills the screen and centers the content
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        // Display the title, either "Login" or "Sign Up" based on the current mode
        Text(text = if (isLogin) "Login" else "Sign Up", style = MaterialTheme.typography.headlineLarge)

        Spacer(modifier = Modifier.height(16.dp))

        // Email Input Box
        Box(modifier = Modifier.fillMaxWidth()) {
            // Display the label "Email" if the email field is empty
            if (email.isEmpty()) {
                Text("Email", modifier = Modifier.padding(start = 16.dp, top = 12.dp), color = Color.Gray)
            }
            // BasicTextField to allow the user to input the email
            BasicTextField(
                value = email,
                onValueChange = { email = it }, // Update email state as the user types
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .border(1.dp, Color.Gray), // Simple border around the input field
                singleLine = true, // Ensure only one line for the email input
                decorationBox = { innerTextField ->
                    Row(modifier = Modifier.padding(16.dp)) {
                        innerTextField() // The inner text field where the user types the email
                    }
                }
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Password Input
        BasicTextField(
            value = password,
            onValueChange = { password = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .border(1.dp, Color.Gray),
            visualTransformation = PasswordVisualTransformation(),
            decorationBox = { innerTextField ->
                Row(modifier = Modifier.padding(16.dp)) {
                    Text("Password:")
                    innerTextField()
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Loading indicator
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                // Trim any leading/trailing whitespace from email
                val trimmedEmail = email.trim()

                // Validate if the email is empty or badly formatted
                if (trimmedEmail.isBlank()) {
                    Toast.makeText(context, "Email cannot be empty", Toast.LENGTH_SHORT).show()
                    return@Button
                }

                // Optional: Validate email format (if needed)
                if (!isValidEmail(trimmedEmail)) {
                    Toast.makeText(context, "Invalid email format", Toast.LENGTH_SHORT).show()
                    return@Button
                }

                // Check if the password is not empty
                if (password.isBlank()) {
                    Toast.makeText(context, "Password cannot be empty", Toast.LENGTH_SHORT).show()
                    return@Button
                }

                // Start loading
                isLoading = true

                // Proceed with login or sign-up based on the state of isLogin
                if (isLogin) {
                    loginUser(auth, trimmedEmail, password, context) { success, message ->
                        isLoading = false
                        if (success) {
                            // Trigger login success callback
                            onLoginSuccess()
                        } else {
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    signUpUser(auth, trimmedEmail, password, context) { success, message ->
                        isLoading = false
                        if (success) {
                            Toast.makeText(context, "Sign-up successful", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = if (isLogin) "Login" else "Sign Up")
        }


        Spacer(modifier = Modifier.height(8.dp))

        // Switch between Login/Sign-up
        TextButton(onClick = { isLogin = !isLogin }) {
            Text(text = if (isLogin) "Don't have an account? Sign up" else "Already have an account? Login")
        }
    }
}

fun loginUser(auth: FirebaseAuth, email: String, password: String, context: Context, callback: (Boolean, String) -> Unit) {
    val trimmedEmail = email.trim() // Ensure no leading/trailing spaces
    auth.signInWithEmailAndPassword(trimmedEmail, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                callback(true, "Login successful")
            } else {
                callback(false, task.exception?.message ?: "Unknown error")
            }
        }
}


fun signUpUser(auth: FirebaseAuth, email: String, password: String, context: Context, callback: (Boolean, String) -> Unit) {
    val trimmedEmail = email.trim() // Ensure no leading/trailing spaces
    auth.createUserWithEmailAndPassword(trimmedEmail, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                callback(true, "Sign-up successful")
            } else {
                callback(false, task.exception?.message ?: "Unknown error")
            }
        }
}


fun isValidEmail(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}
