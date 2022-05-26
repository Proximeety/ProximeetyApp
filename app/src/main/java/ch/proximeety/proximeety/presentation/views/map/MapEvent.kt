package ch.proximeety.proximeety.presentation.views.map

import ch.proximeety.proximeety.core.entities.Tag
import com.google.android.gms.maps.model.LatLng

sealed class MapEvent {
    object MapLoaded : MapEvent()
    //class CreateNfcTag(val latlng: LatLng): MapEvent()
    class OnClickNfcTag(val nfcId: String): MapEvent()
}
