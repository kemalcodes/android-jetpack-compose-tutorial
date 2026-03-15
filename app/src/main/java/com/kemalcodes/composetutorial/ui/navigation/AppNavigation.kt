// AppNavigation.kt — Navigation graph with slide animations between screens.
// Includes three tabs: Tasks, Add, and Settings.
// Transition animations make screen changes feel smooth and polished.
package com.kemalcodes.composetutorial.ui.navigation

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.kemalcodes.composetutorial.ui.screens.addtask.AddTaskScreen
import com.kemalcodes.composetutorial.ui.screens.settings.SettingsScreen
import com.kemalcodes.composetutorial.ui.screens.tasklist.TaskListScreen
import kotlinx.serialization.Serializable

// Type-safe routes
@Serializable object TaskListRoute
@Serializable object AddTaskRoute
@Serializable object SettingsRoute

data class BottomNavItem(
    val label: String,
    val icon: ImageVector,
    val route: Any
)

@Composable
fun AppNavigation(
    isDarkMode: Boolean,
    onDarkModeToggle: (Boolean) -> Unit
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val bottomNavItems = listOf(
        BottomNavItem("Tasks", Icons.AutoMirrored.Filled.List, TaskListRoute),
        BottomNavItem("Add", Icons.Default.Add, AddTaskRoute),
        BottomNavItem("Settings", Icons.Default.Settings, SettingsRoute)
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                bottomNavItems.forEach { item ->
                    NavigationBarItem(
                        icon = { Icon(item.icon, contentDescription = item.label) },
                        label = { Text(item.label) },
                        selected = currentDestination?.hasRoute(item.route::class) == true,
                        onClick = {
                            navController.navigate(item.route) {
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
        NavHost(
            navController = navController,
            startDestination = TaskListRoute,
            modifier = Modifier.padding(innerPadding),
            // Slide-in and slide-out animations for screen transitions
            enterTransition = { slideInHorizontally(initialOffsetX = { it }) },
            exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) },
            popEnterTransition = { slideInHorizontally(initialOffsetX = { -it }) },
            popExitTransition = { slideOutHorizontally(targetOffsetX = { it }) }
        ) {
            composable<TaskListRoute> {
                TaskListScreen()
            }
            composable<AddTaskRoute> {
                AddTaskScreen(
                    onTaskSaved = {
                        navController.navigate(TaskListRoute) {
                            popUpTo(TaskListRoute) { inclusive = true }
                        }
                    }
                )
            }
            composable<SettingsRoute> {
                SettingsScreen(
                    isDarkMode = isDarkMode,
                    onDarkModeToggle = onDarkModeToggle
                )
            }
        }
    }
}
