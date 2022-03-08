package ch.proximeety.proximeety.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import ch.proximeety.proximeety.presentation.navigation.graphs.authenticationGraph
import ch.proximeety.proximeety.presentation.navigation.graphs.mainGraph

/**
 * Root Navigation
 */
@Composable
fun RootNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Graph.AuthenticationGraph.route,
        route = Graph.RootGraph.route
    ) {
        authenticationGraph(navController)
        mainGraph(navController)
    }
}