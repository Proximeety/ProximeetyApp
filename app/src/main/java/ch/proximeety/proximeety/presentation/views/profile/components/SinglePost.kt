package ch.proximeety.proximeety.presentation.views.profile.components

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import ch.proximeety.proximeety.core.entities.Post
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

    Column {
        Image(
            painter = rememberImagePainter(post.postURL),
            contentScale = ContentScale.Crop,
            contentDescription = "Post",
            modifier = Modifier
                .aspectRatio(1f, matchHeightConstraintsFirst = false)
                .fillMaxSize()
                .clip(MaterialTheme.shapes.medium)
        )
        if (isAuthenticatedUserProfile) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ){
                ButtonExtended(viewModel, onDelete)
            }
        }
    }
}
