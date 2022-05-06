package ch.proximeety.proximeety.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.collectAsState
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import ch.proximeety.proximeety.core.interactions.UserInteractions
import ch.proximeety.proximeety.presentation.navigation.NavigationCommand
import ch.proximeety.proximeety.presentation.navigation.NavigationManager
import ch.proximeety.proximeety.presentation.navigation.graphs.AuthenticationNavigationCommands
import ch.proximeety.proximeety.presentation.navigation.graphs.MainNavigationCommands
import ch.proximeety.proximeety.presentation.navigation.graphs.authenticationNavigationGraph
import ch.proximeety.proximeety.presentation.navigation.graphs.mainNavigationGraph
import ch.proximeety.proximeety.presentation.theme.ProximeetyTheme
import ch.proximeety.proximeety.util.SyncActivity
import com.google.accompanist.pager.ExperimentalPagerApi
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@ExperimentalPagerApi
@AndroidEntryPoint
class MainActivity : SyncActivity() {

    @Inject
    lateinit var navigationManager: NavigationManager

    @Inject
    lateinit var userInteractions: UserInteractions

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        userInteractions.setActivity(this)
        userInteractions.startLiveLocation()
        userInteractions.enableNfc()

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val navController = rememberNavController()
            navigationManager.command.collectAsState().value?.also { command ->
                if (command == NavigationCommand.GoBack) {
                    navController.popBackStack()
                } else {
                    navController.popBackStack(command.route, true)
                    navController.navigate(command.route)
                }
                navigationManager.clear()
            }

            userInteractions.getLiveNfcTagId().observe(this) {
                if (it != null) {
                    navigationManager.navigate(MainNavigationCommands.nfcWithArgs(it))
                }
            }

            ProximeetyTheme {
                Surface(
                    color = MaterialTheme.colors.background
                ) {
                    NavHost(
                        navController = navController,
                        startDestination =
                        if (userInteractions.getAuthenticatedUser().value == null)
                            AuthenticationNavigationCommands.default.route
                        else
                            MainNavigationCommands.default.route,
                    ) {
                        authenticationNavigationGraph()
                        mainNavigationGraph()
                    }
                }
            }
        }
    }
}