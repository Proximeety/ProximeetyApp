package ch.proximeety.proximeety.presentation.navigation

import androidx.navigation.NamedNavArgument

/**
 * Interface representing commands used to navigate the application's graph.
 */
interface NavigationCommand {

    companion object {
        val GoBack = object : NavigationCommand {
            override val arguments = listOf<NamedNavArgument>()
            override val route = ""

        }
    }

    val arguments: List<NamedNavArgument>
    val route: String
}
