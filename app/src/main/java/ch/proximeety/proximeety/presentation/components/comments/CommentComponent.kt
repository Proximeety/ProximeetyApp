package ch.proximeety.proximeety.presentation.components.comments

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.Reply
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ch.proximeety.proximeety.core.entities.Comment
import ch.proximeety.proximeety.presentation.theme.spacing

@Composable
fun CommentComponent(
    comment: Comment,
    onCommentLike: (Comment) -> Unit,
    onReplyClick: () -> Unit,
    onShowRepliesClick: () -> Unit
) {
    val commentLikeContentDescricption = "Like Comment"
    val commentReplyContentDescricption = "Reply to Comment"
    var showRepliesText =
        if (comment.replies == 1)
            "Show reply"
        else
            "Show ${comment.replies} replies"

    Row(
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier
            .padding(all = spacing.small)
            .fillMaxWidth()
    ) {
        CommentProfilePic(
            picUrl = comment.userProfilePicture,
            displayName = comment.userDisplayName
        )

        Spacer(modifier = Modifier.size(spacing.small))

        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {

            Text(
                text = comment.userDisplayName,
                style = MaterialTheme.typography.subtitle2
            )

            Spacer(modifier = Modifier.height(spacing.small))

            Text(
                text = comment.comment,
                style = MaterialTheme.typography.body2
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
            ) {
                IconButton(
                    onClick = {
                        onCommentLike(comment)
                    },
                    modifier = Modifier
                        .padding(all = spacing.small)
                        .size(spacing.medium)
                ) {
                    Icon(
                        imageVector = if (comment.isLiked) Icons.Rounded.Favorite
                        else Icons.Rounded.FavoriteBorder,
                        contentDescription = commentLikeContentDescricption,
                    )
                }

                IconButton(
                    onClick = {
                        onReplyClick
                    },
                    modifier = Modifier
                        .padding(all = spacing.small)
                        .size(spacing.medium)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Reply,
                        contentDescription = commentReplyContentDescricption
                    )
                }

                Spacer(modifier = Modifier.width(spacing.extraSmall))

                Text(text = comment.likes.toString(), style = MaterialTheme.typography.h5)
            }

            if (comment.hasReplies) {
                Text(
                    text = showRepliesText,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(onClick = onShowRepliesClick),
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colors.secondary
                )
            }
        }
    }
}
