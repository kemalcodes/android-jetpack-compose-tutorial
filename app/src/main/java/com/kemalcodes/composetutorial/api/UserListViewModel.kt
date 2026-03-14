package com.kemalcodes.composetutorial.api

// Tutorial #12: Retrofit — ViewModel
// Calls the API using viewModelScope (auto-cancels)
// Exposes state as StateFlow for Compose to observe
// Handles loading, success, and error states

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// One state object holds everything the screen needs
data class UserListState(
    val users: List<User> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null
)

class UserListViewModel : ViewModel() {

    private val _state = MutableStateFlow(UserListState())
    val state: StateFlow<UserListState> = _state.asStateFlow()

    init {
        loadUsers()
    }

    private fun loadUsers() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            try {
                // Call the API — Retrofit handles threading
                val users = RetrofitClient.userApi.getUsers()
                _state.update { it.copy(users = users, isLoading = false) }
            } catch (e: java.net.UnknownHostException) {
                // No internet connection
                _state.update { it.copy(error = "No internet connection", isLoading = false) }
            } catch (e: java.net.SocketTimeoutException) {
                // Request took too long
                _state.update { it.copy(error = "Request timed out", isLoading = false) }
            } catch (e: Exception) {
                // Any other error
                _state.update {
                    it.copy(error = e.message ?: "Unknown error", isLoading = false)
                }
            }
        }
    }

    fun retry() {
        loadUsers()
    }
}
