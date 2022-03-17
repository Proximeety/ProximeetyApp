package ch.proximeety.proximeety.presentation.views.friends

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
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
    fun sampleTest() {
        composeTestRule.onNodeWithText("User 1").assertExists()
        composeTestRule.onNodeWithText("User 2").assertExists()
        composeTestRule.onNodeWithText("User 3").assertExists()
        composeTestRule.onNodeWithText("User 4").assertExists()
        composeTestRule.onNodeWithText("user1@gmail.com").assertExists()
        composeTestRule.onNodeWithText("user2@gmail.com").assertExists()
        composeTestRule.onNodeWithText("user3@gmail.com").assertExists()
        composeTestRule.onNodeWithText("user4@gmail.com").assertExists()
        composeTestRule.onAllNodesWithContentDescription("User profile picture")
    }
}

