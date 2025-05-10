package uk.co.devfoundry.taskly

import androidx.compose.runtime.*
import uk.co.devfoundry.taskly.data.model.Task
import uk.co.devfoundry.taskly.ui.screen.TaskDetailScreen
import uk.co.devfoundry.taskly.ui.screen.TaskListScreen
import uk.co.devfoundry.taskly.ui.theme.TasklyTheme

@Composable
fun TasklyApp() {
    TasklyTheme {
        // Screen state: null = show list, else = show details for that ID
        var selectedTaskId by remember { mutableStateOf<String?>(null) }

        // Task list with ID, title, and completion status
        var tasks by remember { mutableStateOf(listOf<Task>()) }

        // Show the task list if no task is selected
        if (selectedTaskId == null) {
            TaskListScreen(
                tasks = tasks,
                onTaskClick = { taskId ->
                    selectedTaskId = taskId
                },
                onAddTask = { title ->
                    tasks = tasks + Task(title = title)
                }
            )
        } else {
            // Look up the selected task by ID
            val selectedTask = tasks.find { it.id == selectedTaskId }

            // Only show the detail screen if the task exists
            if (selectedTask != null) {
                TaskDetailScreen(
                    task = selectedTask,
                    onMarkComplete = {
                        tasks = tasks.map {
                            if (it.id == selectedTask.id) it.copy(isComplete = true) else it
                        }
                        selectedTaskId = null
                    },
                    onBack = {
                        selectedTaskId = null
                    }
                )
            }
        }
    }
}
