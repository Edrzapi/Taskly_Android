package uk.co.devfoundry.taskly

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import uk.co.devfoundry.taskly.ui.theme.TasklyTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TasklyTheme {
                TasklyApp()
            }
        }
    }
}

