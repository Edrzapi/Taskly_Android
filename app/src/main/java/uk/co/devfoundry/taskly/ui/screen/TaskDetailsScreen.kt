package uk.co.devfoundry.taskly.ui.screen
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.unit.dp
import uk.co.devfoundry.taskly.ui.screen.detail.TaskDetailViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailScreen(
    viewModel: TaskDetailViewModel,
    onBack:    () -> Unit
) {

   val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Task Details") },
                navigationIcon = {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier.semantics { testTag = "detail_back_button" }
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        when {
            uiState.isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .semantics { testTag = "detail_loading" },
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            uiState.errorMessage != null -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .semantics { testTag = "detail_error" },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = uiState.errorMessage!!,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
            uiState.task != null -> {
                // Success: show task details
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(16.dp)
                        .semantics { testTag = "detail_content" },
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text("Task ID:", style = MaterialTheme.typography.labelLarge)
                    Text(uiState.task!!.id, style = MaterialTheme.typography.bodyLarge)
                    Spacer(Modifier.height(24.dp))
                    Button(
                        onClick = { viewModel.markComplete() },
                        modifier = Modifier.semantics { testTag = "mark_complete_button" }
                    ) {
                        Text("Mark Complete")
                    }
                }
            }
        }
    }
}
