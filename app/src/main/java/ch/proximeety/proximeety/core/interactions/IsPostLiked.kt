package ch.proximeety.proximeety.core.interactions

import ch.proximeety.proximeety.core.entities.Post
import ch.proximeety.proximeety.core.repositories.UserRepository

class IsPostLiked(
    private val repository: UserRepository
) {
    suspend operator fun invoke(post: Post): Boolean {
        return repository.isPostLiked(post)
    }
}