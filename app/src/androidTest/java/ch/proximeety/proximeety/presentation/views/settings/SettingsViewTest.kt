package ch.proximeety.proximeety.presentation.views.settings

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import ch.proximeety.proximeety.di.TestAppModule
import ch.proximeety.proximeety.presentation.MainActivity
import ch.proximeety.proximeety.presentation.navigation.NavigationManager
import ch.proximeety.proximeety.presentation.theme.ProximeetyTheme
import ch.proximeety.proximeety.presentation.views.home.HomeView
import ch.proximeety.proximeety.presentation.views.home.HomeViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
@UninstallModules(TestAppModule::class)
class SettingsViewTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private lateinit var viewModel: SettingsViewModel

    @Inject
    lateinit var navigationManager: NavigationManager


    @Before
    fun setup() {
        hiltRule.inject()

        viewModel = SettingsViewModel(navigationManager)

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