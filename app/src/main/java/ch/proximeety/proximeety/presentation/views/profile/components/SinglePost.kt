package ch.proximeety.proximeety.presentation.views.profile.components

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MoreHoriz
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
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


    Column(){
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

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun ButtonExtended(viewModel: ProfileViewModel, onDelete: () -> Unit){
    val menuExpanded = remember { mutableStateOf(false) }
    val showDialog = remember { mutableStateOf(false) }

    IconButton( onClick = { menuExpanded.value = true }) {
        Icon(imageVector = Icons.Rounded.MoreHoriz, contentDescription = "More")
    }

    Column() {
        DropdownMenu(
            expanded = menuExpanded.value,
            onDismissRequest = {
                menuExpanded.value = false
            },
            modifier = Modifier.width(200.dp)
        ) {
            DropdownMenuItem(onClick = {
                menuExpanded.value = false
                showDialog.value = true
                viewModel.onOpenDialogClicked()
            }) {
                Text(text = "Delete post")
            }

            DropdownMenuItem(onClick = {  }) {
                Text(text = "More")
            }
        }
    }

    if (showDialog.value) {
        val showDialogState = viewModel.showDialog.collectAsState()
        SimpleAlertDialog(
            show = showDialogState,
            onDismiss = { viewModel.onCloseDialog() },
            onConfirm = onDelete
        )
    }
}


@Composable
fun SimpleAlertDialog(
    show: State<Boolean>,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    if (show.value) {
        AlertDialog(
            onDismissRequest = onDismiss,
            confirmButton = {
                TextButton(onClick = onConfirm)
                { Text(text = "Confirm") }
            },
            dismissButton = {
                TextButton(onClick = onDismiss)
                { Text(text = "Cancel") }
            },
            title = { Text(text = "Delete post") },
            text = { Text(text = "Do you want to continue?") }
        )
    }
}
