package com.kemalcodes.composetutorial.userlist

// Tutorial #9: ViewModel — The UI
// Observes ViewModel state with collectAsStateWithLifecycle.
// Shows loading, error, or user list based on state.
// Search filters users in real time.

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun UserListScreen(
    modifier: Modifier = Modifier,
    viewModel: UserListViewModel = viewModel()
) {
    // collectAsStateWithLifecycle stops collecting when app is in background
    val state by viewModel.state.collectAsStateWithLifecycle()

    Column(modifier = modifier.fillMaxSize()) {
        // Header
        Text(
            text = "Users",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(start = 16.dp, top = 16.dp)
        )

        // Search bar
        OutlinedTextField(
            value = state.searchQuery,
            onValueChange = { viewModel.onSearchQueryChange(it) },
            label = { Text("Search users") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
            trailingIcon = {
                if (state.searchQuery.isNotEmpty()) {
                    IconButton(onClick = { viewModel.onSearchQueryChange("") }) {
                        Icon(Icons.Default.Clear, contentDescription = "Clear")
                    }
                }
            },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        // Content based on state
        when {
            // Loading state
            state.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator()
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Loading users...")
                    }
                }
            }

            // Error state
            state.error != null -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            "Something went wrong",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            state.error ?: "",
                            color = MaterialTheme.colorScheme.error
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = { viewModel.retry() }) {
                            Text("Retry")
                        }
                    }
                }
            }

            // Success state — show filtered user list
            else -> {
                val filteredUsers = state.users.filter {
                    it.name.contains(state.searchQuery, ignoreCase = true) ||
                        it.email.contains(state.searchQuery, ignoreCase = true)
                }

                if (filteredUsers.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "No users found",
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        contentPadding = androidx.compose.foundation.layout.PaddingValues(
                            horizontal = 16.dp
                        )
                    ) {
                        items(filteredUsers, key = { it.id }) { user ->
                            UserRow(
                                user = user,
                                onDelete = { viewModel.deleteUser(user.id) }
                            )
                        }

                        // Footer
                        item {
                            Text(
                                text = "${filteredUsers.size} of ${state.users.size} users",
                                modifier = Modifier.padding(vertical = 16.dp),
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }
    }
}

// UserRow — doesn't know about ViewModel, just displays data and sends events
@Composable
fun UserRow(user: User, onDelete: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Avatar
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = user.name.firstOrNull()?.toString() ?: "?",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        // User info
        Column(modifier = Modifier.weight(1f)) {
            Text(user.name, fontWeight = FontWeight.Medium)
            Text(
                user.email,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        // Delete button
        OutlinedButton(onClick = onDelete) {
            Text("Delete", fontSize = 12.sp)
        }
    }
}
