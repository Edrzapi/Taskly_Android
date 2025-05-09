package uk.co.devfoundry.taskly.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import uk.co.devfoundry.taskly.ui.screen.list.TaskListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen(
    viewModel: TaskListViewModel,
    onTaskClick: (String) -> Unit,
    onSettingsClick: () -> Unit,
) {
    val tasks by viewModel.tasks.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    var newTitle by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Taskly") },
                actions = {
                    IconButton(
                        onClick = onSettingsClick,
                        modifier = Modifier
                            .semantics(mergeDescendants = true) {
                                contentDescription = "settings_button"
                            }
                    ) {
                        Icon(Icons.Default.Settings, contentDescription = null)
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showDialog = true },
                modifier = Modifier
                    .semantics(mergeDescendants = true) {
                        contentDescription = "add_task_button"
                    }
            ) {
                Icon(Icons.Default.Add, contentDescription = null)
            }
        }
    ) { padding ->
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("New Task") },
                text = {
                    TextField(
                        value = newTitle,
                        onValueChange = { newTitle = it },
                        placeholder = { Text("Enter task title") },
                        modifier = Modifier
                            .clearAndSetSemantics {
                                contentDescription = "task_input"
                            }
                            .fillMaxWidth()
                    )
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            viewModel.addTask(newTitle.trim().ifEmpty { "New Task" })
                            newTitle = ""
                            showDialog = false
                        },
                        modifier = Modifier.clearAndSetSemantics {
                            contentDescription = "confirm_add_button"
                        }
                    ) {
                        Text("Add")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            newTitle = ""
                            showDialog = false
                        },
                        modifier = Modifier.clearAndSetSemantics {
                            contentDescription = "cancel_add_button"
                        }
                    ) {
                        Text("Cancel")
                    }
                }
            )
        }

        // Original list rendering logic
        if (tasks.isEmpty()) {
            Box(
                Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                Text("No tasks yet")
            }
        } else {
            LazyColumn(
                contentPadding = padding,
                modifier = Modifier.fillMaxSize()
            ) {
                items(tasks) { task ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .clickable { onTaskClick(task.id) }
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            task.title,
                            modifier = Modifier.weight(1f),
                            style = MaterialTheme.typography.bodyLarge.copy(
                                textDecoration = if (task.isComplete)
                                    TextDecoration.LineThrough
                                else
                                    TextDecoration.None
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
}
