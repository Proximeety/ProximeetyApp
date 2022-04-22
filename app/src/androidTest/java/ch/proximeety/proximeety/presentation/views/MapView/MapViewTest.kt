package ch.proximeety.proximeety.presentation.views.MapView

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import ch.proximeety.proximeety.core.interactions.UserInteractions
import ch.proximeety.proximeety.di.AppModule
import ch.proximeety.proximeety.presentation.MainActivity
import ch.proximeety.proximeety.presentation.theme.ProximeetyTheme
import ch.proximeety.proximeety.presentation.views.conversationList.ConversationListView
import ch.proximeety.proximeety.presentation.views.mapView.MapView
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
@UninstallModules(AppModule::class)
class MapViewTest {
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() {
        composeTestRule.setContent {
            ProximeetyTheme {
                MapView()
            }
        }
    }

    @Test
    fun shouldDisplayFriendsMarkers() {
        composeTestRule.onNodeWithText("2022-01-01").assertExists()
        composeTestRule.onNodeWithText("2022-01-01").performClick()
    }

    // should center user
    // should drag around? not sure how
}