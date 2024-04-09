package orlando.p4_mapsapp_orlandochirinos.ModelView

import android.graphics.Bitmap
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import orlando.p4_mapsapp_orlandochirinos.Models.Ubicacion

class MapViewmodel : ViewModel() {

    //private val database = FirebaseFirestore.getInstance()

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

    var imageUri by mutableStateOf( "" )
        private set


    fun storeImageUri(intImageUri : String) { this.imageUri = intImageUri  }


    var currentScreen by mutableStateOf("")
        private set
    fun screenSelect(screenValue: String) { this.currentScreen = screenValue }
    var availableLocations : MutableList<Ubicacion> by mutableStateOf(
        mutableListOf(
            Ubicacion(
                        ubicationId = null,
                        ubicationName = "ITB",
                        snippet = "MARKER AT ITB",
                        position = LatLng(41.4534265, 2.1837151),
                        tag = tagList[0],
                        image = this.imageUri )
        )
    )
        private set
    fun addLocation( newUbication : Ubicacion ){ this.availableLocations.add(newUbication) }
    fun getAllLocations():List<Ubicacion> { return this.availableLocations }

    var positionToSee: LatLng = availableLocations[0].position
        private set
    fun changePosition(newPosition : LatLng){ this.positionToSee = newPosition }

    private val _selectedLocation = MutableStateFlow<LatLng?>(null)
    val selectedLocation: StateFlow<LatLng?> = _selectedLocation

    fun onMapLongClick(coordinates: LatLng) { _selectedLocation.value = coordinates }

    var bottomSheet : Boolean by  mutableStateOf(false)
        private set
    fun showBottomSheet(){ this.bottomSheet = !this.bottomSheet }


    /*    var closeNav : Boolean by mutableStateOf(true)

        fun closeNavigationMenu(){ this.closeNav = !this.closeNav }*/
}

