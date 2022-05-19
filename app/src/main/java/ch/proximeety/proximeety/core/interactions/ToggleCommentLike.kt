package ch.proximeety.proximeety.core.interactions

import ch.proximeety.proximeety.core.entities.Comment
import ch.proximeety.proximeety.core.repositories.UserRepository

class ToggleCommentLike(
    private val repository: UserRepository
) {
    suspend operator fun invoke(comment: Comment) {
        repository.toggleCommentLike(comment)
    }
}