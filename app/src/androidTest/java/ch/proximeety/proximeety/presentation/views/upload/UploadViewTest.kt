package ch.proximeety.proximeety.presentation.views.upload

import android.content.Context
import android.net.Uri
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.platform.app.InstrumentationRegistry
import ch.proximeety.proximeety.R
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
import org.junit.Assert.assertTrue
import javax.inject.Inject

@HiltAndroidTest
@UninstallModules(AppModule::class)
class UploadViewTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    lateinit var context: Context

    @Inject
    lateinit var navigationManager: NavigationManager

    @Inject
    lateinit var userInteractions: UserInteractions

    private lateinit var viewModel: UploadViewModel

    @Before
    fun setup() {
        hiltRule.inject()

        runBlocking {
            userInteractions.authenticateWithGoogle()
        }

        viewModel = UploadViewModel(navigationManager, userInteractions)

        composeTestRule.setContent {
            ProximeetyTheme {
                UploadView(viewModel)
            }
        }

        context = InstrumentationRegistry.getInstrumentation().targetContext

    }

    @Test
    fun takePictureButtonShouldBeDisplayedIfURIIsNull() {
        composeTestRule.onNodeWithTag(context.getString(R.string.TT_UV_take_picture_button))
            .assertExists()
    }

    @Test
    fun takePictureButtonShouldNotBeDisplayedIfURIIsNull() {
        viewModel.onEvent(UploadEvent.SetPostURI(Uri.parse("test")))
        composeTestRule.onNodeWithTag(context.getString(R.string.TT_UV_take_picture_button))
            .assertDoesNotExist()
    }

    @Test
    fun postButtonShouldPost() {
        val randomUri ="random_uri_1234"
        viewModel.onEvent(UploadEvent.SetPostURI(Uri.parse(randomUri)))
        composeTestRule.onNodeWithTag(context.getString(R.string.TT_UV_post_button))
            .assertExists()
            .performClick()
        runBlocking {
            val feed = userInteractions.getFeed()
            assertTrue(feed.filter { it.id == randomUri }.size == 1)
        }
    }

    @Test
    fun cancelButtonShouldNotPost() {
        val randomUri ="random_uri_5678"
        viewModel.onEvent(UploadEvent.SetPostURI(Uri.parse(randomUri)))
        composeTestRule.onNodeWithTag(context.getString(R.string.TT_UV_cancel_post_button))
            .assertExists()
            .performClick()
        runBlocking {
            val feed = userInteractions.getFeed()
            assertTrue(feed.filter { it.id == randomUri }.isEmpty())
        }
    }
}