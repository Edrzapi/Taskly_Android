package uk.co.devfoundry.taskly

import androidx.compose.runtime.Composable
import uk.co.devfoundry.taskly.ui.screen.TaskListScreen
import uk.co.devfoundry.taskly.ui.theme.TasklyTheme
import androidx.compose.runtime.*
import uk.co.devfoundry.taskly.ui.screen.TaskDetailScreen

@Composable
fun TasklyApp() {
    TasklyTheme {
        var currentScreen by remember { mutableStateOf("task_list") }
        var selectedTaskId by remember { mutableStateOf<String?>(null) }

        when (currentScreen) {
            "task_list" -> TaskListScreen(
                onTaskClick = { taskId ->
                    selectedTaskId = taskId
                    currentScreen = "task_detail"
                }
            )
            "task_detail" -> TaskDetailScreen(
                taskId = selectedTaskId ?: "",
                onMarkComplete = { currentScreen = "task_list" },
                onBack = { currentScreen = "task_list" }
            )
        }
    }
}
