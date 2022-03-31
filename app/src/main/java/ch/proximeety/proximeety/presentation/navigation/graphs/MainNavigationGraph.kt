package ch.proximeety.proximeety.presentation.navigation.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import ch.proximeety.proximeety.presentation.views.conversationList.ConversationListView
import ch.proximeety.proximeety.presentation.views.friends.FriendsView
import ch.proximeety.proximeety.presentation.views.home.HomeView
import ch.proximeety.proximeety.presentation.views.mapView.MapView
import ch.proximeety.proximeety.presentation.views.messagesScreen.MessagesView
import ch.proximeety.proximeety.presentation.views.nearbyUsers.NearbyUsersView
import ch.proximeety.proximeety.presentation.views.profile.ProfileView
import ch.proximeety.proximeety.presentation.views.upload.UploadView

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
        composable(
            route = MainNavigationCommands.conversationList.route
        ) {
            ConversationListView()
        }
        composable(
            route = MainNavigationCommands.profile.route,
            arguments = MainNavigationCommands.profile.arguments
        ) {
            ProfileView()
        }
        composable(
            route = MainNavigationCommands.map.route
        ) {
            MapView()
        }
        composable(
            route = MainNavigationCommands.messages.route
        ) {
            MessagesView()
        }
        
        composable(
            route = MainNavigationCommands.upload.route
        ) {
            UploadView()
        }
        composable(
            route = MainNavigationCommands.friends.route
        ) {
            FriendsView()
        }
    }
}