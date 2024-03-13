package orlando.p4_mapsapp_orlandochirinos.viewmodels

import com.google.android.gms.maps.model.LatLng
import orlando.p4_mapsapp_orlandochirinos.models.Ubicacion

val UBICACIONES : List<Ubicacion> = listOf(
    Ubicacion(  nombre = "ITB",
                snippet = "MARKER AT ITB",
                latLng = LatLng(41.4534265, 2.1837151) ),

    Ubicacion(  nombre = "somewhere",
                snippet = "random",
                latLng = LatLng(41.4534265, 2.1835151) )

    )

fun CreateNewMarker(){


}