package ch.proximeety.proximeety.presentation.navigation.graphs

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
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

    val conversationList = object : NavigationCommand {
        override val arguments = emptyList<NamedNavArgument>()
        override val route = "conversation_list_view"
    }

    val nearbyUsers = object : NavigationCommand {
        override val arguments = emptyList<NamedNavArgument>()
        override val route = "nearby_users_view"
    }

    val map = object : NavigationCommand {
        override val arguments = emptyList<NamedNavArgument>()
        override val route = "map_view"
    }

    val profile = object : NavigationCommand {
        override val arguments: List<NamedNavArgument> =
            listOf(navArgument("userId") { type = NavType.StringType })
        override val route = "profile/{userId}"
    }

    fun profileWithArgs(userId: String) = object : NavigationCommand {
        override val arguments: List<NamedNavArgument> = profile.arguments
        override val route: String = "profile/$userId"
    }

    val upload = object : NavigationCommand {
        override val arguments = emptyList<NamedNavArgument>()
        override val route = "upload_view"
    }
}
