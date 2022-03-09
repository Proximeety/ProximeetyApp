package ch.proximeety.proximeety.presentation.navigation

import androidx.navigation.NamedNavArgument

/**
 * Interface representing commands used to navigate the application's graph.
 */
interface NavigationCommand {
    val arguments: List<NamedNavArgument>
    val route: String
}