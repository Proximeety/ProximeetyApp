package ch.proximeety.proximeety.presentation.navigation.graphs

/**
 * Represents a view in the main navigation.
 * @param route the navigation route.
 */
sealed class MainView(val route: String) {
    object HomeView : MainView("home_view")
}
