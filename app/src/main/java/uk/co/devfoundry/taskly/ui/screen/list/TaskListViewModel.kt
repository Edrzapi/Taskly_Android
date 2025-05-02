package uk.co.devfoundry.taskly.ui.screen.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import uk.co.devfoundry.taskly.data.model.Task
import uk.co.devfoundry.taskly.data.repository.TaskRepository

class TaskListViewModel(
    private val repository: TaskRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(TaskListState(isLoading = true))
    val uiState: StateFlow<TaskListState> = _uiState

    /** Expose tasks separately for convenience */
    val tasks: StateFlow<List<Task>> =
        _uiState
            .map { it.tasks }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    init {
        viewModelScope.launch {
            repository.getTasks().collect { list ->
                _uiState.value = TaskListState(tasks = list)
            }
        }
    }


    fun addTask(title: String) {
        viewModelScope.launch {
            repository.addTask(Task(title = title))
        }
    }
}
