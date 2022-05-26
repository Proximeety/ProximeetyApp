package ch.proximeety.proximeety.presentation.views.post

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
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
@UninstallModules(AppModule::class)
class PostViewTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Inject
    lateinit var navigationManager: NavigationManager

    @Inject
    lateinit var userInteractions: UserInteractions

    private lateinit var viewModel: PostViewModel

    @Before
    fun setup() {
        hiltRule.inject()

        runBlocking {
            userInteractions.authenticateWithGoogle()
        }

        val savedStateHandle = SavedStateHandle(
            mapOf(
                "userId" to "friendWithStoryId",
                "postId" to "-MzLEU_55gpq5ZQgAOAp",
            )
        )

        viewModel = PostViewModel(navigationManager, userInteractions, savedStateHandle)

        composeTestRule.setContent {
            ProximeetyTheme {
                PostView(viewModel)
            }
        }
    }

    @Test
    fun commentsTest() {
        composeTestRule.waitUntil(10000) { viewModel.posts.value.isNotEmpty() }
        composeTestRule.onNodeWithTag("comment_button_-MzLEU_55gpq5ZQgAOAp")
            .assertExists()
            .performClick()
        composeTestRule.waitUntil(10000) { viewModel.comments.value.contains("-MzLEU_55gpq5ZQgAOAp") }
        composeTestRule.onNodeWithText("test1")
            .assertExists()

        composeTestRule.onAllNodesWithContentDescription("Comment Like")
            .onFirst()
            .assertExists()
            .performClick()

        composeTestRule.onNodeWithTag("comment_post_text_field")
            .assertExists()
            .performClick()
            .performTextInput("testNewComment")

        composeTestRule.onAllNodesWithText("testNewComment")
            .onFirst()
            .assertExists()

        composeTestRule.onAllNodesWithText("Show replies")
            .onFirst()
            .assertExists()
            .performClick()

        composeTestRule.onNodeWithTag("reply_text_field")
            .assertExists()
            .performTextInput("testNewReply")

        composeTestRule.onAllNodesWithText("testNewReply")
            .onFirst()
            .assertExists()

        composeTestRule.onAllNodesWithText("Hide replies")
            .onFirst()
            .assertExists()
            .performClick()

        composeTestRule.onNodeWithContentDescription("Close Comment Section")
            .assertExists()
            .performClick()
    }

    @Test
    fun postLike() {
        composeTestRule.waitUntil(10000) { viewModel.posts.value.isNotEmpty() }
        composeTestRule.onAllNodesWithContentDescription("Like Post")
            .onFirst()
            .assertExists()
            .performClick()
    }
}