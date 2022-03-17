package ch.proximeety.proximeety.presentation.views.settings

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

/**
 * The View for the Settings.
 */
@Preview
@Composable
fun SettingsView(
    viewModel: SettingsViewModel = hiltViewModel()
) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .padding(bottom = 15.dp)) {
        TopBar();
        SingleSettingSwitch()
        SingleSettingSlider()
        //SingleSettingDropdown()
    }
}

/* @Preview TODO FINISH IMPLEMENTING DROPDOWN
@Composable
fun SingleSettingDropdown(/*TODO setting: String*/) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .width(75.dp),
        horizontalArrangement = Arrangement.SpaceBetween) {
        Text(text = "Test with Dropdown Menu", fontSize = 20.sp,
            modifier = Modifier
                .align(CenterVertically)
                .padding(start = 15.dp))

        DropdownMenu(expanded = false,
            onDismissRequest = { },
            content = ,
            modifier = Modifier
                .align(CenterVertically)
                .padding(end = 15.dp))
    }
} */

@Preview
@Composable
fun SingleSettingSwitch(/*TODO setting: String*/) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .width(75.dp),
        horizontalArrangement = Arrangement.SpaceBetween) {
        Text(text = "Test with switch", fontSize = 20.sp,
            modifier = Modifier
                .align(CenterVertically)
                .padding(start = 15.dp))
        val checkState = remember {mutableStateOf(true)}
        Switch(checked = checkState.value,
        onCheckedChange = { checkState.value = it},
        colors = SwitchDefaults.colors(Color.Black),
        modifier = Modifier
            .align(CenterVertically)
            .padding(end = 15.dp))
    }
}


@Preview
@Composable
fun SingleSettingSlider(/*TODO setting: String*/) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .width(75.dp),) {
        Text(text = "Test with slider", fontSize = 20.sp,
            modifier = Modifier.padding(start = 15.dp))
        var sliderPosition by remember {mutableStateOf(0f)}
        Slider(steps = 10, valueRange = 0f..100f,
            modifier = Modifier.padding(end = 25.dp, start = 25.dp),
            value = sliderPosition,
            onValueChange = { sliderPosition = it},
            colors = SliderDefaults.colors(Color.Black, activeTrackColor = Color.Black))
    }
}

@Preview
@Composable
fun TopBar() {
    /* TODO CHANGE TO TOPAPPBAR SHAPE*/
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .border(
                width = 1.dp,
                color = Color.Black
            )
            .fillMaxWidth()
            .height(50.dp)) {
        TextButton(
            modifier = Modifier
                .background(Color.White)
                .fillMaxHeight(),
            onClick = {/* TODO */ }) {
            Text(
                text = "Back",
                color = Color.Black)}
        Text(
            text = "Settings",
            color = Color.Black,
            fontSize = 30.sp,
            modifier = Modifier
                .align(CenterVertically))
        Box(Modifier.padding(end = 70.dp)) {}
    }
}