package ch.proximeety.proximeety.presentation.views.messages

/**
 * The Model for Messages View
 */
data class MessagesModel(
    val time: String,
    val message: String,
    val sender: String,
    val senderImage: String? = null
)