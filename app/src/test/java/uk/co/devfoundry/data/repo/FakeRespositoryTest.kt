package uk.co.devfoundry.data.repo

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import uk.co.devfoundry.taskly.data.model.Task
import uk.co.devfoundry.taskly.data.repository.FakeRepository

class FakeRepoTest {

    @Test
    fun addTaskEmitsNewTask() = runBlocking {
        // Arrange
        val repo = FakeRepository()
        val task = Task(title = "Pass unit test")

        // Act
        repo.addTask(task)
        val tasks = repo.getTasks().first()

        // Assert
        assertEquals(listOf(task), tasks)
    }

    @Test
    fun getTasksInitialIsEmpty() = runBlocking {
        // Arrange
        val repo = FakeRepository()

        // Act
        val tasks = repo.getTasks().first()

        // Assert
        assertEquals(emptyList<Task>(), tasks)
    }

    @Test
    fun addMultipleTasksEmitsAllTasks() = runBlocking {
        // Arrange
        val repo = FakeRepository()
        val t1 = Task(title = "First")
        val t2 = Task(title = "Second")

        // Act
        repo.addTask(t1)
        repo.addTask(t2)
        val tasks = repo.getTasks().first()

        // Assert
        assertEquals(listOf(t1, t2), tasks)
    }

    @Test
    fun updateTaskReplacesExistingEntry() = runBlocking {
        // Arrange
        val repo = FakeRepository()
        val original = Task(id = "1", title = "Original", isComplete = false)
        val updated  = original.copy(isComplete = true)

        repo.addTask(original)

        // Act
        repo.updateTask(updated)
        val tasks = repo.getTasks().first()

        // Assert
        assertEquals(listOf(updated), tasks)
    }



}
