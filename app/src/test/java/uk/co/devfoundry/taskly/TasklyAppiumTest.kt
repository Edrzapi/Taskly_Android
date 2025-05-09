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
            .setFullReset(true)
            .setNoReset(false)
        
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
    fun completeTaskMarksTaskComplete() {

        val wait = WebDriverWait(driver, Duration.ofSeconds(10))
        val taskName = "New Task!"

        // Add a task
        val addButton = wait.until(
            ExpectedConditions.presenceOfElementLocated(
                AppiumBy.accessibilityId("add_task_button")
            )
        )
        assertTrue("FAB should be visible", addButton.isDisplayed)
        addButton.click()


        val inputField = wait.until(
            ExpectedConditions.presenceOfElementLocated(
                AppiumBy.accessibilityId("task_input")
            )
        )

        // Manual input
        inputField.click()
        driver.executeScript("mobile: type", mapOf("text" to taskName))

        // Click
        wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.accessibilityId("confirm_add_button")))
            .click()


        // Confirm task name is visible in the list
        val listed = wait.until(
            ExpectedConditions.presenceOfElementLocated(
                AppiumBy.androidUIAutomator("new UiSelector().text(\"$taskName\")")
            )
        )
        assertTrue("Task should appear in list after adding", listed.isDisplayed)

        // Enter the task details screen
        listed.click()

        // Mark the task as completed
        val completeBtn = wait.until(
            ExpectedConditions.presenceOfElementLocated(
                (AppiumBy.accessibilityId("mark_complete_button"))
            )
        )

        completeBtn.click()

        // Assert the task is marked completed in the list
        driver.navigate().back()

        val completedIcon = wait.until(
            ExpectedConditions.presenceOfElementLocated(
                AppiumBy.accessibilityId("task_completed_icon")
            )
        )
        assertTrue("Check-icon should appear next to the completed task", completedIcon.isDisplayed)
    }
}