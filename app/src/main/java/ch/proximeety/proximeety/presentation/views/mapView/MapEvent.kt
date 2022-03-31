package ch.proximeety.proximeety.presentation.views.mapView

sealed class MapEvent {
    class MapClick(val friends: MapModel) : MapEvent()
}
