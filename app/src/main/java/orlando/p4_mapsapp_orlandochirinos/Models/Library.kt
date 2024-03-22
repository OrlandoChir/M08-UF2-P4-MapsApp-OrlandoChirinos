package orlando.p4_mapsapp_orlandochirinos.Models

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import com.google.android.gms.maps.model.LatLng
import orlando.p4_mapsapp_orlandochirinos.ModelView.MapViewmodel

fun tryAddNewLocation(
    mapViewModel: MapViewmodel,
    selectedLocation: LatLng?,
    nameOfPlace: String,
    description: String ) {
    if (selectedLocation != null && nameOfPlace.length > 1 &&
        mapViewModel.tagSelected in mapViewModel.tagList) {
        mapViewModel.addLocation(
            Ubicacion(
                nameOfPlace,
                description,
                selectedLocation,
                mapViewModel.tagSelected )
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