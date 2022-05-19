package ch.proximeety.proximeety.presentation.views.friends


/**
 * An event from the View to the ViewModel for the Friends View.
 */
sealed class FriendsEvent {
    class OnUserClick(val id: String) : FriendsEvent()
    class UpdateSearch(val query: String) : FriendsEvent()
}
