package orlando.p4_mapsapp_orlandochirinos.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import orlando.p4_mapsapp_orlandochirinos.models.Ubicacion

class MapViewmodel : ViewModel() {

    var UBICACIONES : MutableList<Ubicacion> by mutableStateOf(
        mutableListOf(
            Ubicacion(  nombre = "ITB",
                        snippet = "MARKER AT ITB",
                        position = LatLng(41.4534265, 2.1837151) ),

            Ubicacion(  nombre = "somewhere",
                        snippet = "random",
                        position = LatLng(41.4534265, 2.1835151) ) )
    )
        private set
    fun addLocation( newUbication : Ubicacion ){ UBICACIONES.add(newUbication) }

}
