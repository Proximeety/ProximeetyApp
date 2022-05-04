package ch.proximeety.proximeety.presentation.views.nfc

sealed class NfcEvent {
    class NavigateToUserProfile(val userId: String) : NfcEvent()
}