package ch.proximeety.proximeety.presentation.views.post

import ch.proximeety.proximeety.core.entities.Comment
import ch.proximeety.proximeety.core.entities.Post

sealed class PostEvent {
    class OnCommentSectionClick(val postId: String) : PostEvent()
    class TogglePostLike(val post: Post) : PostEvent()
    class DownloadPost(val post: Post) : PostEvent()
    class PostComment(val text: String) : PostEvent()
    class ToggleCommentLike(val comment: Comment) : PostEvent()
    object RefreshComments : PostEvent()
}