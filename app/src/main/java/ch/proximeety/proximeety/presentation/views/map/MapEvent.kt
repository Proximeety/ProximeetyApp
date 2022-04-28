package ch.proximeety.proximeety.presentation.views.map

sealed class MapEvent {
    object MapLoaded : MapEvent()
    class OnClick(val id: String): MapEvent()
}
