package ch.proximeety.proximeety.presentation.views.home

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
class HomeUITest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() {
        composeTestRule.setContent {
            ProximeetyTheme {
                HomeView()
            }
        }
    }

    @Test
    fun textIsDisplayed() {
        composeTestRule.onNodeWithText(text = "Proximeety").assertExists()
        composeTestRule.onNodeWithText(text = "map").assertExists()
        composeTestRule.onNodeWithText(text = "msg").assertExists()
        composeTestRule.onNodeWithText(text = "Search").assertExists()
        composeTestRule.onNodeWithText(text = "Camera").assertExists()
        composeTestRule.onNodeWithText(text = "Profile").assertExists()
        composeTestRule.onNodeWithText(text = "Displaying", substring = true).assertExists()
    }
}