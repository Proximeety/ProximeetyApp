package ch.proximeety.proximeety.core.interactions

import ch.proximeety.proximeety.core.entities.Post
import ch.proximeety.proximeety.core.repositories.UserRepository

class TogglePostLike(
    private val repository: UserRepository
) {
    suspend operator fun invoke(post: Post) {
        repository.togglePostLike(post)
    }
}