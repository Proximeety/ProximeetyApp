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
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject


@HiltAndroidTest
@UninstallModules(AppModule::class)
class UploadViewTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private lateinit var context: Context

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
        val randomUri = "random_uri_1234"
        viewModel.onEvent(UploadEvent.SetPostURI(Uri.parse(randomUri)))
        composeTestRule.onNodeWithContentDescription("Upload post")
            .assertExists()
            .performClick()
        composeTestRule.waitForIdle()
        runBlocking {
            val feed = userInteractions.getFeed()
            assertTrue(feed.filter { it.id == randomUri }.size == 1)
        }
    }

    @Test
    fun cancelButtonShouldNotPost() {
        val randomUri = "random_uri_5678"
        viewModel.onEvent(UploadEvent.SetPostURI(Uri.parse(randomUri)))
        composeTestRule.onNodeWithContentDescription("Cancel post")
            .assertExists()
            .performClick()
        composeTestRule.waitForIdle()
        runBlocking {
            val feed = userInteractions.getFeed()
            assertTrue(feed.none { it.id == randomUri })
        }
    }

    @Test
    fun postStoryButtonShouldPost() {
        val randomUri = "random_uri_1234"
        viewModel.onEvent(UploadEvent.SetPostURI(Uri.parse(randomUri)))
        composeTestRule.onRoot().performTouchInput { swipeLeft() }
        composeTestRule.onNodeWithContentDescription("Upload story")
            .assertExists()
            .performClick()
        composeTestRule.waitForIdle()
        runBlocking {
            val stories = userInteractions.getStoriesByUserId("testUserId")
            assertTrue(stories.filter { it.id == randomUri }.size == 1)
        }
    }

    @Test
    fun cancelButtonShouldNotPostStory() {
        val randomUri = "random_uri_5678"
        viewModel.onEvent(UploadEvent.SetPostURI(Uri.parse(randomUri)))
        composeTestRule.onRoot().performTouchInput { swipeLeft() }
        composeTestRule.onNodeWithContentDescription("Cancel story")
            .assertExists()
            .performClick()
        composeTestRule.waitForIdle()
        runBlocking {
            val stories = userInteractions.getStoriesByUserId("testUserId")
            assertTrue(stories.none { it.id == randomUri })
        }
    }

// NOT WORKING IN EMULATOR : see https://github.com/android/camera-samples/issues/428
//    @Test
//    fun takePictureShouldWork() {
//        if (composeTestRule.activity.checkSelfPermission(android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//            composeTestRule.onNodeWithText("Enable permissions")
//                .assertExists()
//                .performClick()
//            composeTestRule.waitForIdle()
//            PermissionsTestsUtils.assertPermissionsAreDisplayAndAllow()
//        }
//        composeTestRule.waitForIdle()
//        composeTestRule.onNodeWithTag(context.getString(R.string.TT_UV_take_picture_button))
//            .assertExists()
//            .performClick()
//        composeTestRule.waitUntil(10000) {
//            viewModel.imageUri.value != EMPTY_IMAGE_URI
//        }
//        composeTestRule.onNodeWithContentDescription("Upload post")
//            .assertExists()
//    }
}