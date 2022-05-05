package ch.proximeety.proximeety.presentation.views.profile.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MoreHoriz
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ch.proximeety.proximeety.presentation.components.SimpleAlertDialog
import ch.proximeety.proximeety.presentation.views.profile.ProfileEvent
import ch.proximeety.proximeety.presentation.views.profile.ProfileViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun ButtonExtended(viewModel: ProfileViewModel, onDelete: () -> Unit){
    val menuExpanded = remember { mutableStateOf(false) }
    val showDialog = remember { mutableStateOf(false) }
    val options = arrayOf("Delete post", "More")

    IconButton( onClick = { menuExpanded.value = true }) {
        Icon(imageVector = Icons.Rounded.MoreHoriz, contentDescription = "More")
    }
    Column() {
        DropdownMenu(
            expanded = menuExpanded.value,
            onDismissRequest = {
                menuExpanded.value = false
            },
            modifier = Modifier.width(200.dp)
        ) {
            DropdownMenuItem(onClick = {
                menuExpanded.value = false
                showDialog.value = true
                viewModel.onEvent(ProfileEvent.OnOpenDialogClicked)
            }) {
                Text(text = options[0])
            }
            DropdownMenuItem(onClick = {  }) {
                Text(text = options[1])
            }
        }
    }
    if (showDialog.value) {
        val showDialogState = viewModel.showDialog.collectAsState()
        SimpleAlertDialog(
            show = showDialogState,
            onDismiss = { viewModel.onEvent(ProfileEvent.OnCloseDialog) },
            onConfirm = onDelete,
            title = "Delete post",
            question = "Do you want to continue?"
        )
    }
}
