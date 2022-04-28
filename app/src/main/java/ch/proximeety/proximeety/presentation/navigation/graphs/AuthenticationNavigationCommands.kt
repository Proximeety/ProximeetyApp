package ch.proximeety.proximeety.presentation.navigation.graphs

import androidx.navigation.NamedNavArgument
import ch.proximeety.proximeety.presentation.navigation.NavigationCommand

/**
 * Contains the commands to navigate the through the authentication graph.
 */
object AuthenticationNavigationCommands {
    val default = object : NavigationCommand {
        override val arguments = emptyList<NamedNavArgument>()
        override val route = "authentication_graph"
    }
    val authenticationHome = object : NavigationCommand {
        override val arguments = emptyList<NamedNavArgument>()
        override val route = "authentication_home_view"
    }
}
