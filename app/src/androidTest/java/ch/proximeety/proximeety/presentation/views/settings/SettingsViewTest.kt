package ch.proximeety.proximeety.presentation.views.settings

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import ch.proximeety.proximeety.core.interactions.UserInteractions
import ch.proximeety.proximeety.di.AppModule
import ch.proximeety.proximeety.di.TestAppModule
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
class SettingsViewTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private lateinit var viewModel: SettingsViewModel

    @Inject
    lateinit var userInteractions: UserInteractions

    @Inject
    lateinit var navigationManager: NavigationManager

    @Before
    fun setup() {
        hiltRule.inject()

        runBlocking {
            userInteractions.authenticateWithGoogle()
        }

        viewModel = SettingsViewModel(navigationManager, userInteractions)

        composeTestRule.setContent {
            ProximeetyTheme {
                SettingsView(viewModel)
            }
        }
    }

    @Test
    fun textIsDisplayed() {
        composeTestRule.onNodeWithText(text = "Test with switch").assertExists()
        composeTestRule.onNodeWithText(text = "Test with slider").assertExists()
        composeTestRule.onNodeWithText(text = "Test with Dropdown Menu").assertExists()
    }
}