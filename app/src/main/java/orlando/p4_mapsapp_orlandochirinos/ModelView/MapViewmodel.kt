package orlando.p4_mapsapp_orlandochirinos.ModelView

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import orlando.p4_mapsapp_orlandochirinos.Models.Repository
import orlando.p4_mapsapp_orlandochirinos.Models.Ubicacion
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MapViewmodel : ViewModel() {

    //private val database = FirebaseFirestore.getInstance()

    var repository : Repository = Repository()

    val defaultBitmap: Bitmap =
        Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)

    val tagList = listOf<String>("Favoritos", "Restaurantes", "Parques", "Casas")

    val screenList = listOf<String>("login","map","markerlist","markerdetail","camera")
    var tagSelected by mutableStateOf("")
        private set
    fun selectTag(tagValue: String) { this.tagSelected = tagValue }
    var imageBitmap by mutableStateOf( this.defaultBitmap )
        private set

    fun storeImageBitmap(bitmap : Bitmap) { this.imageBitmap =  bitmap  }

    var imageUri by mutableStateOf<String?>( null )
        private set

    fun storeImageUri(intImageUri : Uri) { this.imageUri = intImageUri.toString()  }


    var currentScreen by mutableStateOf("")
        private set
    fun screenSelect(screenValue: String) { this.currentScreen = screenValue }
    var availableLocations : MutableList<Ubicacion> by mutableStateOf(
        mutableListOf(
            Ubicacion(
                        ubicationId = "ITB",
                        ubicationName = "ITB",
                        snippet = "MARKER AT ITB",
                        position = LatLng(41.4534265, 2.1837151),
                        tag = "Favoritos",
                        image = this.imageUri )
        )
    )
        private set
    fun addLocation( newUbication : Ubicacion ){ this.availableLocations.add(newUbication) }
    fun getAllLocations():List<Ubicacion> { return this.availableLocations }

    var positionToSee: LatLng = availableLocations[0].position
        private set
    fun changePosition(newPosition : LatLng){ this.positionToSee = newPosition }

    var bottomSheet : Boolean by  mutableStateOf(false)
        private set

    fun showBottomSheet(){ this.bottomSheet = !this.bottomSheet }

    private val _selectedPosition = MutableStateFlow<LatLng?>(null)
    val selectedPosition: StateFlow<LatLng?> = _selectedPosition
    fun onMapLongClick(coordinates: LatLng) { _selectedPosition.value = coordinates }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    private val _actualUbication = MutableStateFlow<Ubicacion?>(null)
    val actualUbication: StateFlow<Ubicacion?> = _actualUbication

    private val _ubicationName = MutableStateFlow("")
    val ubicationName: StateFlow<String> = _ubicationName

    private val _snippet = MutableStateFlow("")
    val snippet: StateFlow<String> = _snippet

    private val _position = MutableStateFlow(LatLng(0.0, 0.0))
    val position: StateFlow<LatLng> = _position

    private val _tag = MutableStateFlow("")
    val tag: StateFlow<String> = _tag

    private val _image = MutableStateFlow<String?>(null)
    val image: StateFlow<String?> = _image

    fun getAllUbications(){
        repository.getAllUbications().addSnapshotListener{ value, error ->
            if (error != null){
                Log.e("Firestore error",error.message.toString() )
                return@addSnapshotListener
            }
            val tempList = mutableListOf<Ubicacion>()
            for (docChange: DocumentChange in value?.documentChanges!!){
                if (docChange.type == DocumentChange.Type.ADDED){
                    val newUbication = docChange.document.toObject(Ubicacion::class.java)
                    newUbication.ubicationId = docChange.document.id
                    tempList.add(newUbication)
                }
            }
            availableLocations = tempList
        }
    }

    fun getUbication(intUbicationId: String){
        repository.getUbication(intUbicationId).addSnapshotListener{ value, error ->
            if (error != null){
                Log.w("UbicationRepository","Listen failed",error)
                return@addSnapshotListener
            }
            if ( value != null && value.exists() ){
                val ubication = value.toObject(Ubicacion::class.java)
                if (ubication != null) {
                    ubication.ubicationId = intUbicationId
                }
                _actualUbication.value = ubication
                _ubicationName.value = _actualUbication.value!!.ubicationName
                _snippet.value = _actualUbication.value!!.ubicationName
                _position.value = _actualUbication.value!!.position
                _tag.value = _actualUbication.value!!.position.toString()
                _image.value = _actualUbication.value!!.position.toString()
            }
            else {
                Log.d("UbicationRepository","Current data: null")
            }
        }
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////
    var imageUriFirebase by mutableStateOf<Uri?>( null )
        private set

    fun setImageUriF(intImageUri : Uri) { this.imageUriFirebase = intImageUri }

    fun uploadImage( imageUri : Uri ){
        val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault() )
        val now = Date()
        val fileName = formatter.format(now)
        val storage = FirebaseStorage.getInstance().getReference("images/$fileName")
        storage.putFile(imageUri)
            .addOnSuccessListener {
                Log.i("IMAGE UPLOAD", "Image uplodaded successfully")
                storage.downloadUrl.addOnSuccessListener { uri ->
                    Log.i("IMAGEN" , uri.toString() )

                    //Hacer cositas to wapas, nene

                }
            }
            .addOnFailureListener{
                Log.i("IMAGE UPLOAD", "Image upload failed")
            }
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////
}
    /* var closeNav : Boolean by mutableStateOf(true)
       fun closeNavigationMenu(){ this.closeNav = !this.closeNav }*/


