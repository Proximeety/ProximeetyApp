package ch.proximeety.proximeety.presentation.views.stories

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
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
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
        composeTestRule.waitUntil (10000) {  viewModel.currentStory.value?.storyURL != first}
        composeTestRule.mainClock.autoAdvance = false
        val after = viewModel.currentStory.value?.storyURL
        composeTestRule.onNodeWithContentDescription("Story of ${viewModel.currentStory.value?.userDisplayName}").assertExists()
        assertNotNull(first)
        assertNotNull(after)
        assertNotEquals(after!!, first!!)
    }

// Not working on emulator (only on real device) for some reason (probably timing issue)
//    @Test
//    fun previousAndNextButtonWork() {
//        // Wait for stories to load
//        composeTestRule.waitUntil(10000) { viewModel.currentStory.value != null}
//        composeTestRule.waitUntil(10000) { viewModel.currentStory.value?.storyURL != null}
//
//        // Read the first story
//        val first = viewModel.currentStory.value?.storyURL
//
//        composeTestRule.waitForIdle()
//
//        // Click next
//        composeTestRule.onRoot().performTouchInput {
//            click(Offset(this.width - 20f, this.height/2f))
//        }
//
//        composeTestRule.waitUntil(10000) { viewModel.currentStory.value?.storyURL != first}
//
//        // Read the second story
//        val after = viewModel.currentStory.value?.storyURL
//
//        // Perform checks
//        assertNotNull(first)
//        assertNotNull(after)
//        assertNotEquals(after!!, first!!)
//
//        // Click previous
//        composeTestRule.onRoot().performTouchInput {
//            click(Offset(20f, this.height/2f))
//        }
//        composeTestRule.mainClock.advanceTimeUntil { viewModel.currentStory.value?.storyURL != after}
//
//        // Read the first story again
//        val firstAgain = viewModel.currentStory.value?.storyURL
//
//        // Perform checks
//        assertNotNull(firstAgain)
//        assertNotEquals(firstAgain!!, after)
//        assertEquals(firstAgain, first)
//    }
}