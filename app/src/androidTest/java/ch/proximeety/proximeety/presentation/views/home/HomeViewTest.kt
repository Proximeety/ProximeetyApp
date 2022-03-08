package ch.proximeety.proximeety.presentation.views.home

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ch.proximeety.proximeety.di.AppModule
import ch.proximeety.proximeety.di.TestAppModule
import ch.proximeety.proximeety.presentation.MainActivity
import ch.proximeety.proximeety.presentation.navigation.Graph
import ch.proximeety.proximeety.presentation.navigation.graphs.authenticationGraph
import ch.proximeety.proximeety.presentation.navigation.graphs.mainGraph
import ch.proximeety.proximeety.presentation.theme.ProximeetyTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(TestAppModule::class)
class HomeViewTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() {
        composeTestRule.setContent { 
            ProximeetyTheme() {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "default"
                ) {
                    composable("default") {
                        HomeView(navController = navController)
                    }
                }
            }
        }     
    }
    
    @Test
    fun sampleTest() {
        composeTestRule.onNodeWithText("Welcome", substring = true).assertExists()
    }
}