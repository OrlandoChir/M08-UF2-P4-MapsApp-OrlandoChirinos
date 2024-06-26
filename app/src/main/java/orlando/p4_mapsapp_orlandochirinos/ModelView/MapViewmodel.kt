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

    val defaultBitmap: Bitmap? = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)

    val tagList = listOf<String>("Favoritos", "Restaurantes", "Parques", "Casas")

    val screenList = listOf<String>("login","map","markerlist","markerdetail","camera")

    var tagSelected by mutableStateOf("")
    fun clearTag(){ tagSelected = "" }
    fun selectTag(tagValue: String) { this.tagSelected = tagValue }
    var imageBitmap by mutableStateOf( this.defaultBitmap )
        private set

    fun storeImageBitmap(bitmap : Bitmap?) { this.imageBitmap =  bitmap  }
    fun clearImageAndUri(){
        this.imageUriFirebase = null
        this.imageBitmap = defaultBitmap
    }

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

    var loginMail by mutableStateOf("")
        private set
    fun setMail(pMail : String){this.loginMail = pMail}

    var loginPasswd by mutableStateOf("")
        private set
    fun setPasswd(pPasswd : String){this.loginPasswd = pPasswd}
    var loginPasswdConfirm by mutableStateOf("")
        private set
    fun setPasswdConfirm(pPasswd : String){this.loginPasswdConfirm = pPasswd}

    fun clearMailPasswd(){this.loginMail = "" ; this.loginPasswd = "" ; this.loginPasswdConfirm = ""}

    fun clearModal(){
        this.nameOfPlace = ""
        this.description = ""
        this.imageUriFirebase = null
    }

    var bottomSheet : Boolean by  mutableStateOf(false)
        private set

    fun showBottomSheet(){ this.bottomSheet = !this.bottomSheet }

    private val _selectedPosition = MutableStateFlow<LatLng?>(null)
    val selectedPosition: StateFlow<LatLng?> = _selectedPosition
    fun onMapLongClick(coordinates: LatLng) { _selectedPosition.value = coordinates }

    ///////////////////////////////////////////////////////////////////////////////////////////////

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
    fun getAllUbications(userId: String){
        repository.getAllUbications().whereEqualTo("ubicationOwner",userId).addSnapshotListener{ value, error ->
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
    fun getUbicationsFiltered(tagValue: String, userId: String){
        //FILTRO
         repository.getAllUbications()
             .whereEqualTo("tag",tagValue)
             .whereEqualTo("ubicationOwner",userId).addSnapshotListener{ value, error ->
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

    //DELETE
    fun deleteUbication(intUbicationId: String){ repository.deleteUbication(intUbicationId) }
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
    val userId : MutableLiveData<String> = _userId

    private val _userMail = MutableLiveData<String>()
    val userMail : MutableLiveData<String> = _userMail

    private val _loggedUser = MutableLiveData<String>()
    val loggedUser : MutableLiveData<String> = _loggedUser

    fun registerUser(email: String, password: String){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _userMail.value = email
                    _goToNext.value = true
                    _userId.value = task.result.user?.uid
                    _loggedUser.value = task.result.user?.email?.split("@")?.get(0)
                }
                else {
                    val exception = task.exception
                    if (exception is FirebaseAuthUserCollisionException) {
                        _errorMessage.value = "Correo ya registrado"
                        _goToNext.value = false
                        showError()
                    }
                    else {
                        _errorMessage.value = "Error al crear usuario: ${exception?.message}"
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
                    _userMail.value = email!!
                    _userId.value = task.result.user?.uid
                    _loggedUser.value = task.result.user?.email?.split("@")?.get(0)
                    _goToNext.value = true
                } else {
                    _goToNext.value = false
                    _errorMessage.value = "Usuario o contraseñas incorrectos."
                    showError()
                    Log.d("Error", "Error signing in: ${task.exception}")
                }
            }
    }


    fun signOut() {
        clearModal()
        clearTag()
        _userMail.value = ""
        _userId.value = ""
        _loggedUser.value = ""
        _goToNext.value = false
        auth.signOut()
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////
}



