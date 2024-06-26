package orlando.p4_mapsapp_orlandochirinos.Models

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import android.net.Uri
import android.os.Environment
import android.provider.Settings
import com.google.android.gms.maps.model.LatLng
import orlando.p4_mapsapp_orlandochirinos.ModelView.MapViewmodel
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


fun tryAddNewLocation(
    mapViewModel: MapViewmodel,
    selectedLocation: LatLng?) {

    if (selectedLocation != null && mapViewModel.nameOfPlace.length > 1 &&
        mapViewModel.tagSelected in mapViewModel.tagList) {

        mapViewModel.repository.addUbication(
            Ubicacion(
                ubicationId = mapViewModel.nameOfPlace,
                ubicationName = mapViewModel.nameOfPlace,
                markerOwner = mapViewModel.userId.value.toString(),
                snippet = mapViewModel.description,
                latitud = selectedLocation.latitude,
                longitud = selectedLocation.longitude,
                tag = mapViewModel.tagSelected,
                image = mapViewModel.imageUri )
        )
    }
}

fun openAppSettings(activity: Activity){
    val intent = Intent().apply{
        action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        data = Uri.fromParts("package",activity.packageName,null)
        flags = Intent.FLAG_ACTIVITY_NEW_TASK
    }
    activity.startActivity(intent)
}

fun filterMarker(mapViewmodel: MapViewmodel, userId: String) {
    if (mapViewmodel.tagSelected != "") { mapViewmodel.getUbicationsFiltered(mapViewmodel.tagSelected, userId) }
    else { mapViewmodel.getAllUbications(userId) }
}

///////////////////////////////////AUTH: REGISTER AND LOGIN/////////////////////////////////////////

fun tryAddUser(userEmail: String, userPassword: String, mapViewmodel: MapViewmodel) {
    val mailNoSpace = userEmail.trim()
    val passwordNoSpace = userPassword.trim()
    if ( (mailNoSpace.isNotBlank() && mailNoSpace.endsWith("@gmail.com")) &&
        (passwordNoSpace.length > 6) ){
        mapViewmodel.registerUser(mailNoSpace,passwordNoSpace)
        mapViewmodel.clearMailPasswd()
    }
}
fun tryLogin(userEmail: String, userPassword: String, mapViewmodel: MapViewmodel) {
    val mailNoSpace = userEmail.trim()
    val passwordNoSpace = userPassword.trim()
    if ( (mailNoSpace.isNotBlank() && mailNoSpace.endsWith("@gmail.com")) &&
        (passwordNoSpace.length > 6) ){
        mapViewmodel.loginUser(mailNoSpace,passwordNoSpace)
        mapViewmodel.clearMailPasswd()
    }
}