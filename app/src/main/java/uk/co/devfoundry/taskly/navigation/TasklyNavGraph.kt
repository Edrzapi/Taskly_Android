package uk.co.devfoundry.taskly.navigation

import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavType
import androidx.navigation.navArgument
import uk.co.devfoundry.taskly.data.repository.TaskRepository
import uk.co.devfoundry.taskly.ui.screen.SettingsScreen
import uk.co.devfoundry.taskly.ui.screen.TaskDetailScreen
import uk.co.devfoundry.taskly.ui.screen.TaskListScreen
import uk.co.devfoundry.taskly.ui.screen.detail.TaskDetailViewModel
import uk.co.devfoundry.taskly.ui.screen.list.TaskListViewModel

@Composable
fun TasklyNav(
    navController: NavHostController,
    repository: TaskRepository,
    listViewModel: TaskListViewModel,
    darkModeEnabled: Boolean,
    onDarkModeToggle: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = Routes.HOME
    ) {
        // Home / Task list
        composable(Routes.HOME) {
            TaskListScreen(
                viewModel = listViewModel,
                onTaskClick = { id ->
                    navController.navigate(Routes.detailRoute(id))
                },
                onSettingsClick = {
                    navController.navigate(Routes.SETTINGS)
                }
            )
        }

        composable(
            route = Routes.TASK_DETAIL,
            arguments = listOf(navArgument("taskId") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments!!.getString("taskId")!!
            val detailViewModel = remember(taskId) {
                TaskDetailViewModel(repository, taskId)
            }
            TaskDetailScreen(
                viewModel = detailViewModel,
                onBack = { navController.popBackStack() }
            )
        }

        // Settings screen
        composable(Routes.SETTINGS) {
            SettingsScreen(
                darkModeEnabled = darkModeEnabled,
                onToggleDarkMode = onDarkModeToggle,
                onBack = { navController.popBackStack() }
            )

        }
    }
}

