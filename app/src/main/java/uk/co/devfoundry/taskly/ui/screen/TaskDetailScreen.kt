package uk.co.devfoundry.taskly.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailScreen(
    taskId: String,
    onMarkComplete: () -> Unit,
    onBack:        () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Task Details") },
                navigationIcon = {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier.clearAndSetSemantics { contentDescription = "detail_back_button" }
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .clearAndSetSemantics { contentDescription = "detail_content" },
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Task ID:",
                style = MaterialTheme.typography.labelLarge
            )
            Text(
                text = taskId,
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = onMarkComplete,
                modifier = Modifier.clearAndSetSemantics { contentDescription = "mark_complete_button" }
            ) {
                Text("Mark Complete")
            }
        }
    }
}
