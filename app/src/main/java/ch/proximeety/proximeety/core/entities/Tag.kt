package ch.proximeety.proximeety.core.entities

data class Tag(
    val id: String,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val visitors: List<Pair<Long, User>>,
    val owner: User
)
