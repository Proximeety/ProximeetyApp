package ch.proximeety.proximeety.presentation.views.home.components.comments

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ch.proximeety.proximeety.core.entities.Comment
import ch.proximeety.proximeety.core.entities.User
import ch.proximeety.proximeety.presentation.theme.spacing


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CommentSection(
    user: User?,
    comments: List<Comment>,
    onCloseClick: () -> Unit,
    onPostClick: (String) -> Unit
) {

    Column(
        modifier = Modifier.padding(MaterialTheme.spacing.extraSmall)
    ) {
        CommentTopBar(comments.size, onCloseClick)
        CommentPostComponent(user?.profilePicture, user?.displayName, onPostClick)

        LazyColumn(
            verticalArrangement = Arrangement.Top,
            modifier = Modifier.fillMaxHeight(0.45f)
        ) {
            items(comments) {
                CommentComponent(it)
            }
        }
    }
}