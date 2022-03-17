package ch.proximeety.proximeety.presentation.navigation.graphs

import androidx.navigation.NamedNavArgument
import ch.proximeety.proximeety.presentation.navigation.NavigationCommand

/**
 * Contains the commands to navigate the through the main graph of the application.
 */
object MainNavigationCommands {
    val default = object : NavigationCommand {
        override val arguments = emptyList<NamedNavArgument>()
        override val route = "main_graph"
    }
    val home = object : NavigationCommand {
        override val arguments = emptyList<NamedNavArgument>()
        override val route = "home_view"
    }
    val nearbyUsers = object : NavigationCommand {
        override val arguments = emptyList<NamedNavArgument>()
        override val route = "nearby_users_view"
    }
}
