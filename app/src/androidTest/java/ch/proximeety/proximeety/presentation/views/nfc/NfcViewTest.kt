package ch.proximeety.proximeety.presentation.views.nfc

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.lifecycle.SavedStateHandle
import ch.proximeety.proximeety.core.interactions.UserInteractions
import ch.proximeety.proximeety.core.repositories.UserRepository
import ch.proximeety.proximeety.data.repositories.UserRepositoryMockImplementation
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
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltAndroidTest
@UninstallModules(AppModule::class)
class NfcViewTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Inject
    lateinit var repository: UserRepository

    @Inject
    lateinit var navigationManager: NavigationManager

    @Inject
    lateinit var userInteractions: UserInteractions

    private lateinit var viewModel: NfcViewModel

    @Before
    fun setup() {
        hiltRule.inject()

        runBlocking {
            userInteractions.authenticateWithGoogle()
        }

        val savedStateHandle = SavedStateHandle(
            mapOf(
                "tagId" to "00:00:00:00:00:00"
            )
        )

        viewModel = NfcViewModel(navigationManager, userInteractions, savedStateHandle)

        composeTestRule.setContent {
            ProximeetyTheme {
                NfcView(viewModel)
            }
        }

        (repository as UserRepositoryMockImplementation).setTag()
    }

    @Test
    fun createNfcTag() {
        composeTestRule.waitUntil(1000) { viewModel.nfcTag.value != null}
        composeTestRule.onNodeWithText("Yes").performClick()
    }

    @Test
    fun nfcViewShouldDisplayTagName() {
        composeTestRule.onNodeWithText("testTag").assertExists()
    }

    @Test
    fun nfcViewShouldDisplayOwnerName() {
        composeTestRule.onNodeWithText("By testUser").assertExists()
    }

    @Test
    fun nfcViewShouldDisplayVisitorName() {
        composeTestRule.onNodeWithText("testUserVisitor").assertExists()
    }

    @Test
    fun nfcViewShouldDisplayVisitorDate() {
        composeTestRule.onNodeWithText(
            SimpleDateFormat("EEE, MMM d, yyyy").format(Date(1651653481L))
        ).assertExists()
    }


}