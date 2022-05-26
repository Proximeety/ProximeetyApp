package ch.proximeety.proximeety.presentation.views.authenticationHome

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import ch.proximeety.proximeety.di.AppModule
import ch.proximeety.proximeety.presentation.MainActivity
import ch.proximeety.proximeety.presentation.theme.ProximeetyTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class)
class AuthenticationHomeViewTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() {
        composeTestRule.setContent {
            ProximeetyTheme {
                AuthenticationHomeView()
            }
        }
    }

    @Test
    fun sampleTest() {
        // Page 1
        composeTestRule.onNodeWithText("Welcome to Proximity!").assertExists()
        composeTestRule.onNodeWithText("Next").performClick()

        // Page 2
        composeTestRule.onNodeWithText("Back").assertExists()
        composeTestRule.onNodeWithText("Next").performClick()

        // Page 3
        composeTestRule.onNodeWithText("Back").assertExists()
        composeTestRule.onNodeWithText("Do you already have an account?").assertExists()
        composeTestRule.onAllNodesWithText("Login").onFirst().performClick()
    }

    @Test
    fun testBackButton() {
        // Page 1
        composeTestRule.onNodeWithText("Welcome to Proximity!").assertExists()
        composeTestRule.onNodeWithText("Next").performClick()

        // Page 2
        composeTestRule.onNodeWithText("Back").assertExists()
        composeTestRule.onNodeWithText("Next").assertExists()
        composeTestRule.onNodeWithText("Back").performClick()

        // Page 1
        composeTestRule.onNodeWithText("Welcome to Proximity!").assertExists()
    }
}
