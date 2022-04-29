package ch.proximeety.proximeety.presentation.views.settings

import android.media.audiofx.Equalizer
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Image
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Alignment.Companion.Start
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ch.proximeety.proximeety.presentation.views.profile.ProfileEvent
import ch.proximeety.proximeety.presentation.views.upload.UploadEvent
import ch.proximeety.proximeety.util.SafeArea

val EMPTY_PROFILE_PIC: String = ""
val EMPTY_BIO: String = ""

/**
 * The View for the Settings.
 */
@Preview
@Composable
fun SettingsView(
    viewModel: SettingsViewModel = hiltViewModel()
) {

    val context = LocalContext.current

    SafeArea {
        Scaffold {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(bottom = 15.dp)
            ) {
                SetProfilePicture(viewModel,"Set A New Profile Picture")
                SetUserBio(viewModel, "Set A New Bio")
            }
        }
    }


}

@Composable
fun SetUserBio(viewModel: SettingsViewModel, setting: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .width(75.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = setting, fontSize = 20.sp,
            modifier = Modifier
                .align(CenterVertically)
                .padding(start = 15.dp)
        )

        var bio by remember { mutableStateOf(TextFieldValue("")) }
        OutlinedTextField(
            value = bio,

            modifier = Modifier.align(CenterVertically).padding(end = 10.dp).size(width = 220.dp, height = 70.dp),
            label = { Text(text = "Write The Bio Here") },
            onValueChange = {
                bio = it
                viewModel.onEvent(SettingsEvent.setBio(it.text))
                viewModel.onEvent(SettingsEvent.ChangeBio)
            }
        )
    }
}

@Composable
fun SetProfilePicture(viewModel: SettingsViewModel, setting: String) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            uri?.also { viewModel.onEvent(SettingsEvent.setProfilePicLink(it.toString()))
                viewModel.onEvent(SettingsEvent.ChangeProfilePic)}
        }
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .width(75.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = setting, fontSize = 20.sp,
            modifier = Modifier
                .align(CenterVertically)
                .padding(start = 15.dp)
        )
        IconButton(onClick = {launcher.launch("image/*")}) {
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = setting,
                modifier = Modifier
                    .align(CenterVertically)
            )
        }
    }
}


@Preview
@Composable
fun SingleSettingDropdown(/*TODO link setting*/) {
    var expanded by remember { mutableStateOf(false) }
    val items = listOf("A", "B", "C", "D")
    var selectedIndex by remember { mutableStateOf(0) }
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Test with Dropdown Menu", fontSize = 20.sp,
            modifier = Modifier
                .padding(start = 15.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 20.dp, end = 5.dp)
                .wrapContentSize(Alignment.TopStart)
        ) {
            Text(
                items[selectedIndex],
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = { expanded = true })
                    .background(Color.White)
                    .border(color = Color.Black, width = 2.dp, shape = RoundedCornerShape(5.dp))
                    .padding(all = 5.dp)
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .background(Color.LightGray)
            ) {
                items.forEachIndexed { index, s ->
                    DropdownMenuItem(onClick = {
                        selectedIndex = index
                        expanded = false
                    }) { Text(text = s) }
                }
            }
        }
    }
}

@Preview
@Composable
fun SingleSettingSwitch(/*TODO link setting*/) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .width(75.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Test with switch", fontSize = 20.sp,
            modifier = Modifier
                .align(CenterVertically)
                .padding(start = 15.dp)
        )
        val checkState = remember { mutableStateOf(true) }
        Switch(
            checked = checkState.value,
            onCheckedChange = { checkState.value = it },
            colors = SwitchDefaults.colors(Color.Black),
            modifier = Modifier
                .align(CenterVertically)
                .padding(end = 15.dp)
        )
    }
}


@Preview
@Composable
fun SingleSettingSlider(/*TODO link setting*/) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .width(75.dp),
    ) {
        Text(
            text = "Test with slider", fontSize = 20.sp,
            modifier = Modifier.padding(start = 15.dp)
        )
        var sliderPosition by remember { mutableStateOf(0f) }
        Slider(
            steps = 10, valueRange = 0f..100f,
            modifier = Modifier.padding(end = 25.dp, start = 25.dp),
            value = sliderPosition,
            onValueChange = { sliderPosition = it },
            colors = SliderDefaults.colors(Color.Black, activeTrackColor = Color.Black)
        )
    }
}