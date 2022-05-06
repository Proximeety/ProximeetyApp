package ch.proximeety.proximeety.presentation.views.nfc

sealed class NfcEvent {
    object GoBack : NfcEvent()
    object CreateNewTag : NfcEvent()
    class NavigateToUserProfile(val userId: String) : NfcEvent()
}