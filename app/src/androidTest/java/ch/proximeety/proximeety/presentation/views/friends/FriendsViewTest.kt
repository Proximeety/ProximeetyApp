package ch.proximeety.proximeety.presentation.views.friends

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
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
class FriendsViewTest {
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() {
        composeTestRule.setContent {
            ProximeetyTheme {
                FriendsView()
            }
        }
    }

    @Test
    fun friendListTest() {
        composeTestRule.onNodeWithText("Tom").assertExists()
        composeTestRule.onNodeWithText("Tomas").assertExists()
        composeTestRule.onNodeWithText("Mery").assertExists()
        composeTestRule.onNodeWithText("Mark").assertExists()
        composeTestRule.onNodeWithText("user1@gmail.com").assertExists()
        composeTestRule.onNodeWithText("user2@gmail.com").assertExists()
        composeTestRule.onNodeWithText("user3@gmail.com").assertExists()
        composeTestRule.onNodeWithText("user4@gmail.com").assertExists()
        composeTestRule.onAllNodesWithContentDescription("User profile picture")
    }

    @Test
    fun searchBarTest() {
        composeTestRule.onNodeWithTag("textField").performClick().performTextInput("tom")
        composeTestRule.onNodeWithTag("button").performClick()
        composeTestRule.onNodeWithText("Tom").assertExists()
        composeTestRule.onNodeWithText("Tomas").assertExists()

        composeTestRule.onNodeWithTag("textField").performClick().performTextClearance()
        composeTestRule.onNodeWithTag("button").performClick()
        composeTestRule.onNodeWithText("Tom").assertExists()
        composeTestRule.onNodeWithText("Tomas").assertExists()
        composeTestRule.onNodeWithText("Mery").assertExists()
        composeTestRule.onNodeWithText("Mark").assertExists()
    }
}
