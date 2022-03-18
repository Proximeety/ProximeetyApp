package ch.proximeety.proximeety.presentation.views.friends

import ch.proximeety.proximeety.core.entities.User

/**
 * The Model for the Friends View.
 */
var user1: User = User(
    "1",
    "User 1",
    "user1",
    "user1",
    "user1@gmail.com"
)
var user2: User = User(
    "2",
    "User 2",
    "user2",
    "user2",
    "user2@gmail.com",

)
var user3: User = User(
    "3",
    "User 3",
    "user3",
    "user3",
    "user3@gmail.com"
)
var user4: User = User(
    "4",
    "User 4",
    "user4",
    "user4",
    "user4@gmail.com"
)



data class FriendsModel (
    var users: List<User> = listOf(user1, user2, user3, user4)
)