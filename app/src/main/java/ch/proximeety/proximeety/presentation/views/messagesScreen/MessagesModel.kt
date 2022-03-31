package ch.proximeety.proximeety.presentation.views.messagesScreen

data class MessagesModel (
    val from: String,
    val to: String,
    val timestamp: Long,
    val value: String,
)


val msg1 = MessagesModel(
    from = "TJepm7a0BVXNOLhrxNqNJhDHk4r1",
    to = "BztoRkZs2JT9cHuVSJt2u7s3Qcw2",
    timestamp = 1648144048000,
    value = "Hello my friend!"
)

val msg2 = MessagesModel(
    to = "TJepm7a0BVXNOLhrxNqNJhDHk4r1",
    from = "BztoRkZs2JT9cHuVSJt2u7s3Qcw2",
    timestamp = 1648144048100,
    value = "Hello my friend!"
)