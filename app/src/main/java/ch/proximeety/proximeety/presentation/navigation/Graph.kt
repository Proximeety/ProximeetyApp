package ch.proximeety.proximeety.presentation.navigation

/**
 * Represents a view in the root navigation.
 * @param route the navigation route.
 */
sealed class Graph(val route: String) {
   object RootGraph : Graph("root_graph")
   object AuthenticationGraph : Graph("authentication_graph")
   object MainGraph : Graph("main_graph")
}
