// AppNavigation.kt — Sets up the navigation graph for the app.
// We use a bottom navigation bar with two tabs: Tasks and Add.
// The NavHost handles switching between screens based on the current route.
package com.kemalcodes.composetutorial.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.kemalcodes.composetutorial.ui.screens.addtask.AddTaskScreen
import com.kemalcodes.composetutorial.ui.screens.tasklist.TaskListScreen
import kotlinx.serialization.Serializable

// Type-safe routes using kotlinx.serialization
@Serializable object TaskListRoute
@Serializable object AddTaskRoute

// Data class representing a bottom navigation tab
data class BottomNavItem(
    val label: String,
    val icon: ImageVector,
    val route: Any
)

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    // Define the two bottom navigation tabs
    val bottomNavItems = listOf(
        BottomNavItem("Tasks", Icons.AutoMirrored.Filled.List, TaskListRoute),
        BottomNavItem("Add", Icons.Default.Add, AddTaskRoute)
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                bottomNavItems.forEach { item ->
                    NavigationBarItem(
                        icon = { Icon(item.icon, contentDescription = item.label) },
                        label = { Text(item.label) },
                        // Highlight the tab that matches the current route
                        selected = currentDestination?.hasRoute(item.route::class) == true,
                        onClick = {
                            navController.navigate(item.route) {
                                // Pop back to start to avoid building up a large back stack
                                popUpTo(TaskListRoute) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        // The NavHost decides which screen to show based on the current route
        NavHost(
            navController = navController,
            startDestination = TaskListRoute,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable<TaskListRoute> {
                TaskListScreen()
            }
            composable<AddTaskRoute> {
                AddTaskScreen(
                    onTaskSaved = {
                        // After saving, go back to the task list
                        navController.navigate(TaskListRoute) {
                            popUpTo(TaskListRoute) { inclusive = true }
                        }
                    }
                )
            }
        }
    }
}
