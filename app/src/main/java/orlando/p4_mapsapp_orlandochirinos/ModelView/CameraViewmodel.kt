package orlando.p4_mapsapp_orlandochirinos.ModelView

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CameraViewmodel : ViewModel() {

    var cameraOpen by mutableStateOf(false)
        private set
    fun openCamera(){ cameraOpen = !cameraOpen }


    private val _cameraPermissionGranted = MutableLiveData(false)
    val cameraPermissionGranted = _cameraPermissionGranted

    private val _shouldShowPermissionRationale = MutableLiveData(false)
    val shouldShowPermissionRationale = _shouldShowPermissionRationale

    private val _showPermissionDenied = MutableLiveData(false)
    val showPermissionDenied = _showPermissionDenied

    fun setCameraPermissionGranted(granted : Boolean){_cameraPermissionGranted.value = granted}
    fun setShouldShowPermissionRationale(should : Boolean){_shouldShowPermissionRationale.value = should}
    fun setshowPermissionDenied(denied : Boolean){_showPermissionDenied.value = denied}

}