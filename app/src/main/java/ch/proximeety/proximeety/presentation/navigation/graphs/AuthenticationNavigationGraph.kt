package ch.proximeety.proximeety.presentation.navigation.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import ch.proximeety.proximeety.presentation.views.authenticationHome.AuthenticationHomeView

/**
 * Authentication navigation. Nested in root navigation.
 */
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