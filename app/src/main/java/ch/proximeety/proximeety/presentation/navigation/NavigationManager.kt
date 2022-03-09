package ch.proximeety.proximeety.presentation.navigation

import kotlinx.coroutines.flow.MutableStateFlow

class NavigationManager {

    var command = MutableStateFlow<NavigationCommand?>(null)

    fun navigate(command: NavigationCommand) {
        this.command.value = command
    }
}