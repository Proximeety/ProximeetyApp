package ch.proximeety.proximeety.presentation.views.userProfile

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import ch.proximeety.proximeety.di.TestAppModule
import ch.proximeety.proximeety.presentation.MainActivity
import ch.proximeety.proximeety.presentation.theme.ProximeetyTheme
import ch.proximeety.proximeety.presentation.views.settings.SettingsView
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(TestAppModule::class)
class UserProfileViewTest {
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() {
        composeTestRule.setContent {
            ProximeetyTheme {
                UserProfileView()
            }
        }
    }

    @Test
    fun textIsDisplayed() {
        composeTestRule.onNodeWithText(text = "Back").assertExists()
        composeTestRule.onNodeWithText(text = "Proximeety").assertExists()
        composeTestRule.onNodeWithText(text = "Settings").assertExists()
        composeTestRule.onNodeWithText(text = "FirstName LastName").assertExists()
        composeTestRule.onNodeWithText(text = "Example Bio For User").assertExists()
        composeTestRule.onNodeWithText(text = "Posts").assertExists()
        composeTestRule.onNodeWithText(text = "Friends").assertExists()
    }
}