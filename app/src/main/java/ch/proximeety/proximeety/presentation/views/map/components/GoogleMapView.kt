package ch.proximeety.proximeety.presentation.views.map.components

import android.graphics.Bitmap
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.core.graphics.drawable.toBitmap
import ch.proximeety.proximeety.presentation.theme.spacing
import ch.proximeety.proximeety.presentation.views.map.MapViewModel
import ch.proximeety.proximeety.util.SafeArea
import ch.proximeety.proximeety.util.extensions.getRoundedCroppedBitmap
import coil.compose.LocalImageLoader
import coil.request.ImageRequest
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

@Composable
fun GoogleMapView(
    modifier: Modifier,
    cameraPositionState: CameraPositionState,
    onMapLoaded: () -> Unit,
    viewModel: MapViewModel,
) {

    val context = LocalContext.current
    val imageLoader = LocalImageLoader.current

    val uiSettings by remember {
        mutableStateOf(
            MapUiSettings(
                myLocationButtonEnabled = true,
            )
        )
    }

    val mapProperties = remember {
        mutableStateOf(MapProperties(isMyLocationEnabled = true))
    }

    val friendsPosition = viewModel.friendsPosition.observeAsState(mapOf())
    val friends = viewModel.friends

    GoogleMap(
        modifier = modifier.testTag("Google Map"),
        cameraPositionState = cameraPositionState,
        properties = mapProperties.value,
        uiSettings = uiSettings,
        contentPadding = WindowInsets.safeDrawing.asPaddingValues(),
        onMapLoaded = onMapLoaded,
    ) {
        friendsPosition.value.forEach { (id, position) ->
            val bitmap = remember { mutableStateOf<Bitmap?>(null) }
            val user = remember { friends.value.firstOrNull { it.id == id } }

            if (user != null) {
                LaunchedEffect(Unit) {
                    val request = ImageRequest.Builder(context)
                        .data(friends.value.first().profilePicture)
                        .target { drawable ->
                            bitmap.value = drawable.toBitmap().copy(Bitmap.Config.ARGB_8888, false)
                                .getRoundedCroppedBitmap()
                        }
                        .build()

                    imageLoader.enqueue(request)
                }

                if (bitmap.value != null) {
                    Marker(
                        position = LatLng(position.second, position.third),
                        title = user.displayName,
                        icon = BitmapDescriptorFactory.fromBitmap(bitmap.value!!),
                    )
                }
            }
        }
    }

    SafeArea {
        Column(modifier = Modifier.padding(MaterialTheme.spacing.small)) {
            MapTypeControls(onMapTypeClick = {
                mapProperties.value = mapProperties.value.copy(mapType = it)
            })
        }
    }
}

