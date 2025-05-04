package uk.co.devfoundry.taskly

import io.appium.java_client.android.AndroidDriver
import io.appium.java_client.AppiumBy
import io.appium.java_client.android.options.UiAutomator2Options
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertTrue
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.net.URL
import java.time.Duration

class TasklyAppiumTest {
    private lateinit var driver: AndroidDriver

    @Before
    fun setUp() {
        val apkFile = java.io.File(
            System.getProperty("user.dir"),
            "build/outputs/apk/debug/app-debug.apk"
        )

        val opts = UiAutomator2Options()
            .setDeviceName("Android Emulator")
            .setApp(apkFile.absolutePath)
            .autoGrantPermissions()

        driver = AndroidDriver(URL("http://127.0.0.1:4723/"), opts)
    }

    @After
    fun tearDown() {
        driver.quit()
    }

    @Test
    fun addTaskUpdatesTheTaskList() {
        val wait = WebDriverWait(driver, Duration.ofSeconds(10))

        // Click the FAB
        val addButton =
            wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.accessibilityId("add_task_button")))
        addButton.click()

        // Input new task
        val inputField = driver.findElement(
            AppiumBy.androidUIAutomator(
                "new UiSelector().className(\"android.view.View\").descriptionContains(\"task_input\")"
            )
        )

        // Send keys via script
        inputField.click()
        driver.executeScript("mobile: type", mapOf("text" to "Learn Appium"))

        // Confirm the add
        val confirmButton =
            wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.accessibilityId("confirm_add_button")))
        confirmButton.click()

        // Check if task appears in list
        val taskItem = wait.until(
            ExpectedConditions.presenceOfElementLocated(
                AppiumBy.androidUIAutomator(
                    "new UiSelector().text(\"Learn Appium\")"
                )
            )
        )

        assertTrue("New task should be visible in the list", taskItem.isDisplayed)
    }

    @Test
    fun CompleteTaskMarksTaskComplete() {

        // Add a task


        // Confirm task name is visible in the list


        // Enter the task details screen


        // Mark the task as completed


        // Assert the task is marked completed in the list

    }
}