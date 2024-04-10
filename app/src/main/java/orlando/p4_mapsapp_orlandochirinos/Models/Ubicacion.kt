package orlando.p4_mapsapp_orlandochirinos.Models
import com.google.android.gms.maps.model.LatLng

data class Ubicacion(
    var ubicationId: String? = null,
    val ubicationName: String,
    val snippet: String,
    val position: LatLng,
    val tag: String,
    val image: String? = null
){
    constructor():this(
        ubicationId = null,
        ubicationName = "",
        snippet = "",
        position = LatLng(41.4534265, 2.1837151),
        tag = "",
        image = null )
}
