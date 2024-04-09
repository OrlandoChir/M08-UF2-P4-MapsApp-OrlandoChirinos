package orlando.p4_mapsapp_orlandochirinos.Models

import com.google.firebase.firestore.CollectionReference
import orlando.p4_mapsapp_orlandochirinos.View.database

//SELECT
fun getUbications(): CollectionReference { return database.collection("ubications") }

//INSERT
fun addUbication(ubicacion: Ubicacion){
    database.collection("ubications")
        .add(
            hashMapOf(
            "ubicationName" to ubicacion.ubicationName,
            "snippet" to ubicacion.snippet,
            "position" to ubicacion.position,
            "tag" to ubicacion.tag,
            "image" to ubicacion.image )
        )
}

//UPDATE
fun editUbication(editedUbicacion: Ubicacion){
    database.collection("ubications").document(editedUbicacion.ubicationId!!).set(
        hashMapOf(
            "ubicationName" to editedUbicacion.ubicationName,
            "snippet" to editedUbicacion.snippet,
            "position" to editedUbicacion.position,
            "tag" to editedUbicacion.tag,
            "image" to editedUbicacion.image)
    )
}

//DELETE
fun deleteUbication(ubicationId: String){
    database.collection("ubications").document(ubicationId).delete()
}