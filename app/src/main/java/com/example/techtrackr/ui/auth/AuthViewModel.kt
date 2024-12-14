// AuthViewModel.kt
package com.example.techtrackr

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class AuthUIState(
    val email: String = "",
    val password: String = "",
    val isLogin: Boolean = true,
    val isLoading: Boolean = false
)

class AuthViewModel(private val auth: FirebaseAuth, private val context: Context) : ViewModel() {
    private val _uiState = MutableStateFlow(AuthUIState())
    val uiState: StateFlow<AuthUIState> = _uiState

    fun updateEmail(newEmail: String) {
        _uiState.value = _uiState.value.copy(email = newEmail)
    }

    fun updatePassword(newPassword: String) {
        _uiState.value = _uiState.value.copy(password = newPassword)
    }

    fun toggleLoginMode() {
        _uiState.value = _uiState.value.copy(isLogin = !_uiState.value.isLogin)
    }

    fun loginUser(onResult: (Boolean) -> Unit) {
        val state = _uiState.value
        val email = state.email.trim()
        val password = state.password

        if (email.isBlank()) {
            toast("Email cannot be empty")
            onResult(false)
            return
        }
        if (!isValidEmail(email)) {
            toast("Invalid email format")
            onResult(false)
            return
        }
        if (password.isBlank()) {
            toast("Password cannot be empty")
            onResult(false)
            return
        }

        setLoading(true)
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                setLoading(false)
                if (task.isSuccessful) {
                    Log.d("AuthViewModel", "User logged in: ${auth.currentUser?.uid}, isAnonymous: ${auth.currentUser?.isAnonymous}")
                    onResult(true)
                } else {
                    toast(task.exception?.message ?: "Unknown error")
                    onResult(false)
                }
            }

    }

    fun signUpUser(onResult: (Boolean) -> Unit) {
        val state = _uiState.value
        val email = state.email.trim()
        val password = state.password

        if (email.isBlank()) {
            toast("Email cannot be empty")
            onResult(false)
            return
        }
        if (!isValidEmail(email)) {
            toast("Invalid email format")
            onResult(false)
            return
        }
        if (password.isBlank()) {
            toast("Password cannot be empty")
            onResult(false)
            return
        }

        setLoading(true)
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                setLoading(false)
                if (task.isSuccessful) {
                    toast("Sign-up successful")
                    Log.d("AuthViewModel", "User signed up: ${auth.currentUser?.uid}, isAnonymous: ${auth.currentUser?.isAnonymous}")

                    // **Key Modification:** Switch to login mode after successful registration
                    _uiState.value = _uiState.value.copy(isLogin = true, email = "", password = "")
                    onResult(true)
                } else {
                    toast(task.exception?.message ?: "Unknown error")
                    onResult(false)
                }
            }
    }

    fun loginAsGuest(onResult: (Boolean) -> Unit) {
        setLoading(true)
        auth.signInAnonymously()
            .addOnCompleteListener { task ->
                setLoading(false)
                if (task.isSuccessful) {
                    Log.d("AuthViewModel", "User logged in as guest: ${auth.currentUser?.uid}, isAnonymous: ${auth.currentUser?.isAnonymous}")
                    onResult(true)
                } else {
                    toast(task.exception?.message ?: "Unknown error")
                    onResult(false)
                }
            }

    }

    private fun setLoading(loading: Boolean) {
        _uiState.value = _uiState.value.copy(isLoading = loading)
    }

    fun toast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}

class AuthViewModelFactory(
    private val auth: FirebaseAuth,
    private val context: Context
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AuthViewModel(auth, context) as T
    }
}
