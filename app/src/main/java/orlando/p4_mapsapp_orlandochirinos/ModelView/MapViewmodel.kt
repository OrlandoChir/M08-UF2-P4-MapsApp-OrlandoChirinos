package orlando.p4_mapsapp_orlandochirinos.ModelView

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import orlando.p4_mapsapp_orlandochirinos.Models.Ubicacion

class MapViewmodel : ViewModel() {

    val ubicationTypes : List<String> = listOf("Favoritos","Restaurantes","Casas")

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
    fun addLocation( newUbication : Ubicacion ){ this.UBICACIONES.add(newUbication) }

    var bottomSheet : Boolean by  mutableStateOf(false)
        private set
    fun showBottomSheet(){ this.bottomSheet = !this.bottomSheet }


}
