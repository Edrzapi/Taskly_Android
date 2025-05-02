package uk.co.devfoundry.taskly.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import uk.co.devfoundry.taskly.data.repository.TaskRepository

/**
 * Loads a single Task by ID and allows marking it complete.
 */
class TaskDetailViewModel(
    private val repository: TaskRepository,
    private val taskId: String
) : ViewModel() {

    private val _uiState = MutableStateFlow(TaskDetailState(isLoading = true))
    val uiState: StateFlow<TaskDetailState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getTasks()
                .map { list -> list.find { it.id == taskId } }
                .collect { taskOrNull ->
                    _uiState.value = when {
                        taskOrNull != null -> TaskDetailState(task = taskOrNull)
                        else -> TaskDetailState(errorMessage = "Task not found")
                    }
                }
        }
    }

    /** Marks the current task as complete by re-adding with updated flag. */
 
    fun markComplete() {
        viewModelScope.launch {
            uiState.value.task?.let { original ->
                repository.updateTask(original.copy(isComplete = true))
            }
        }
    }
}

