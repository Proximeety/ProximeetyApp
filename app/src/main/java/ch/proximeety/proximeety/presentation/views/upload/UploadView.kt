package ch.proximeety.proximeety.presentation.views.upload

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import ch.proximeety.proximeety.R
import ch.proximeety.proximeety.presentation.theme.spacing
import ch.proximeety.proximeety.presentation.views.upload.components.CameraCapture
import ch.proximeety.proximeety.util.SafeArea
import coil.compose.rememberImagePainter
import com.google.accompanist.permissions.ExperimentalPermissionsApi

private val PICTURE_BUTTON_SIZE = 120.dp
val EMPTY_IMAGE_URI: Uri = Uri.parse("file://dev/null")

/**
 * The Upload View.
 */
@OptIn(
    ExperimentalPermissionsApi::class, kotlinx.coroutines.ExperimentalCoroutinesApi::class,
    coil.annotation.ExperimentalCoilApi::class
)
@Composable
fun UploadView(
    viewModel: UploadViewModel = hiltViewModel()
) {
    val imageUri = viewModel.imageUri.value

    if (imageUri != EMPTY_IMAGE_URI) {
        SafeArea {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(modifier = Modifier.padding(MaterialTheme.spacing.medium)) {
                    Image(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f)
                            .clip(MaterialTheme.shapes.large),
                        painter = rememberImagePainter(imageUri),
                        contentScale = ContentScale.Crop,
                        contentDescription = "Captured image"
                    )
                    Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
                }
                Row {
                    IconButton(
                        onClick = { viewModel.onEvent(UploadEvent.SetPostURI(EMPTY_IMAGE_URI)) },
                        modifier = Modifier.testTag(stringResource(id = R.string.TT_UV_cancel_post_button))
                    ) {
                        Icon(
                            imageVector = Icons.Default.Cancel,
                            contentDescription = "Cancel",
                            modifier = Modifier.size(48.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(MaterialTheme.spacing.medium))
                    IconButton(
                        onClick = { viewModel.onEvent(UploadEvent.Post) },
                        modifier = Modifier.testTag(stringResource(id = R.string.TT_UV_post_button))
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Upload",
                            modifier = Modifier.size(48.dp)
                        )
                    }
                }
            }
        }
    } else {
        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent(),
            onResult = { uri: Uri? ->
                uri?.also { viewModel.onEvent(UploadEvent.SetPostURI(it)) }
            }
        )
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            CameraCapture(
                modifier = Modifier
                    .fillMaxSize(),
                onImageFile = { file ->
                    viewModel.onEvent(UploadEvent.SetPostURI(file.toUri()))
                }
            )
            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .height(PICTURE_BUTTON_SIZE)
                    .padding(MaterialTheme.spacing.small),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    modifier = Modifier
                        .padding(MaterialTheme.spacing.medium),
                    onClick = {
                        launcher.launch("image/*")
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Image,
                        contentDescription = "Image",
                        tint = Color.White
                    )
                }
            }
        }
    }
}

