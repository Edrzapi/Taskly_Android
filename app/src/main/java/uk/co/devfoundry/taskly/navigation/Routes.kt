package uk.co.devfoundry.taskly.navigation



object Routes {
    const val HOME        = "home"
    const val TASK_DETAIL = "detail/{taskId}"
    const val SETTINGS    = "settings"

    /** Helper to build a real route to a specific task */
    fun detailRoute(taskId: String) = "detail/$taskId"
}
