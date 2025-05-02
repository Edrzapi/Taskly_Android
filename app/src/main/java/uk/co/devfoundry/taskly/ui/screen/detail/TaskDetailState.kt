package uk.co.devfoundry.taskly.ui.screen.detail

import uk.co.devfoundry.taskly.data.model.Task

data class TaskDetailState(
    val task: Task? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
