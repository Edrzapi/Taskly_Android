package uk.co.devfoundry.taskly.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp

@Composable
fun TaskListScreen(onTaskClick: (String) -> Unit) {

    var showDialog by remember { mutableStateOf(false) }
    var taskInput by remember { mutableStateOf("") }
    val tasks = remember { mutableStateListOf<String>() }

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
                Text("No tasks yet")
            } else {
                LazyColumn {
                    items(tasks) { task ->
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onTaskClick(task) } // Trigger callback when clicked
                                .padding(vertical = 8.dp)
                        ) {
                            Text(text = task)
                            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                        }
                    }
                }
            }
        }

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
                                tasks.add(taskInput)
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
