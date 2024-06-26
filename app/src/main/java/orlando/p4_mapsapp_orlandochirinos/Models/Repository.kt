package orlando.p4_mapsapp_orlandochirinos.Models

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

class Repository {
    val database = FirebaseFirestore.getInstance() //Almacenamiento y marcadores

    ////////////////////////////  ALMACENAMIENTO Y MARCADORES  ////////////////////////////////////
    //SELECT
    fun getAllUbications(): CollectionReference {
        return database.collection("ubications") }

    fun getUbication(ubicationId: String): DocumentReference {
        return database.collection("ubications").document(ubicationId) }

    //INSERT
    fun addUbication(ubicacion: Ubicacion){
        database.collection("ubications")
            .add(
                hashMapOf(
                    "ubicationName" to ubicacion.ubicationName,
                    "ubicationOwner" to ubicacion.markerOwner,
                    "snippet" to ubicacion.snippet,
                    "latitud" to ubicacion.latitud,
                    "longitud" to ubicacion.longitud,
                    "tag" to ubicacion.tag,
                    "image" to ubicacion.image )
            )
    }

    //UPDATE
    fun editUbication(editedUbicacion: Ubicacion){
        database.collection("ubications").document(editedUbicacion.ubicationId!!)
            .set(
            hashMapOf(
                "ubicationName" to editedUbicacion.ubicationName,
                "snippet" to editedUbicacion.snippet,
                "latitud" to editedUbicacion.latitud,
                "longitud" to editedUbicacion.longitud,
                "tag" to editedUbicacion.tag,
                "image" to editedUbicacion.image)
        )
    }

    //DELETE
    fun deleteUbication(ubicationId: String){
        database.collection("ubications").document(ubicationId).delete()
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////
}