package ch.proximeety.proximeety.presentation.views.mapView

sealed class MapEvent {
    class CenterAtUserLocation(val friends: MapModel) : MapEvent()
}
