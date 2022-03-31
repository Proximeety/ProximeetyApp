package ch.proximeety.proximeety.presentation.views.conversationList

/**
 * An event from the View to the ViewModel for the Messages View.
 */
sealed class ConversationListEvent {
    class ConversationClick(val message: MessagesModel) : ConversationListEvent()
}