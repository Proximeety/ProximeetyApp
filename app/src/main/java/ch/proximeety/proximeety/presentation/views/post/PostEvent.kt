package ch.proximeety.proximeety.presentation.views.post

import ch.proximeety.proximeety.core.entities.Comment
import ch.proximeety.proximeety.core.entities.Post

sealed class PostEvent {
    class DownloadPost(val post: Post) : PostEvent()
    class OnStoryClick(val id: String) : PostEvent()
    class OnCommentSectionClick(val postId: String) : PostEvent()
    class PostComment(val text: String) : PostEvent()
    class PostReply(val comment: Comment, val reply: String) : PostEvent()
    class DownloadReplies(val comment: Comment) : PostEvent()
    object Refresh : PostEvent()
    object RefreshComments : PostEvent()
    class TogglePostLike(val post: Post) : PostEvent()
    class ToggleCommentLike(val comment: Comment) : PostEvent()
}