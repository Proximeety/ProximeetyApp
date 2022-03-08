package ch.proximeety.proximeety.presentation.navigation.graphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import ch.proximeety.proximeety.presentation.navigation.Graph
import ch.proximeety.proximeety.presentation.views.authenticationHome.AuthenticationHomeView

/**
 * Authentication navigation. Nested in root navigation.
 */
fun NavGraphBuilder.authenticationGraph(navController: NavController) {
    navigation(
        startDestination = AuthenticationView.AuthenticationHomeView.route,
        route = Graph.AuthenticationGraph.route
    ) {
        composable(
            route = AuthenticationView.AuthenticationHomeView.route
        ) {
            AuthenticationHomeView(navController = navController)
        }
    }
}