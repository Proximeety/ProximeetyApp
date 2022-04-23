package ch.proximeety.proximeety.presentation.views.mapView

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.maps.android.compose.*

private const val TAG = "MapViewActivity"

val userCurrentLocation = LatLng(46.5, 6.6) // TODO: change later to fetch user location

@Composable
fun MapView(
    viewModel: MapViewModel = hiltViewModel()
) {

    var isMapLoaded by remember { mutableStateOf(false) }
    // Observing and controlling the camera's state can be done with a CameraPositionState
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(userCurrentLocation, 11f)
    }

    Box(Modifier.fillMaxSize()) {
        GoogleMapView(
            modifier = Modifier.matchParentSize(),
            cameraPositionState = cameraPositionState,
            onMapLoaded = {
                isMapLoaded = true
            },
        )
        if (!isMapLoaded) {
            AnimatedVisibility(
                modifier = Modifier
                    .matchParentSize(),
                visible = !isMapLoaded,
                enter = EnterTransition.None,
                exit = fadeOut()
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .background(MaterialTheme.colors.background)
                        .wrapContentSize()
                )
            }
        }
    }
}

@Composable
fun GoogleMapView(
    modifier: Modifier,
    cameraPositionState: CameraPositionState,
    onMapLoaded: () -> Unit,
    viewModel: MapViewModel = hiltViewModel()
) {
    var uiSettings by remember { mutableStateOf(MapUiSettings(myLocationButtonEnabled = false, zoomControlsEnabled = false)) }
    var mapProperties by remember {
        mutableStateOf(MapProperties(isMyLocationEnabled = true))
    }
    val friends = viewModel.friends.observeAsState(listOf())
    var friendIndex = 0

    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(onClick = {
                cameraPositionState.move(CameraUpdateFactory.newLatLng(userCurrentLocation)) }) {
                Icon(
                    imageVector = Icons.Default.Sync, //or ".AssistantPhoto"
                    contentDescription = "Sync with Current User Location"
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) {
        GoogleMap(
            modifier = modifier,
            cameraPositionState = cameraPositionState,
            properties = mapProperties,
            uiSettings = uiSettings,
            onMapLoaded = onMapLoaded,
        ) {
            // Drawing on the map is accomplished with a child-based API
            val markerClick: (Marker) -> Boolean = {
                Log.d(TAG, "${it.title} was clicked")
                cameraPositionState.projection?.let { projection ->
                    Log.d(TAG, "The current projection is: $projection")
                }
                false
            }

            friends.value.forEach {
                Marker(
                    position = LatLng(it.Lat, it.Lng),
                    title = it.moments,
                    onClick = markerClick
                )
            }

            Marker(
                position = userCurrentLocation,
                title = "My Location",
                onClick = markerClick
            )

            Circle(
                center = userCurrentLocation,
                fillColor = MaterialTheme.colors.secondary,
                strokeColor = MaterialTheme.colors.secondaryVariant,
                radius = 1000.0,
            )
        }

//        Column {
//            MapTypeControls(onMapTypeClick = {
//                Log.d("GoogleMap", "Selected map type $it")
//                mapProperties = mapProperties.copy(mapType = it)
//            })
//            val coroutineScope = rememberCoroutineScope()
//        }
        
        Button(onClick = {
            val nextFriend = friends.value.get(friendIndex)
            cameraPositionState.move(CameraUpdateFactory.newLatLng(LatLng(nextFriend.Lat, nextFriend.Lng)))
            if (friendIndex < friends.value.lastIndex) {
                friendIndex++
            } else {
                friendIndex = 0
            }
        }) {
            Text(text = "Find a friend!")
        }
    }
}

@Composable
private fun MapTypeControls(
    onMapTypeClick: (MapType) -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .horizontalScroll(state = ScrollState(0)),
        horizontalArrangement = Arrangement.Center
    ) {
        MapType.values().forEach {
            MapTypeButton(type = it) { onMapTypeClick(it) }
        }
    }
}

@Composable
private fun MapTypeButton(type: MapType, onClick: () -> Unit) {
    Button(
        modifier = Modifier.padding(4.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.onPrimary,
            contentColor = MaterialTheme.colors.primary
        ),
        onClick = onClick
    ) {
        Text(text = type.toString(), style = MaterialTheme.typography.body1)
    }
}

@Composable
private fun ZoomControls(
    isCameraAnimationChecked: Boolean,
    isZoomControlsEnabledChecked: Boolean,
    onZoomOut: () -> Unit,
    onZoomIn: () -> Unit,
    onCameraAnimationCheckedChange: (Boolean) -> Unit,
    onZoomControlsCheckedChange: (Boolean) -> Unit,
) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        MapButton("-", onClick = { onZoomOut() })
        MapButton("+", onClick = { onZoomIn() })
        Column(verticalArrangement = Arrangement.Center) {
            Text(text = "Camera Animations On?")
            Switch(
                isCameraAnimationChecked,
                onCheckedChange = onCameraAnimationCheckedChange,
                modifier = Modifier.testTag("cameraAnimations"),
            )
            Text(text = "Zoom Controls On?")
            Switch(
                isZoomControlsEnabledChecked,
                onCheckedChange = onZoomControlsCheckedChange
            )
        }
    }
}

@Composable
private fun MapButton(text: String, onClick: () -> Unit) {
    Button(
        modifier = Modifier.padding(8.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.onPrimary,
            contentColor = MaterialTheme.colors.primary
        ),
        onClick = onClick
    ) {
        Text(text = text, style = MaterialTheme.typography.h5)
    }
}

/*
@Composable
private fun DebugView(cameraPositionState: CameraPositionState) {
    Column(
        Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center
    ) {
        val moving =
            if (cameraPositionState.isMoving) "moving" else "not moving"
        Text(text = "Camera is $moving")
        Text(text = "Camera position is ${cameraPositionState.position}")
    }
}*/
