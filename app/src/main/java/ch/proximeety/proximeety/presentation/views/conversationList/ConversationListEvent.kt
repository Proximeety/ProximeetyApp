package ch.proximeety.proximeety.presentation.views.conversationList

/**
 * An event from the View to the ViewModel for the Messages View.
 */
sealed class ConversationListEvent {
    class ConversationClick(val message: MessagesModel) : ConversationListEvent()
    // TODO will have to change this signature here to send user IDs and then load the message inside?
}