package uk.co.devfoundry.taskly

import io.appium.java_client.android.AndroidDriver
import io.appium.java_client.AppiumBy
import io.appium.java_client.android.options.UiAutomator2Options
import io.appium.java_client.remote.AutomationName
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertTrue
import org.junit.Ignore
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
    fun sanityCheckShowsNoTasksYet() {

        val elements = driver.findElements(AppiumBy.xpath("//*"))
        var foundNoTasks = false

        elements.forEachIndexed { index, element ->
            val text = element.text.trim()
            println("${index + 1}. text: \"$text\"")

            if (text == "No tasks yet") {
                foundNoTasks = true
            }
        }

        assertTrue("Expected to find 'No tasks yet' on screen", foundNoTasks)

        println("Confirmed: App shows 'No tasks yet' on startup. Appium is working as expected")
    }

}