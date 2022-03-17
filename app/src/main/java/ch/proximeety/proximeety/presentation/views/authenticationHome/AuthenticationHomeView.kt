package ch.proximeety.proximeety.presentation.views.authenticationHome

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.collectLatest

/**
 * The Authentication Home View.
 */
@Composable
fun AuthenticationHomeView(
    viewModel: AuthenticationHomeViewModel = hiltViewModel()
) {

    LaunchedEffect(true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
            }
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Button(onClick = { viewModel.onEvent(AuthenticationHomeEvent.AuthenticateWithGoogle) }) {
            Text(text = "Login")
        }
    }
}
