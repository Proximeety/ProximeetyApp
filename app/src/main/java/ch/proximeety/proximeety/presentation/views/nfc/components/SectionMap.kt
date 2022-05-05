package ch.proximeety.proximeety.presentation.views.nfc.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import ch.proximeety.proximeety.core.entities.Tag
import ch.proximeety.proximeety.presentation.theme.spacing
import com.google.accompanist.placeholder.material.placeholder
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMapOptions
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

@Composable
fun SectionMap(tag: State<Tag?>, width: Float) {
    val camera = CameraPositionState()
    val mapReady = remember { mutableStateOf(false) }

    LaunchedEffect(tag.value, mapReady.value) {
        if (tag.value != null && mapReady.value) {
            camera.move(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(
                        tag.value?.latitude ?: 0.0,
                        tag.value?.longitude ?: 0.0
                    ),
                    15f
                )
            )
        }
    }

    GoogleMap(
        cameraPositionState = camera,
        googleMapOptionsFactory = {
            GoogleMapOptions().mapType(GoogleMap.MAP_TYPE_NORMAL)
                .compassEnabled(false)
                .zoomControlsEnabled(false)
                .zoomGesturesEnabled(false)
                .mapToolbarEnabled(false)
                .tiltGesturesEnabled(false)
                .rotateGesturesEnabled(false)
                .scrollGesturesEnabled(false)
        },
        onMapLoaded = {
            mapReady.value = true
        },
        properties = MapProperties(),
        uiSettings = MapUiSettings(
            mapToolbarEnabled = false,
            myLocationButtonEnabled = false,
            compassEnabled = false,
            zoomControlsEnabled = false,
            zoomGesturesEnabled = false,
            tiltGesturesEnabled = false
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(width.dp * 2f / 3f - MaterialTheme.spacing.large * 1f / 3f)
            .clip(MaterialTheme.shapes.large)
            .placeholder(tag.value == null)
    ) {
        if (tag.value != null) {
            Marker(
                position = LatLng(
                    tag.value?.latitude ?: 0.0,
                    tag.value?.longitude ?: 0.0
                )
            )
        }
    }
}