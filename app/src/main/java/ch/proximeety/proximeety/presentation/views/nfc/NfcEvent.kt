package ch.proximeety.proximeety.presentation.views.nfc

sealed class NfcEvent {
    object MapLoaded : NfcEvent()
    class NavigateToUserProfile(val userId: String) : NfcEvent()
}