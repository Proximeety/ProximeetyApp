package ch.proximeety.proximeety

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onParent
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import ch.proximeety.proximeety.ui.theme.ProximeetyTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule()
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun sampleTest() {

        composeTestRule.setContent {
            ProximeetyTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting()
                }
            }
        }
        composeTestRule.onNodeWithText("Hello").assertExists()

        composeTestRule.onNodeWithText("Hello").onParent().performClick()
    }
}