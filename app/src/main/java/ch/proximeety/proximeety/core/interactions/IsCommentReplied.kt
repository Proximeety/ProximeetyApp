package ch.proximeety.proximeety.core.interactions

import ch.proximeety.proximeety.core.entities.Comment
import ch.proximeety.proximeety.core.repositories.UserRepository

class IsCommentReplied(
    private val repository: UserRepository
) {
    suspend operator fun invoke(comment: Comment): Boolean {
        return repository.hasReplies(comment)
    }
}