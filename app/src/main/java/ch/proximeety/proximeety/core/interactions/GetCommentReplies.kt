package ch.proximeety.proximeety.core.interactions

import ch.proximeety.proximeety.core.entities.CommentReply
import ch.proximeety.proximeety.core.repositories.UserRepository

/**
 * A user interaction used to get the list of comments by postId.
 * @param repository the user repository.
 */
class GetCommentReplies(
    private val repository: UserRepository
) {
    suspend operator fun invoke(commentId : String): List<CommentReply> {
        return repository.getCommentReplies(commentId)
    }
}