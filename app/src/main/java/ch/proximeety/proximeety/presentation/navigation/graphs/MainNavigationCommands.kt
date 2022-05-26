package ch.proximeety.proximeety.presentation.navigation.graphs

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import ch.proximeety.proximeety.presentation.navigation.NavigationCommand
import com.google.android.gms.maps.model.LatLng
import java.util.jar.Attributes

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
    val messages = object : NavigationCommand {
        override val arguments = emptyList<NamedNavArgument>()
        override val route = "messages_view"
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
    val settings = object : NavigationCommand {
        override val arguments = emptyList<NamedNavArgument>()
        override val route = "settings_view"
    }
    val friends = object : NavigationCommand {
        override val arguments = emptyList<NamedNavArgument>()
        override val route = "friends_view"
    }
    val stories = object : NavigationCommand {
        override val arguments: List<NamedNavArgument> =
            listOf(navArgument("userId") { type = NavType.StringType })
        override val route = "stories/{userId}"
    }

    fun storiesWithArgs(userId: String) = object : NavigationCommand {
        override val arguments: List<NamedNavArgument> = stories.arguments
        override val route: String = "stories/$userId"
    }

    val nfc = object : NavigationCommand {
        override val arguments = listOf(navArgument("tagId") {type = NavType.StringType})
        override val route = "nfc_view/{tagId}"
    }

    fun nfcWithArgs(tagId: String) = object : NavigationCommand {
        override val arguments: List<NamedNavArgument> = nfc.arguments
        override val route: String = "nfc_view/$tagId"
    }

    val post = object : NavigationCommand {
        override val arguments: List<NamedNavArgument> =
            listOf(navArgument("userId") { type = NavType.StringType },
                navArgument("postId") { type = NavType.StringType })
        override val route = "posts/{userId}/{postId}"
    }

    fun postWithArgs(userId: String, postId: String) = object : NavigationCommand {
        override val arguments: List<NamedNavArgument> = post.arguments
        override val route: String = "posts/$userId/$postId"
    }
}
