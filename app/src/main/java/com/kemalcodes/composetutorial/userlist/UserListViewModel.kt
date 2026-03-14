package com.kemalcodes.composetutorial.userlist

// Tutorial #9: ViewModel — The brain of the screen
// Holds data, handles logic, survives rotation.
// Uses StateFlow to expose state that Compose observes.
// Uses viewModelScope for coroutines that auto-cancel.

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UserListViewModel : ViewModel() {

    // Private mutable state — only ViewModel can change it
    private val _state = MutableStateFlow(UserListState())

    // Public read-only state — Compose observes this
    val state: StateFlow<UserListState> = _state.asStateFlow()

    init {
        // Load data when ViewModel is created (once, not on every rotation)
        loadUsers()
    }

    private fun loadUsers() {
        // viewModelScope auto-cancels when ViewModel is destroyed
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            try {
                // Simulate network delay
                delay(1000)

                val users = listOf(
                    User("1", "Alex", "alex@example.com"),
                    User("2", "Sam", "sam@example.com"),
                    User("3", "Jordan", "jordan@example.com"),
                    User("4", "Taylor", "taylor@example.com"),
                    User("5", "Morgan", "morgan@example.com"),
                    User("6", "Casey", "casey@example.com"),
                    User("7", "Riley", "riley@example.com"),
                    User("8", "Quinn", "quinn@example.com"),
                )

                // Update state with new data — .copy() creates a new state object
                _state.update { it.copy(users = users, isLoading = false) }
            } catch (e: Exception) {
                _state.update { it.copy(error = e.message, isLoading = false) }
            }
        }
    }

    // Called when user types in the search bar
    fun onSearchQueryChange(query: String) {
        _state.update { it.copy(searchQuery = query) }
    }

    // Called when user deletes a user
    fun deleteUser(userId: String) {
        _state.update { currentState ->
            currentState.copy(
                users = currentState.users.filter { it.id != userId }
            )
        }
    }

    // Called when user taps retry after an error
    fun retry() {
        loadUsers()
    }
}
