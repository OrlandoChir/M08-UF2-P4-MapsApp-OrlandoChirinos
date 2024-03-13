package orlando.p4_mapsapp_orlandochirinos.models
import com.google.android.gms.maps.model.LatLng

data class Ubicacion(
    val nombre: String,
    val snippet : String,
    val latLng: LatLng
)

val listOfUbicacion : MutableList<Ubicacion> = mutableListOf()
