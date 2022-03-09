package ch.proximeety.proximeety.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.collectAsState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import ch.proximeety.proximeety.presentation.navigation.NavigationManager
import ch.proximeety.proximeety.presentation.navigation.graphs.AuthenticationNavigationCommands
import ch.proximeety.proximeety.presentation.navigation.graphs.authenticationNavigationGraph
import ch.proximeety.proximeety.presentation.navigation.graphs.mainNavigationGraph
import ch.proximeety.proximeety.presentation.theme.ProximeetyTheme
import ch.proximeety.proximeety.util.SyncActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : SyncActivity() {

    @Inject
    lateinit var navigationManager: NavigationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            navigationManager.command.collectAsState().value?.also { command ->
                if (command.route.isNotEmpty()) {
                    navController.navigate(command.route)
                }
            }

            ProximeetyTheme {
                Surface(
                    color = MaterialTheme.colors.background
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = AuthenticationNavigationCommands.default.route,
                    ) {
                        authenticationNavigationGraph()
                        mainNavigationGraph()
                    }
                }
            }
        }
    }
}