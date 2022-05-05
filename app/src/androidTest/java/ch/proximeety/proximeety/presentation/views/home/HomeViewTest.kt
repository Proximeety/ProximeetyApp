package ch.proximeety.proximeety.presentation.views.home

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import ch.proximeety.proximeety.core.entities.User
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
class HomeViewTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Inject
    lateinit var navigationManager: NavigationManager

    @Inject
    lateinit var userInteractions: UserInteractions

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setup() {
        hiltRule.inject()

        runBlocking {
            userInteractions.authenticateWithGoogle()
        }

        viewModel = HomeViewModel(navigationManager, userInteractions)

        composeTestRule.setContent {
            ProximeetyTheme {
                HomeView(viewModel)
            }
        }
    }

    @Test
    fun postsAreDisplayed() {
        composeTestRule.waitUntil(10000) { viewModel.posts.value.isNotEmpty() }
        composeTestRule.onAllNodesWithText("Yanis De Busschere").onFirst().assertExists()
    }

    @Test
    fun commentsSectionDrawerWorksProperly() {
        composeTestRule.waitUntil(10000) { viewModel.posts.value.isNotEmpty() }
        composeTestRule
            .onAllNodesWithContentDescription("Comments")
            .onFirst()
            .assertHasClickAction()
            .performClick()
        composeTestRule
            .onNodeWithText("Comments", substring = true)
            .assertExists()
        composeTestRule
            .onNodeWithContentDescription("Close Comment Section")
            .assertHasClickAction()
            .performClick()
    }
}