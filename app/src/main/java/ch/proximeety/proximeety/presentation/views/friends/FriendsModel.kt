package ch.proximeety.proximeety.presentation.views.friends

import ch.proximeety.proximeety.core.entities.User

/**
 * The Model for the Friends View.
 */
var user1: User = User(
    "1",
    "Tom",
    "user1",
    "user1",
    "user1@gmail.com"
)
var user2: User = User(
    "2",
    "Tomas",
    "user2",
    "user2",
    "user2@gmail.com",

    )
var user3: User = User(
    "3",
    "Mery",
    "user3",
    "user3",
    "user3@gmail.com"
)
var user4: User = User(
    "4",
    "Mark",
    "user4",
    "user4",
    "user4@gmail.com"
)

class FriendsModel {
    var users: List<User> = listOf()

    constructor(query : String = ""){
        users = listOf(user4, user2, user1, user3).sortedWith(compareBy { it.givenName })
        if (query.isNotEmpty()) {
            users = users.filter { (it.displayName).toLowerCase()!!.startsWith(query.toLowerCase()) }
        }
    }
}
