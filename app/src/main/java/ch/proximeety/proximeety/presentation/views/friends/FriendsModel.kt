package ch.proximeety.proximeety.presentation.views.friends

import ch.proximeety.proximeety.core.entities.User

/**
 * The Model for the Friends View.
 */
var user1: User = User(
    "User 1",
    "user1",
    "user1",
    "user1@gmail.com",
    "1"
)
var user2: User = User(
    "User 2",
    "user2",
    "user2",
    "user2@gmail.com",
    "2"
)
var user3: User = User(
    "User 3",
    "user3",
    "user3",
    "user3@gmail.com",
    "1"
)
var user4: User = User(
    "User 4",
    "user4",
    "user4",
    "user4@gmail.com",
    "1"
)



data class FriendsModel (
    var users: List<User> = listOf(user1, user2, user3, user4)
)