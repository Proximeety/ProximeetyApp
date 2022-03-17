package ch.proximeety.proximeety.presentation.views.nearbyUsers

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import ch.proximeety.proximeety.core.entities.User
import ch.proximeety.proximeety.core.interactions.UserInteractions
import ch.proximeety.proximeety.core.repositories.UserRepository
import ch.proximeety.proximeety.data.repositories.UserRepositoryMockImplementation
import ch.proximeety.proximeety.di.AppModule
import ch.proximeety.proximeety.di.TestAppModule
import ch.proximeety.proximeety.presentation.MainActivity
import ch.proximeety.proximeety.presentation.theme.ProximeetyTheme
import dagger.Module
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
@UninstallModules(AppModule::class)
class NearbyUsersViewTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var userInteractions : UserInteractions

    @Before
    fun setup() {
        hiltRule.inject()
        runBlocking {
            userInteractions.authenticateWithGoogle()
        }
        composeTestRule.setContent {
            ProximeetyTheme {
                NearbyUsersView()
            }
        }
    }

    @Test
    fun shouldDisplayNearbyUsers() {
        composeTestRule.onNodeWithText("User1").assertExists()
        composeTestRule.onNodeWithText("User2").assertExists()
        composeTestRule.onNodeWithText("User3").assertExists()
    }
}