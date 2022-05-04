package ch.proximeety.proximeety.presentation.views.stories

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
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
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.*
import javax.inject.Inject

@HiltAndroidTest
@UninstallModules(AppModule::class)
class StoriesViewTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Inject
    lateinit var navigationManager: NavigationManager

    @Inject
    lateinit var userInteractions: UserInteractions

    private lateinit var viewModel: StoriesViewModel

    @Before
    fun setup() {
        hiltRule.inject()

        runBlocking {
            userInteractions.authenticateWithGoogle()
        }

        val savedStateHandle = SavedStateHandle(
            mapOf(
                "userId" to "testUserId"
            )
        )
        viewModel = StoriesViewModel(navigationManager, userInteractions, savedStateHandle)

        composeTestRule.mainClock.autoAdvance = false

        composeTestRule.setContent {
            ProximeetyTheme {
                StoriesView(viewModel = viewModel)
            }
        }
    }

    @Test
    fun storiesAreDisplayed() {
        composeTestRule.waitUntil(1000) { viewModel.currentStory.value != null}
        composeTestRule.waitUntil(1000) { viewModel.currentStory.value?.storyURL != null}
        composeTestRule.mainClock.advanceTimeBy(1000)
        composeTestRule.onNodeWithContentDescription("Story of ${viewModel.currentStory.value?.userDisplayName}").assertExists()
    }

    @Test
    fun storiesAreChanging() {
        composeTestRule.waitUntil(1000) { viewModel.currentStory.value != null}
        composeTestRule.waitUntil(1000) { viewModel.currentStory.value?.storyURL != null}
        val first = viewModel.currentStory.value?.storyURL
        composeTestRule.mainClock.autoAdvance = true
        composeTestRule.waitUntil (100000) {  viewModel.currentStory.value?.storyURL != first}
        composeTestRule.mainClock.autoAdvance = false
        val after = viewModel.currentStory.value?.storyURL
        composeTestRule.onNodeWithContentDescription("Story of ${viewModel.currentStory.value?.userDisplayName}").assertExists()
        assertNotNull(first)
        assertNotNull(after)
        assertNotEquals(after!!, first!!)
    }

    @Test
    fun previousAndNextButtonWork() {
        composeTestRule.waitUntil(1000) { viewModel.currentStory.value != null}
        composeTestRule.waitUntil(1000) { viewModel.currentStory.value?.storyURL != null}
        val first = viewModel.currentStory.value?.storyURL
        composeTestRule.mainClock.advanceTimeBy(1000)
        composeTestRule.onRoot().performTouchInput {
            click(Offset(this.width - 10f, this.height - 10f))
        }
        composeTestRule.mainClock.advanceTimeBy(1000)
        val after = viewModel.currentStory.value?.storyURL
        assertNotNull(first)
        assertNotNull(after)
        assertNotEquals(after!!, first!!)
        composeTestRule.mainClock.advanceTimeBy(1000)
        composeTestRule.onRoot().performTouchInput {
            click(Offset(10f, this.height - 10f))
        }
        composeTestRule.mainClock.advanceTimeBy(1000)
        val firstAgain = viewModel.currentStory.value?.storyURL
        assertNotNull(firstAgain)
        assertNotEquals(firstAgain!!, after)
        assertEquals(firstAgain, first)
    }

    @Test
    fun extendedButtonAndAlertDialogForDeletingStories() {
        var storyId = viewModel.currentStory.value?.id

        composeTestRule.waitUntil(1000) { viewModel.currentStory.value != null}
        composeTestRule.waitUntil(1000) { viewModel.currentStory.value?.storyURL != null}
        composeTestRule.mainClock.advanceTimeBy(1000)
        composeTestRule.onNodeWithContentDescription("Story of ${viewModel.currentStory.value?.userDisplayName}").assertExists()
        // More button
        composeTestRule.onNodeWithContentDescription("More $storyId").performClick()
        composeTestRule.mainClock.advanceTimeBy(1000)
        // Extended button
        composeTestRule.onNodeWithText("More").assertExists()
        composeTestRule.onNodeWithText("Delete Story").performClick()
        composeTestRule.mainClock.advanceTimeBy(1000)
        // Alert dialog
        composeTestRule.onNodeWithText("Delete story").assertExists()
        composeTestRule.onNodeWithText("Confirm").assertHasClickAction()
        composeTestRule.onNodeWithText("Dismiss").assertHasClickAction()
    }

    @Test
    fun deleteStories() {
        var storyId = viewModel.currentStory.value?.id
        var userId = "testUserId"

        // The story exists
        runBlocking {
            val stories = userInteractions.getStoriesByUserId(userId)
            assertTrue(stories.filter { it.id == storyId }.size == 1)
        }
        // Delete story
        composeTestRule.waitUntil(1000) { viewModel.currentStory.value != null}
        composeTestRule.waitUntil(1000) { viewModel.currentStory.value?.storyURL != null}
        composeTestRule.mainClock.advanceTimeBy(1000)
        //composeTestRule.onNodeWithContentDescription("Story of ${viewModel.currentStory.value?.userDisplayName}").assertExists()
        composeTestRule.onNodeWithContentDescription("More $storyId").performClick()
        composeTestRule.mainClock.advanceTimeBy(1000)
        composeTestRule.onNodeWithText("Delete Story").performClick()
        composeTestRule.mainClock.advanceTimeBy(1000)
        composeTestRule.onNodeWithText("Confirm").performClick()

        // The post doesn't exist anymore
        runBlocking {
            val stories = userInteractions.getStoriesByUserId(userId)
            assertTrue(stories.none { it.id == storyId })
        }
    }
}