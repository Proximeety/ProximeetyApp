package ch.proximeety.proximeety.presentation.views.settings

import androidx.lifecycle.ViewModel
import ch.proximeety.proximeety.presentation.navigation.NavigationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * The ViewModel for the Settings View.
 */
@HiltViewModel
class SettingsViewModel @Inject constructor(
    val navigationManager: NavigationManager
) : ViewModel()