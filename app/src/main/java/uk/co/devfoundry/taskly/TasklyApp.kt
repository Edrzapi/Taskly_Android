package uk.co.devfoundry.taskly

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.compose.rememberNavController
import uk.co.devfoundry.taskly.data.repository.FakeRepository
import uk.co.devfoundry.taskly.navigation.TasklyNav
import uk.co.devfoundry.taskly.ui.screen.list.TaskListViewModel
import uk.co.devfoundry.taskly.ui.theme.TasklyTheme


@Composable
fun TasklyApp() {
    var darkMode by rememberSaveable { mutableStateOf(false) }
    val repo = remember { FakeRepository() }
    val vm = remember { TaskListViewModel(repo) }
    val nav = rememberNavController()

    TasklyTheme(darkTheme = darkMode) {
        TasklyNav(
            navController = nav,
            repository = repo,
            listViewModel = vm,
            darkModeEnabled = darkMode,
            onDarkModeToggle = { darkMode = !darkMode }
        )
    }
}
