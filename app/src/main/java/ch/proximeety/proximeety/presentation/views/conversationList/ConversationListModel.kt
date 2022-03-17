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

var msg1 = MessagesModel("333", "test1", "sender1")

val msg2 = MessagesModel(
    sender = "Test sender",
    time = "10:00 AM",
    message = "Hello there, how are you?"
)