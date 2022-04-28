package ch.proximeety.proximeety.presentation.views.home.components.comments

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ch.proximeety.proximeety.core.entities.Comment
import ch.proximeety.proximeety.core.entities.User
import ch.proximeety.proximeety.presentation.theme.Shapes
import ch.proximeety.proximeety.presentation.theme.spacing
import ch.proximeety.proximeety.presentation.views.home.components.comments.CommentComponent
import ch.proximeety.proximeety.presentation.views.home.components.comments.CommentPostComponent
import ch.proximeety.proximeety.presentation.views.home.components.comments.CommentTopBar
import coil.compose.rememberImagePainter


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CommentSection(
    user: User?,
    comments: List<Comment>,
    onCloseClick: () -> Unit,
    onPostClick: (String) -> Unit,
) {

    Column(
        modifier = Modifier.padding(30.dp)
    ) {
        CommentTopBar(numComments = 10, onCloseClick)
        CommentPostComponent(user, onPostClick)

        LazyColumn {
            items(comments) {
                CommentComponent(it)
            }
        }
    }
}