package ch.proximeety.proximeety.presentation.views.authenticationHome

data class AuthenticationHomeModel(
    val title: String
)

val screens = listOf(
    AuthenticationHomeModel("Welcome to Proximeety!"),
    AuthenticationHomeModel("The new social network to stay closer to your friends"),
    AuthenticationHomeModel("Do you already have an account?")
)
