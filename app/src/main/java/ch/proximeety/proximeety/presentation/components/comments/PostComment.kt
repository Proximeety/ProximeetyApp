package ch.proximeety.proximeety.presentation.components.comments

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ch.proximeety.proximeety.core.entities.Comment
import ch.proximeety.proximeety.core.entities.CommentReply
import ch.proximeety.proximeety.core.entities.User
import ch.proximeety.proximeety.presentation.theme.spacing


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CommentSection(
    user: User?,
    comments: List<Comment>,
    replies: Map<String, List<CommentReply>>,
    onCloseClick: () -> Unit,
    onPostComment: (String) -> Unit,
    onCommentLike: (Comment) -> Unit,
    onPostReply: (Comment, String) -> Unit,
    onShowRepliesClick: (Comment) -> Unit
) {
    Column(
        modifier = Modifier.padding(spacing.extraSmall).fillMaxHeight(0.8f)
    ) {
        CommentTopBar(comments.size, onCloseClick)
        CommentPostComponent(user?.profilePicture, user?.displayName, onPostComment)
        LazyColumn(
            verticalArrangement = Arrangement.Top,
//            modifier = Modifier.fillMaxHeight(0.45f)
        ) {
            items(comments) { comment ->
                CommentComponent(
                    comment = comment,
                    replies = replies[comment.id] ?: emptyList(),
                    onCommentLike = { onCommentLike(comment) },
                    onPostReply = { onPostReply(comment, it) },
                    onShowRepliesClick = { onShowRepliesClick(comment) },
                    user?.displayName,
                    user?.profilePicture
                )
            }
        }
    }
}