package com.kemalcodes.composetutorial.userlist

// Tutorial #9: ViewModel — State
// One data class holds everything the screen needs to display.
// This is the same "one screen = one state" rule from MVI (Tutorial #10).

data class UserListState(
    val users: List<User> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null,
    val searchQuery: String = ""
)
