package orlando.p4_mapsapp_orlandochirinos.Models
import com.google.android.gms.maps.model.LatLng

data class Ubicacion(
    val nombre: String,
    val snippet : String,
    val position: LatLng,
    val type : String
)



