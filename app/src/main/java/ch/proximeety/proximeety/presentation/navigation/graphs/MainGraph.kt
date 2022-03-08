package ch.proximeety.proximeety.presentation.navigation.graphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import ch.proximeety.proximeety.presentation.navigation.Graph
import ch.proximeety.proximeety.presentation.views.home.HomeView

/**
 * Main navigation. Nested in Root Navigation.
 */
fun NavGraphBuilder.mainGraph(navController: NavController) {
    navigation(
        startDestination = AuthenticationView.AuthenticationHomeView.route,
        route = Graph.MainGraph.route
    ) {
        composable(
            route = MainView.HomeView.route
        ) {
            HomeView(navController = navController)
        }
    }
}