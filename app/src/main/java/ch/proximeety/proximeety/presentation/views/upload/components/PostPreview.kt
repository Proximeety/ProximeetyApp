package ch.proximeety.proximeety.presentation.views.upload.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import ch.proximeety.proximeety.presentation.theme.spacing
import ch.proximeety.proximeety.presentation.views.upload.EMPTY_IMAGE_URI
import ch.proximeety.proximeety.presentation.views.upload.UploadEvent
import ch.proximeety.proximeety.presentation.views.upload.UploadViewModel
import ch.proximeety.proximeety.util.SafeArea
import coil.compose.ImagePainter

@Composable
fun PostPreview(image: ImagePainter, viewModel: UploadViewModel) {
    SafeArea {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(spacing.medium),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Post", style = MaterialTheme.typography.h1)
            Spacer(modifier = Modifier.height(spacing.medium))
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(MaterialTheme.shapes.large),
                painter = image,
                contentScale = ContentScale.Crop,
                contentDescription = "Captured image"
            )
            Spacer(modifier = Modifier.height(spacing.medium))
            PostPreviewButtonBar(
                onCancel = {
                    viewModel.onEvent(
                        UploadEvent.SetPostURI(
                            EMPTY_IMAGE_URI
                        )
                    )
                },
                onValidate = { viewModel.onEvent(UploadEvent.Post) },
                postTypeName = "post"
            )
        }
    }
}
