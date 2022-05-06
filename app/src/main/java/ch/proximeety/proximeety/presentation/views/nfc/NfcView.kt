package ch.proximeety.proximeety.presentation.views.nfc

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ch.proximeety.proximeety.presentation.theme.spacing
import ch.proximeety.proximeety.presentation.views.nfc.components.SectionHeader
import ch.proximeety.proximeety.presentation.views.nfc.components.SectionMap
import ch.proximeety.proximeety.presentation.views.nfc.components.SectionPictures
import ch.proximeety.proximeety.presentation.views.nfc.components.SectionVisitors
import ch.proximeety.proximeety.util.SafeArea

@Composable
fun NfcView(viewModel: NfcViewModel = hiltViewModel()) {

    val tag = viewModel.nfcTag
    val isNewTag = viewModel.isNewTag

    SafeArea(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        if (isNewTag.value == null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (isNewTag.value == true) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "New tag found", style = MaterialTheme.typography.h2)
                Text(text = "Do you want to create a new tag?", style = MaterialTheme.typography.h3)
                Row(horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium)) {
                    Button(onClick = { viewModel.onEvent(NfcEvent.GoBack) }) {
                        Text(text = "No")
                    }
                    Button(onClick = { viewModel.onEvent(NfcEvent.CreateNewTag) }) {
                        Text(text = "Yes")
                    }
                }
            }
        } else {
            BoxWithConstraints(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = MaterialTheme.spacing.medium)
            ) {
                val width = this.maxWidth.value
                val height = this.maxHeight.value
                Column(
                    Modifier.verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.large)
                ) {
                    SectionHeader(tag.value, viewModel = viewModel, modifier = Modifier.width(width.dp))
                    SectionPictures(tag = tag.value, width = width, height = height)
                    SectionMap(tag = tag, width = width)
                    SectionVisitors(tag = tag.value, viewModel = viewModel)
                    Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
                }
            }
        }
    }
}