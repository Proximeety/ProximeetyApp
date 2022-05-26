package ch.proximeety.proximeety.presentation.components.comments

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.Reply
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import ch.proximeety.proximeety.core.entities.Comment
import ch.proximeety.proximeety.core.entities.CommentReply
import ch.proximeety.proximeety.presentation.theme.spacing

@Composable
fun CommentComponent(
    comment: Comment,
    replies: List<CommentReply>,
    onCommentLike: () -> Unit,
    onPostReply: (String) -> Unit,
    onShowRepliesClick: () -> Unit,
    userDisplayName: String?,
    userPicUrl: String?,
) {
    val reply = remember { mutableStateOf("") }
    val showReplies = remember { mutableStateOf(false) }

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
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {

            Row {
                Text(
                    text = comment.userDisplayName,
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(spacing.small))
                Text(
                    text = comment.comment,
                    style = MaterialTheme.typography.body1
                )
            }
            Spacer(modifier = Modifier.height(spacing.extraSmall))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
            ) {
                Row(
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable { onCommentLike() },
                    horizontalArrangement = Arrangement.spacedBy(spacing.extraSmall),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = if (comment.isLiked) Icons.Rounded.Favorite
                        else Icons.Rounded.FavoriteBorder,
                        contentDescription = "Like",
                        modifier = Modifier.size(spacing.medium)
                    )
                    Text(text = comment.likes.toString(), style = MaterialTheme.typography.body1)
                }
                Spacer(modifier = Modifier.width(spacing.small))
                Row(
                    modifier = Modifier
                        .clip(CircleShape),
                    horizontalArrangement = Arrangement.spacedBy(spacing.extraSmall),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Reply,
                        contentDescription = "Reply",
                        modifier = Modifier.size(spacing.medium)
                    )
                    Text(text = comment.replies.toString(), style = MaterialTheme.typography.body1)
                }
                Spacer(modifier = Modifier.width(spacing.small))
                Box(modifier = Modifier
                    .clip(CircleShape)
                    .clickable {
                        showReplies.value = !showReplies.value
                        if (showReplies.value) {
                            onShowRepliesClick()
                        }
                    }) {
                    Text(
                        text = if (showReplies.value) "Hide replies" else "Show replies",
                        style = MaterialTheme.typography.body1,
                        color = MaterialTheme.colors.onBackground,
                        modifier = Modifier
                            .padding(all = spacing.extraSmall)
                    )
                }
            }

            if (showReplies.value) {
                replies.forEach { reply ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(spacing.small),
                    ) {
                        CommentProfilePic(
                            picUrl = userPicUrl,
                            displayName = userDisplayName,
                            small = true
                        )
                        Row {
                            Text(
                                text = reply.userDisplayName,
                                style = MaterialTheme.typography.body1,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.width(spacing.small))
                            Text(
                                text = reply.commentReply,
                                style = MaterialTheme.typography.body1,
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(spacing.extraSmall))
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(spacing.small),
                ) {
                    CommentProfilePic(
                        picUrl = userPicUrl,
                        displayName = userDisplayName,
                        small = true
                    )
                    Box {
                        BasicTextField(
                            value = reply.value,
                            onValueChange = { reply.value = it },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            textStyle = MaterialTheme.typography.body1.copy(
                                color = MaterialTheme.colors.onBackground
                            ),
                            cursorBrush = SolidColor(MaterialTheme.colors.onBackground),
                            keyboardActions = KeyboardActions(
                                onSend = {
                                    onPostReply(reply.value)
                                    reply.value = ""
                                }
                            ),
                            keyboardOptions = KeyboardOptions.Default.copy(
                                imeAction = ImeAction.Send
                            )
                        )
                        if (reply.value.isBlank()) {
                            Text(
                                text = "Add a reply...",
                                style = MaterialTheme.typography.body1,
                                color = Color.Gray,
                            )
                        }
                    }
                }
            }
        }
    }
}
