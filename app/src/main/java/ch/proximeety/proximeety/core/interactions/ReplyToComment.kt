package ch.proximeety.proximeety.core.interactions

import ch.proximeety.proximeety.core.repositories.UserRepository

/**
 * A user interaction used to leave a comment under a post.
 * @param repository the user repository.
 */
class ReplyToComment(
    private val repository: UserRepository
) {
    suspend operator fun invoke(postId: String, commentId: String, commentReply: String) {
        return repository.replyToComment(postId, commentId, commentReply)
    }
}