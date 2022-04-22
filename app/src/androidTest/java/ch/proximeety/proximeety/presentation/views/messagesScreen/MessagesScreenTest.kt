package ch.proximeety.proximeety.presentation.views.messagesScreen

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import ch.proximeety.proximeety.di.AppModule
import ch.proximeety.proximeety.presentation.MainActivity
import ch.proximeety.proximeety.presentation.theme.ProximeetyTheme
import ch.proximeety.proximeety.presentation.views.conversationList.ConversationListView
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class)
class MessagesScreenTest {
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() {
        composeTestRule.setContent {
            ProximeetyTheme {
                MessagesView()
            }
        }
    }

    @Test
    fun shouldDisplayMessages() {
        composeTestRule.onNodeWithText("Test User").assertExists()
        composeTestRule.onNodeWithText("Hello my friend!").assertExists()
        composeTestRule.onNodeWithText("Hello back!").assertExists()
        composeTestRule.onNodeWithText("Send").assertExists()
        composeTestRule.onNodeWithText("Type your Message").assertExists()
    }

    @Test
    fun shouldDisplayTopBar() {
        composeTestRule.onNodeWithText("Test User").assertExists()
    }

    @Test
    fun shouldDisplayBottomBar() {
        composeTestRule.onNodeWithText("Send").assertExists()
        composeTestRule.onNodeWithText("Type your Message").assertExists()
    }
}