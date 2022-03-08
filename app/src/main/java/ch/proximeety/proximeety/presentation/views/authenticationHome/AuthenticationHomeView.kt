package ch.proximeety.proximeety.presentation.views.authenticationHome

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ch.proximeety.proximeety.presentation.navigation.graphs.MainView
import ch.proximeety.proximeety.util.extensions.getActivity
import kotlinx.coroutines.flow.collectLatest

/**
 * The Authentication Home View.
 */
@Composable
fun AuthenticationHomeView(
    navController: NavController,
    viewModel: AuthenticationHomeViewModel = hiltViewModel()
) {

    val context = LocalContext.current

    LaunchedEffect(true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                AuthenticationHomeUIEvent.NavigateToMainNavigation -> {
                    navController.navigate(MainView.HomeView.route)
                }
            }
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Button(onClick = {
            context.getActivity()
                ?.let { viewModel.onEvent(AuthenticationHomeEvent.AuthenticateWithGoogle(it)) }
        }) {
            Text(text = "Login")
        }
    }
}
