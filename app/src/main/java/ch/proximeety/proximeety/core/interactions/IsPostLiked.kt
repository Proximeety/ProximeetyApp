package ch.proximeety.proximeety.core.interactions

import ch.proximeety.proximeety.core.repositories.UserRepository

class IsPostLiked (
    private val repository: UserRepository
) {
    suspend operator fun invoke(postId: String): Boolean {
        return repository.isPostLiked(postId)
    }
}