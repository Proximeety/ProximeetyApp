package ch.proximeety.proximeety.presentation.views.map.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.maps.android.compose.MapType

@Composable
fun MapTypeControls(
    onMapTypeClick: (MapType) -> Unit
) {
    Column(
        Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Top
    ) {
        MapType.values().filterNot { it.ordinal == 0 }.forEach {
            MapTypeButton(type = it) { onMapTypeClick(it) }
        }
    }
}
