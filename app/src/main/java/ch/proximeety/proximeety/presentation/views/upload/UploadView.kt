package ch.proximeety.proximeety.presentation.views.upload

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import ch.proximeety.proximeety.presentation.theme.spacing
import ch.proximeety.proximeety.presentation.views.upload.components.CameraCapture
import ch.proximeety.proximeety.presentation.views.upload.components.PostPreview
import ch.proximeety.proximeety.presentation.views.upload.components.StoryPreview
import ch.proximeety.proximeety.util.lerp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.math.absoluteValue

private val PICTURE_BUTTON_SIZE = 120.dp
val EMPTY_IMAGE_URI: Uri = Uri.parse("file://dev/null")

/**
 * The Upload View.
 */
@OptIn(
    ExperimentalPermissionsApi::class, ExperimentalCoroutinesApi::class,
    ExperimentalCoilApi::class, ExperimentalPagerApi::class
)
@Composable
fun UploadView(
    viewModel: UploadViewModel = hiltViewModel()
) {
    val imageUri = viewModel.imageUri.value

    if (imageUri != EMPTY_IMAGE_URI) {
        HorizontalPager(count = 2) { page ->

            val image = rememberImagePainter(imageUri)

            Box(modifier = Modifier.graphicsLayer {
                val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue
                lerp(0.85f, 1f, 1f - pageOffset.coerceIn(0f, 1f)).also { scale ->
                    scaleX = scale
                    scaleY = scale
                }
                alpha = lerp(0.5f, 1f, 1f - pageOffset.coerceIn(0f, 1f))
            }) {
                when (page) {
                    0 -> PostPreview(image = image, viewModel = viewModel)
                    1 -> StoryPreview(image = image, viewModel = viewModel)
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

