package ch.proximeety.proximeety.presentation.views.profile

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.lifecycle.SavedStateHandle
import ch.proximeety.proximeety.core.interactions.UserInteractions
import ch.proximeety.proximeety.di.AppModule
import ch.proximeety.proximeety.presentation.MainActivity
import ch.proximeety.proximeety.presentation.navigation.NavigationManager
import ch.proximeety.proximeety.presentation.theme.ProximeetyTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
@UninstallModules(AppModule::class)
class ProfileViewTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Inject
    lateinit var navigationManager: NavigationManager

    @Inject
    lateinit var userInteractions: UserInteractions

    private lateinit var viewModel: ProfileViewModel

    private fun setup(id: String) {
        hiltRule.inject()

        runBlocking {
            userInteractions.authenticateWithGoogle()
        }

        val savedStateHandle = SavedStateHandle(
            mapOf(
                "userId" to id
            )
        )
        viewModel = ProfileViewModel(navigationManager, userInteractions, savedStateHandle)

        composeTestRule.setContent {
            ProximeetyTheme {
                ProfileView(viewModel)
            }
        }

        composeTestRule.waitUntil(100) { viewModel.user.value.value != null }
    }

    @Test
    fun nameIsDisplayed() {
        setup("testUserId1")
        composeTestRule.onNodeWithText("User1").assertExists()
    }

    @Test
    fun addAsFriendShouldBeDisplayed() {
        setup("testUserId1")
        composeTestRule.onNodeWithText("Add").assertExists()
    }

    @Test
    fun addAsFriendShouldAddAFriend() {
        setup("testUserId1")
        composeTestRule.onNodeWithText("Add").performClick()
        runBlocking {
            val friends = userInteractions.getFriends()
            assertTrue(friends.filter { it.id == "testUserId1" }.size == 1)
        }
    }

    @Test
    fun removeFriendShouldBeDisplayed() {
        setup("friendWithStoryId")
        composeTestRule.onNodeWithText("Remove").assertExists()
    }

    @Test
    fun removeFriendShouldRemoveAFriend() {
        setup("friendWithStoryId")
        composeTestRule.onNodeWithText("Remove").performClick()
        runBlocking {
            val friends = userInteractions.getFriends()
            assertTrue(friends.none { it.id == "friendWithStoryId" })
        }
    }


    @Test
    fun addAsFriendShouldNotBeDisplayedIfAuthenticatedUserProfile() {
        setup("testUserId")
        composeTestRule.onNodeWithText("Add").assertDoesNotExist()
    }

    @Test
    fun signOutDisplayedIfAuthenticatedUserProfile() {
        setup("testUserId")
        composeTestRule.onNodeWithText("Add").assertDoesNotExist()
    }

}