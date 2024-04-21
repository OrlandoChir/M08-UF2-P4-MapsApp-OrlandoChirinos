package orlando.p4_mapsapp_orlandochirinos.ModelView

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
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
    private val auth = FirebaseAuth.getInstance() //Autenticacion

    var repository : Repository = Repository()

    var nameOfPlace by mutableStateOf("")

    var description by mutableStateOf("")

    val defaultBitmap: Bitmap =
        Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)

    val tagList = listOf<String>("Favoritos", "Restaurantes", "Parques", "Casas")

    val screenList = listOf<String>("login","map","markerlist","markerdetail","camera")

    var tagSelected by mutableStateOf("")
    fun clearTag(){ tagSelected = "" }
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
    var availableLocations : MutableList<Ubicacion> by mutableStateOf( mutableListOf() )
        private set

    private val _firestoreAvailableLocations = MutableLiveData<List<Ubicacion>>()
    val firestoreAvailableLocations: LiveData<List<Ubicacion>> = _firestoreAvailableLocations

    fun updateAvailableLocations(newLocations: List<Ubicacion>) {
        _firestoreAvailableLocations.value = newLocations
    }

    fun clearModal(){
        this.nameOfPlace = ""
        this.description = ""
        this.imageUriFirebase = null
    }

    /*    var availableLocations2 : MutableList<Ubicacion> by mutableStateOf( mutableListOf(
            Ubicacion(
                ubicationId = "null",
                ubicationName = "ITB",
                snippet = "dadad",
                latitud = 41.4534265,
                longitud = 2.1837151,
                tag = "Favoritos",
                image = "null" )
            )
        )
            private set*/

/*    fun addLocation( newUbication : Ubicacion ){ this.firestoreAvailableLocations.add(newUbication) }
    fun getAllLocations(): LiveData<List<Ubicacion>> { return this.firestoreAvailableLocations }*/

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

    private val _latitud = MutableStateFlow(0.0)
    val latitud: StateFlow<Double> = _latitud

    private val _longitud = MutableStateFlow(0.0)
    val longitud: StateFlow<Double> = _longitud

    private val _tag = MutableStateFlow("")
    val tag: StateFlow<String> = _tag

    private val _image = MutableStateFlow<String?>(null)
    val image: StateFlow<String?> = _image

    var positionToSee: LatLng =
        if (availableLocations.isNotEmpty()) {
            LatLng(availableLocations[0].latitud, availableLocations[0].longitud) }

        else { LatLng(41.4534265, 2.1837151) }

        private set
    fun changePosition(newPosition : LatLng){ this.positionToSee = newPosition }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    //SQL
    //SELECT ALL
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
            _firestoreAvailableLocations.postValue(tempList)
        }
    }

    //SELECT WHERE
    fun getUbicationsFiltered(tagValue:String){
        //FILTRO
         repository.getAllUbications().whereEqualTo("tag",tagValue).addSnapshotListener{ value, error ->
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
            _firestoreAvailableLocations.postValue(tempList)
        }
    }

    //SELECT x FROM
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
                _latitud.value = _actualUbication.value!!.latitud
                _longitud.value = _actualUbication.value!!.longitud
                _tag.value = _actualUbication.value!!.tag
                _image.value = _actualUbication.value!!.image.toString()
            }
            else {
                Log.d("UbicationRepository","Current data: null")
            }
        }
    }

    //UPDATE
    fun deleteUbication(intUbicationId: String){
        repository.deleteUbication(intUbicationId)
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
                    storeImageUri(uri)
                }
            }
            .addOnFailureListener{
                Log.i("IMAGE UPLOAD", "Image upload failed")
            }
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////


    /////////////////////////////  AUTENTICACION DE USUARIOS  /////////////////////////////////////

    private val _goToNext = MutableLiveData<Boolean>()
    val goToNext: LiveData<Boolean> = _goToNext

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    var registerError by mutableStateOf(false)
    fun showError(){this.registerError = !this.registerError}

    private val _userId = MutableLiveData<String>()
    private val userId : MutableLiveData<String> = _userId

    private val _loggedUser = MutableLiveData<String>()
    private val loggedUser : MutableLiveData<String> = _loggedUser

    fun registerUser(email: String, password: String){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) { _goToNext.value = true }
                else {
                    val exception = task.exception
                    if (exception is FirebaseAuthUserCollisionException) {
                        _errorMessage.value = "Mail is currently registered"
                        _goToNext.value = false
                        showError()
                    }
                    else {  _errorMessage.value = "User creation error - ${exception?.message}"
                            _goToNext.value = false
                            showError()
                    }
                    _goToNext.value = false
                }
            }
    }

    fun loginUser(email: String?, password: String?) {
        auth.signInWithEmailAndPassword(email!!, password!!)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _userId.value = task.result.user?.uid
                    _loggedUser.value = task.result.user?.email?.split("@")?.get(0)
                    _goToNext.value = true
                } else {
                    _goToNext.value = false
                    _errorMessage.value = task.exception?.message ?: "Error desconocido al iniciar sesión"
                    showError()
                    Log.d("Error", "Error signing in: ${task.exception}")
                }
                // No propagues la excepción fuera de este bloque
                //modifyProcessing()
            }
    }


    fun signOut(){
        _userId.value = ""
        _loggedUser.value = ""
        _goToNext.value = false
        auth.signOut()
    }

}
    /* var closeNav : Boolean by mutableStateOf(true)
       fun closeNavigationMenu(){ this.closeNav = !this.closeNav }*/


