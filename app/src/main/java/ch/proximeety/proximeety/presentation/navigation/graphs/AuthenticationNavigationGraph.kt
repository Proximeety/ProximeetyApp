package ch.proximeety.proximeety.presentation.navigation.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import ch.proximeety.proximeety.presentation.views.authenticationHome.AuthenticationHomeView
import com.google.accompanist.pager.ExperimentalPagerApi

/**
 * Authentication navigation. Nested in root navigation.
 */
@ExperimentalPagerApi
fun NavGraphBuilder.authenticationNavigationGraph() {
    navigation(
        startDestination = AuthenticationNavigationCommands.authenticationHome.route,
        route = AuthenticationNavigationCommands.default.route
    ) {
        composable(
            route = AuthenticationNavigationCommands.authenticationHome.route
        ) {
            AuthenticationHomeView()
        }
    }
}
