package ch.proximeety.proximeety.presentation

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import ch.proximeety.proximeety.di.AppModule
import ch.proximeety.proximeety.presentation.theme.ProximeetyTheme
import ch.proximeety.proximeety.presentation.views.authenticationHome.AuthenticationHomeView
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class)
class MainActivityTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

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
    fun sampleTestDarkTheme() {
        composeTestRule.setContent {
            ProximeetyTheme(darkTheme = true) {
                ProximeetyTheme {
                    Surface(
                        color = MaterialTheme.colors.background
                    ) {
                        AuthenticationHomeView()
                    }
                }
            }
        }
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
    fun sampleTestLightTheme() {
        composeTestRule.setContent {
            ProximeetyTheme(darkTheme = false) {
                ProximeetyTheme {
                    Surface(
                        color = MaterialTheme.colors.background
                    ) {
                        AuthenticationHomeView()
                    }
                }
            }
        }
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

}