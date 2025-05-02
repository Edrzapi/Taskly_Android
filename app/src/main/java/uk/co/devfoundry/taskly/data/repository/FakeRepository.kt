package uk.co.devfoundry.taskly.data.repository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import uk.co.devfoundry.taskly.data.model.Task

/**
 * An in-memory fake repository
 * This will start empty and simply appends new tasks.
 */
class FakeRepository : TaskRepository {
    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    override fun getTasks() = _tasks.asStateFlow()

    override suspend fun addTask(task: Task) {
        _tasks.value += task
    }

    override suspend fun updateTask(task: Task) {
        _tasks.value = _tasks.value.map {
            if (it.id == task.id) task else it
        }
    }
}
