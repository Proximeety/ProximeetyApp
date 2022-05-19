package ch.proximeety.proximeety.presentation.views.upload.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import ch.proximeety.proximeety.presentation.theme.spacing
import ch.proximeety.proximeety.presentation.views.upload.EMPTY_IMAGE_URI
import ch.proximeety.proximeety.presentation.views.upload.UploadEvent
import ch.proximeety.proximeety.presentation.views.upload.UploadViewModel
import coil.compose.ImagePainter

@Composable
fun StoryPreview(image: ImagePainter, viewModel: UploadViewModel) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            modifier = Modifier
                .fillMaxSize(),
            painter = image,
            contentScale = ContentScale.Crop,
            contentDescription = "Captured image"
        )
        Text(
            text = "Story", modifier = Modifier
                .padding(top = spacing.large)
                .align(
                    Alignment.TopCenter
                ), style = MaterialTheme.typography.h1
        )
        PostPreviewButtonBar(
            onCancel = {
                viewModel.onEvent(
                    UploadEvent.SetPostURI(
                        EMPTY_IMAGE_URI
                    )
                )
            },
            onValidate = { viewModel.onEvent(UploadEvent.PostStory) },
            modifier = Modifier.align(Alignment.BottomCenter),
            postTypeName = "story"
        )
    }
}
