package com.example.techtrackr.ui.watchlist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.techtrackr.data.model.Product
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WatchlistViewModel : ViewModel() {

    private val _watchlist = MutableStateFlow<List<WatchlistProduct>>(emptyList())
    val watchlist: StateFlow<List<WatchlistProduct>> = _watchlist

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val firestore = FirebaseFirestore.getInstance()
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var watchlistListenerRegistration: ListenerRegistration? = null

    init {
        fetchWatchlist()
    }

    private fun fetchWatchlist() {
        val currentUser = auth.currentUser
        if (currentUser == null || currentUser.isAnonymous) {
            _errorMessage.value = "You must be logged in to view the watchlist."
            _isLoading.value = false
            return
        }

        val watchlistCollection = firestore.collection("users")
            .document(currentUser.uid)
            .collection("watchlist")

        watchlistListenerRegistration = watchlistCollection.addSnapshotListener { snapshot, exception ->
            if (exception != null) {
                Log.e("WatchlistViewModel", "Error fetching watchlist: ${exception.message}")
                _errorMessage.value = "Failed to load watchlist."
                _isLoading.value = false
                return@addSnapshotListener
            }

            if (snapshot != null) {
                val watchlistProducts = snapshot.documents.mapNotNull { doc ->
                    doc.toObject(WatchlistProduct::class.java)
                }

                _watchlist.value = watchlistProducts
                _isLoading.value = false
            }
        }
    }

    fun removeFromWatchlist(productId: String) {
        viewModelScope.launch {
            val currentUser = auth.currentUser
            if (currentUser == null || currentUser.isAnonymous) {
                _errorMessage.value = "You must be logged in to modify the watchlist."
                return@launch
            }

            val watchlistDocRef = firestore.collection("users")
                .document(currentUser.uid)
                .collection("watchlist")
                .document(productId)

            watchlistDocRef.delete()
                .addOnSuccessListener {
                    // Firestore snapshot listener will automatically update the watchlist
                }
                .addOnFailureListener { exception ->
                    Log.e("WatchlistViewModel", "Error removing from watchlist: ${exception.message}")
                    _errorMessage.value = "Failed to remove from watchlist."
                }
        }
    }

    override fun onCleared() {
        super.onCleared()
        watchlistListenerRegistration?.remove()
    }
}

