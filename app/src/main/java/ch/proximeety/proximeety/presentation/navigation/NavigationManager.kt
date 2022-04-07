package ch.proximeety.proximeety.presentation.navigation

import kotlinx.coroutines.flow.MutableStateFlow

/**
 * Navigation Manager.
 *
 * A single instance of the class is shared through the application to navigate the different screen.
 */
class NavigationManager {

    var command = MutableStateFlow<NavigationCommand?>(null)

    /**
     * Navigate to the specified screen.
     *
     * @param command the command used for navigation.
     */
    fun navigate(command: NavigationCommand) {
        this.command.value = command
    }

    /**
     * Navigate to the previous screen.
     */
    fun goBack() {
        this.command.value = NavigationCommand.GoBack
    }

    /**
     * Clear the last command.
     *
     * Must be called after the navigation is completed by the navigation controller.
     */
    fun clear() {
        this.command.value = null
    }
}