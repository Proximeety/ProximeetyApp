package ch.proximeety.proximeety.presentation.views.nfc

sealed class NfcEvent {
    object GoBack : NfcEvent()
    object CreateNewTag : NfcEvent()
    class OnNameChange(val name : String) : NfcEvent()
    object MapLoaded : NfcEvent()
    class NavigateToUserProfile(val userId: String) : NfcEvent()
}