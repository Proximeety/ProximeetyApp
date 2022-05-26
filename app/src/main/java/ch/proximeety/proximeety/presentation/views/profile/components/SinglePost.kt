package ch.proximeety.proximeety.presentation.views.profile.components

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import ch.proximeety.proximeety.core.entities.Post
import ch.proximeety.proximeety.presentation.views.profile.ProfileEvent
import ch.proximeety.proximeety.presentation.views.profile.ProfileViewModel
import coil.compose.rememberImagePainter

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun SinglePost(
    post: Post,
    viewModel: ProfileViewModel,
    onDelete: () -> Unit
) {
    val isAuthenticatedUserProfile = viewModel.isAuthenticatedUserProfile

    BoxWithConstraints(
        contentAlignment = Alignment.BottomEnd
    ) {
        val size = constraints.maxWidth
        Image(
            painter = rememberImagePainter(post.postURL),
            contentScale = ContentScale.Crop,
            contentDescription = "Post",
            modifier = Modifier
                .aspectRatio(1f, matchHeightConstraintsFirst = false)
                .fillMaxSize()
                .clip(MaterialTheme.shapes.medium)
                .clickable { viewModel.onEvent(ProfileEvent.OnPostClick(post.id)) }
        )
        if (isAuthenticatedUserProfile) {
            ButtonExtended(viewModel, onDelete)
        }
    }
}
