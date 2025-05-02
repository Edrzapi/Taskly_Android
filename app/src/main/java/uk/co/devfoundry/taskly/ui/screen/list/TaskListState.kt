package uk.co.devfoundry.taskly.ui.screen.list

import uk.co.devfoundry.taskly.data.model.Task

data class TaskListState(
    val tasks: List<Task> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
