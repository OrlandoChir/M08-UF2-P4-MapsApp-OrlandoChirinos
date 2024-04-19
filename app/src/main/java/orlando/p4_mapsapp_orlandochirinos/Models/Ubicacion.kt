package orlando.p4_mapsapp_orlandochirinos.Models

data class Ubicacion(
    var ubicationId: String? = null,
    val ubicationName: String,
   // val markerOwner : String,
    val snippet: String,
    var latitud: Double,
    var longitud: Double,
    val tag: String,
    val image: String? = null
){
    constructor():this(
        ubicationId = null,
        ubicationName = "",
       // markerOwner = "",
        snippet = "",
        latitud = 41.4534265,
        longitud = 2.1837151,
        tag = "",
        image = null )
}
