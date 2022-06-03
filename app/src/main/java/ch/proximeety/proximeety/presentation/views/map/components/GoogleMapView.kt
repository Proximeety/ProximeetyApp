package ch.proximeety.proximeety.presentation.views.map.components

import android.graphics.Bitmap
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Sync
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.core.graphics.drawable.toBitmap
import ch.proximeety.proximeety.core.entities.User
import ch.proximeety.proximeety.presentation.theme.spacing
import ch.proximeety.proximeety.presentation.views.map.MapEvent
import ch.proximeety.proximeety.presentation.views.map.MapViewModel
import ch.proximeety.proximeety.util.SafeArea
import ch.proximeety.proximeety.util.extensions.getRoundedCroppedBitmap
import coil.compose.LocalImageLoader
import coil.request.ImageRequest
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import kotlin.random.Random

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
    val tags = viewModel.tags

    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(onClick = {
                val nextFriendId = friends.value[Random.nextInt(0, friends.value.size)].id
                val nextFriendPosition = friendsPosition.value[nextFriendId]
                if (nextFriendPosition != null) {
                    cameraPositionState.move(CameraUpdateFactory.newLatLng(LatLng(nextFriendPosition.second, nextFriendPosition.third)))
                }
            })
            {
                Icon(
                    imageVector = Icons.Default.Sync, //or ".AssistantPhoto"
                    contentDescription = "Jump to a random friend location"
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) {
        GoogleMap(
            modifier = modifier.testTag("Google Map"),
            cameraPositionState = cameraPositionState,
            properties = mapProperties.value,
            uiSettings = uiSettings,
            contentPadding = WindowInsets.safeDrawing.asPaddingValues(),
            onMapLoaded = onMapLoaded,
            /*onMapLongClick = {
                viewModel.onEvent(MapEvent.CreateNfcTag(it))
            }*/) {
                if (friends.value.isNotEmpty()) {
                    friendsPosition.value.forEach { (id, position) ->
                        val bitmap = remember { mutableStateOf<Bitmap?>(null) }
                        val user = remember { friends.value.firstOrNull { it.id == id } }

                        if (user != null) {
                            LaunchedEffect(Unit) {
                                val request = ImageRequest.Builder(context)
                                    .data(user.profilePicture)
                                    .target { drawable ->
                                        bitmap.value =
                                            drawable.toBitmap().copy(Bitmap.Config.ARGB_8888, false)
                                                .getRoundedCroppedBitmap()?.let { it1 ->
                                                    Bitmap.createScaledBitmap(
                                                        it1, 100, 100, false)
                                                }
                                    }
                                    .build()

                                imageLoader.enqueue(request)
                            }

                            if (bitmap.value != null) {
                                Marker(
                                    position = LatLng(position.second, position.third),
                                    title = user.displayName,
                                    icon = BitmapDescriptorFactory.fromBitmap(bitmap.value!!),
                                    onInfoWindowClick = { viewModel.onEvent(MapEvent.OnClickUserProfile(user.id)) }
                                )
                            }
                        }
                    }
                }

                if (tags.value.isNotEmpty()) {
                    tags.value.forEach {
                        Marker(
                            position = LatLng(it.latitude, it.longitude),
                            title = it.name,
                            onClick = { _ ->
                                viewModel.onEvent(MapEvent.OnClickNfcTag(it.id))
                                return@Marker false
                            }
                        )
                    }
                }
            }
    }

    SafeArea {
        Column(modifier = Modifier.padding(spacing.small)) {
            MapTypeControls(onMapTypeClick = {
                mapProperties.value = mapProperties.value.copy(mapType = it)
            })
        }
    }
}

