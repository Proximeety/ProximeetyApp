package ch.proximeety.proximeety.presentation.views.friends

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import ch.proximeety.proximeety.core.interactions.UserInteractions
import ch.proximeety.proximeety.di.AppModule
import ch.proximeety.proximeety.presentation.MainActivity
import ch.proximeety.proximeety.presentation.navigation.NavigationManager
import ch.proximeety.proximeety.presentation.theme.ProximeetyTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
@UninstallModules(AppModule::class)
class FriendsViewTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Inject
    lateinit var navigationManager: NavigationManager

    @Inject
    lateinit var userInteractions: UserInteractions

    @Before
    fun setup() {
        hiltRule.inject()

        runBlocking {
            userInteractions.authenticateWithGoogle()
        }

        composeTestRule.setContent {
            ProximeetyTheme {
                FriendsView()
            }
        }
    }

    @Test
    fun friendListTest() {
        composeTestRule.onNodeWithText("Test User").assertExists()
    }

    @Test
    fun searchBarTest() {
        composeTestRule.onNodeWithTag("textField").performClick().performTextInput("test")
        composeTestRule.onNodeWithTag("button").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Test User").assertExists()
    }

    @Test
    fun searchBarTestEmpty() {
        composeTestRule.onNodeWithTag("textField").performClick().performTextInput("")
        composeTestRule.onNodeWithTag("button").performClick()
        composeTestRule.onNodeWithText("Test User").assertExists()
    }

    @Test
    fun searchBarTestEmptyIME() {
        composeTestRule.onNodeWithTag("textField").performImeAction()
        composeTestRule.onNodeWithText("Test User").assertExists()
    }

    @Test
    fun linkToProfile() {
        composeTestRule.onNodeWithText("Test User").performClick()
    }
}
