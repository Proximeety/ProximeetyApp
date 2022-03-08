package ch.proximeety.proximeety.presentation.navigation.graphs

/**
 * Represents a view in the authentication navigation.
 * @param route the navigation route.
 */
sealed class AuthenticationView(val route: String) {
    object AuthenticationHomeView : AuthenticationView("authentication_home_view")
}
