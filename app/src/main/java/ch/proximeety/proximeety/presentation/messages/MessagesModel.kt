package ch.proximeety.proximeety.presentation.messages

import ch.proximeety.proximeety.core.entities.User

/**
 * The Model for Messages View
 */
data class MessagesModel(
    val time: String,
    val message: String,
    val sender: String,
    val senderImage: String? = null
)