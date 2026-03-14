package com.kemalcodes.composetutorial.navigation

// Tutorial #8: Navigation — The Main Navigation Setup
// Combines bottom navigation bar + nested navigation graph.
// Home tab has its own list→detail flow. Other tabs are single screens.

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute

// Bottom navigation tab definition
data class BottomNavItem(
    val label: String,
    val icon: ImageVector,
    val route: Any  // The @Serializable route object
)

val bottomNavItems = listOf(
    BottomNavItem("Home", Icons.Default.Home, HomeTab),
    BottomNavItem("Search", Icons.Default.Search, SearchTab),
    BottomNavItem("Profile", Icons.Default.Person, ProfileTab),
)

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                bottomNavItems.forEach { item ->
                    NavigationBarItem(
                        // Check if this tab is currently active
                        selected = currentBackStackEntry?.destination?.route
                            ?.contains(item.route::class.qualifiedName ?: "") == true,
                        onClick = {
                            navController.navigate(item.route) {
                                // Pop up to start to avoid building a huge back stack
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                // Don't create duplicate tabs
                                launchSingleTop = true
                                // Remember state when switching tabs
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(item.icon, contentDescription = item.label)
                        },
                        label = { Text(item.label) }
                    )
                }
            }
        }
    ) { innerPadding ->
        // NavHost — defines all screens and how to navigate between them
        NavHost(
            navController = navController,
            startDestination = HomeTab,
            modifier = Modifier.padding(innerPadding)
        ) {
            // --- Home Tab: nested navigation (list → detail) ---
            navigation<HomeTab>(startDestination = HomeList) {
                composable<HomeList> {
                    HomeListScreen(
                        onItemClick = { itemId ->
                            // Navigate to detail, passing the item ID
                            navController.navigate(HomeDetail(itemId = itemId))
                        }
                    )
                }
                composable<HomeDetail> { backStackEntry ->
                    // Extract the type-safe argument
                    val detail = backStackEntry.toRoute<HomeDetail>()
                    HomeDetailScreen(
                        itemId = detail.itemId,
                        onBack = { navController.popBackStack() }
                    )
                }
            }

            // --- Search Tab: single screen ---
            composable<SearchTab> {
                SearchTabScreen()
            }

            // --- Profile Tab: single screen ---
            composable<ProfileTab> {
                ProfileTabScreen()
            }
        }
    }
}
