package ch.proximeety.proximeety.presentation.views.settings

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import ch.proximeety.proximeety.di.TestAppModule
import ch.proximeety.proximeety.presentation.MainActivity
import ch.proximeety.proximeety.presentation.theme.ProximeetyTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(TestAppModule::class)
class SettingsViewTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() {
        composeTestRule.setContent {
            ProximeetyTheme {
                SettingsView()
            }
        }
    }

    @Test
    fun textIsDisplayed() {
        composeTestRule.onNodeWithText(text = "Back").assertExists()
        composeTestRule.onNodeWithText(text = "Settings").assertExists()
        composeTestRule.onNodeWithText(text = "Test with switch").assertExists()
        composeTestRule.onNodeWithText(text = "Test with slider").assertExists()
        composeTestRule.onNodeWithText(text = "Test with Dropdown Menu").assertExists()
    }
}