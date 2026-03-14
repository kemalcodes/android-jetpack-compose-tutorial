package com.kemalcodes.composetutorial.navigation

// Tutorial #8: Navigation — Moving Between Screens
// All routes in one file — easy to see every screen in the app at a glance.
// Using @Serializable for type-safe navigation (no string routes).

import kotlinx.serialization.Serializable

// --- Bottom Navigation Tab Routes ---

@Serializable
object HomeTab          // Home tab in bottom bar

@Serializable
object SearchTab        // Search tab in bottom bar

@Serializable
object ProfileTab       // Profile tab in bottom bar

// --- Screens Inside Home Tab (Nested Navigation) ---

@Serializable
object HomeList         // List of items on the Home tab

@Serializable
data class HomeDetail(  // Detail screen — needs an item ID
    val itemId: String
)
