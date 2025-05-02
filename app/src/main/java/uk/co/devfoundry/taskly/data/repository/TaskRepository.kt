package uk.co.devfoundry.taskly.data.repository

import kotlinx.coroutines.flow.Flow
import uk.co.devfoundry.taskly.data.model.Task

/**
 * Abstraction of Tasks.
 */
interface TaskRepository {
    fun getTasks(): Flow<List<Task>>
    suspend fun addTask(task: Task)
    suspend fun updateTask(task: Task)
}
