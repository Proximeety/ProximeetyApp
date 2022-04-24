package ch.proximeety.proximeety.presentation.views.map.components

import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.google.maps.android.compose.MapType

@Composable
fun MapTypeButton(type: MapType, onClick: () -> Unit) {
    Button(
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.White,
            contentColor = Color.Gray
        ),
        onClick = onClick
    ) {
        Text(text = type.toString(), style = MaterialTheme.typography.body1)
    }
}
