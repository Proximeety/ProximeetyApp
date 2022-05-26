package ch.proximeety.proximeety.presentation.views.nfc.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import ch.proximeety.proximeety.core.entities.Tag
import ch.proximeety.proximeety.presentation.views.nfc.NfcEvent
import ch.proximeety.proximeety.presentation.views.nfc.NfcViewModel
import ch.proximeety.proximeety.util.modifiers.clearFocusOnKeyboardDismiss

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SectionHeader(tag: Tag?, viewModel: NfcViewModel, modifier: Modifier = Modifier) {

    val canEdit = viewModel.canEdit.value

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    Column {
        if (canEdit) {
            BasicTextField(
                value = tag?.name ?: "",
                onValueChange = { viewModel.onEvent(NfcEvent.OnNameChange(it)) },
                textStyle = MaterialTheme.typography.h1,
                keyboardOptions = KeyboardOptions.Default.copy(
                    capitalization = KeyboardCapitalization.Sentences,
                    autoCorrect = true,
                    imeAction = ImeAction.Done,
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                        focusManager.clearFocus()
                    }
                ),
                modifier = Modifier.clearFocusOnKeyboardDismiss()
            )
        } else {
            Text(text = tag?.name ?: "", style = MaterialTheme.typography.h1)
        }
        Text(
            "By ${tag?.owner?.displayName}",
            style = MaterialTheme.typography.h1,
            fontWeight = FontWeight.Light,
            color = Color.Gray
        )
    }
}