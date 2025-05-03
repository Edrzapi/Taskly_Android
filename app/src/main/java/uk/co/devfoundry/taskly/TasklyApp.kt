package uk.co.devfoundry.taskly

import androidx.compose.runtime.Composable
import uk.co.devfoundry.taskly.ui.screen.TaskListScreen
import uk.co.devfoundry.taskly.ui.theme.TasklyTheme

@Composable
fun TasklyApp() {
    TasklyTheme() {
        TaskListScreen()
    }
}
