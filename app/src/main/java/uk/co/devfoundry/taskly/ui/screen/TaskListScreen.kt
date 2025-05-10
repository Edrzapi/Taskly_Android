package uk.co.devfoundry.taskly.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import uk.co.devfoundry.taskly.data.model.Task

@Composable
fun TaskListScreen(
    tasks: List<Task>,
    onTaskClick: (String) -> Unit,
    onAddTask: (String) -> Unit,
) {
    var showDialog by remember { mutableStateOf(false) }
    var taskInput by remember { mutableStateOf("") }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showDialog = true },
                modifier = Modifier
                    .testTag("add_task_button")
                    .semantics { contentDescription = "add_task_button" }
            ) {
                Icon(Icons.Default.Add, contentDescription = null)
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            if (tasks.isEmpty()) {
                Box(
                    Modifier
                        .fillMaxSize()
                        .padding(padding)
                ) {
                    Text("No tasks yet")
                }
            } else {
                LazyColumn {
                    items(tasks) { task ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onTaskClick(task.id) }
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                task.title,
                                modifier = Modifier.weight(1f),
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    textDecoration = if (task.isComplete) TextDecoration.LineThrough else TextDecoration.None
                                )
                            )
                            if (task.isComplete) {
                                Icon(
                                    Icons.Default.Check,
                                    contentDescription = "task_completed_icon",
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                        HorizontalDivider()
                    }
                }
            }
        }

        // 🔥 Moved out of the if/else block
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Add Task") },
                text = {
                    TextField(
                        value = taskInput,
                        onValueChange = { taskInput = it },
                        placeholder = { Text("Enter task") },
                        modifier = Modifier
                            .testTag("task_input")
                            .semantics { contentDescription = "task_input" }
                    )
                },
                confirmButton = {
                    Button(
                        onClick = {
                            if (taskInput.isNotBlank()) {
                                onAddTask(taskInput)
                                taskInput = ""
                                showDialog = false
                            }
                        },
                        modifier = Modifier
                            .testTag("confirm_add_button")
                            .semantics { contentDescription = "confirm_add_button" }
                    ) {
                        Text("Add")
                    }
                },
                dismissButton = {
                    Button(onClick = { showDialog = false }) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}
