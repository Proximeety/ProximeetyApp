package ch.proximeety.proximeety.presentation.views.mapView

/**
 * The Model for Map Users View
 */
data class MapModel(
    val time: String,
    val moments: String,
    val Lat: Double,
    val Lng: Double
)

var friend0 = MapModel("2000-01-01", "Lausanne", 46.55, 6.63)
var friend1 = MapModel("2021-01-01", "I just got home!", 50.5, 10.7)

val friend2 = MapModel(
    time = "2022-03-24",
    moments = "Bonne nuit",
    Lat = 35.0,
    Lng = 22.0
)