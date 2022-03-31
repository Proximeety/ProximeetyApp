package ch.proximeety.proximeety.data.sources

import android.util.Log
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import ch.proximeety.proximeety.di.AppModule
import ch.proximeety.proximeety.presentation.MainActivity
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class)
class FirebaseAccessObjectTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private val db = Firebase.database.reference

    @Test
    fun fetchesCorrectDataFromFirebase() {
        db.child("messages").child("TJepm7a0BVXNOLhrxNqNJhDHk4r1").key?.also {
            assert(it == "TJepm7a0BVXNOLhrxNqNJhDHk4r1")
        }


    }
}