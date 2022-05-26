package ch.proximeety.proximeety.presentation.views.upload.components

import android.Manifest
import android.annotation.SuppressLint
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY
import androidx.camera.core.Preview
import androidx.camera.core.UseCase
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import ch.proximeety.proximeety.presentation.theme.spacing
import ch.proximeety.proximeety.util.extensions.executor
import ch.proximeety.proximeety.util.extensions.getCameraProvider
import ch.proximeety.proximeety.util.extensions.takePicture
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import java.io.File

@SuppressLint("RestrictedApi")
@ExperimentalPermissionsApi
@ExperimentalCoroutinesApi
@Composable
fun CameraCapture(
    modifier: Modifier = Modifier,
    cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA,
    onImageFile: (File) -> Unit
) {
    val context = LocalContext.current

    val permission = rememberPermissionState(permission = Manifest.permission.CAMERA)

    Box(modifier = modifier) {
        val lifecycleOwner = LocalLifecycleOwner.current
        val coroutineScope = rememberCoroutineScope()
        var previewUseCase by remember {
            mutableStateOf<UseCase>(
                Preview.Builder().setCameraSelector(
                    CameraSelector.DEFAULT_BACK_CAMERA
                ).build()
            )
        }
        val imageCaptureUseCase by remember {
            mutableStateOf(
                ImageCapture.Builder()
                    .setCaptureMode(CAPTURE_MODE_MAXIMIZE_QUALITY)
                    .setCameraSelector(CameraSelector.DEFAULT_BACK_CAMERA)
                    .build()
            )
        }
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            if (permission.status == PermissionStatus.Granted) {
                CameraPreview(
                    modifier = Modifier.fillMaxSize(),
                    onUseCase = {
                        previewUseCase = it
                    }
                )
            } else {
                Button(onClick = { permission.launchPermissionRequest() }) {
                    Text("Enable permissions")
                }
            }
            CapturePictureButton(
                modifier = Modifier
                    .size(120.dp)
                    .padding(spacing.medium)
                    .align(Alignment.BottomCenter),
                onClick = {
                    coroutineScope.launch {
                        onImageFile(imageCaptureUseCase.takePicture(context.executor))
                    }
                }
            )
        }

        LaunchedEffect(previewUseCase) {
            val cameraProvider = context.getCameraProvider()
            try {
                // Must unbind the use-cases before rebinding them.
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    lifecycleOwner, cameraSelector, previewUseCase, imageCaptureUseCase
                )
            } catch (ex: Exception) {
                Log.e("CameraCapture", "Failed to bind camera use cases", ex)
            }
        }
    }
}