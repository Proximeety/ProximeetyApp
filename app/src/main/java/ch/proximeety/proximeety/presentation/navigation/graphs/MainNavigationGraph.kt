package ch.proximeety.proximeety.presentation.navigation.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import ch.proximeety.proximeety.presentation.views.home.HomeView
import ch.proximeety.proximeety.presentation.views.nearbyUsers.NearbyUsersView

/**
 * Main navigation. Nested in Root Navigation.
 */
fun NavGraphBuilder.mainNavigationGraph() {
    navigation(
        startDestination = MainNavigationCommands.home.route,
        route = MainNavigationCommands.default.route
    ) {
        composable(
            route = MainNavigationCommands.home.route
        ) {
            HomeView()
        }
        composable(
            route = MainNavigationCommands.nearbyUsers.route
        ) {
            NearbyUsersView()
        }
    }
}