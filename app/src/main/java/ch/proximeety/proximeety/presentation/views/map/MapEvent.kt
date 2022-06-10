package ch.proximeety.proximeety.presentation.views.map

import ch.proximeety.proximeety.core.entities.Tag
import com.google.android.gms.maps.model.LatLng

sealed class MapEvent {
    object MapLoaded : MapEvent()
    class OnClickUserProfile(val id: String): MapEvent()
    class OnClickNfcTag(val nfcId: String): MapEvent()
}
