package ch.proximeety.proximeety.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import ch.proximeety.proximeety.presentation.navigation.RootNavigation
import ch.proximeety.proximeety.presentation.theme.ProximeetyTheme
import ch.proximeety.proximeety.util.SyncActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : SyncActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProximeetyTheme {
                Surface(
                    color = MaterialTheme.colors.background
                ) {
                    RootNavigation()
                }
            }
        }
    }
}