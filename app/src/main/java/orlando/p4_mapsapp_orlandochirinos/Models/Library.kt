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
    selectedLocation: LatLng?,
    nameOfPlace: String,
    description: String ) {

    if (selectedLocation != null && nameOfPlace.length > 1 &&
        mapViewModel.tagSelected in mapViewModel.tagList) {

        mapViewModel.repository.addUbication(
            Ubicacion(
                ubicationId = nameOfPlace ,
                ubicationName  = nameOfPlace,
                snippet = description,
                position = selectedLocation,
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



object BitmapToUriConverter {

    fun getImageUri(context: Context, bitmap: Bitmap): Uri {
        // Guardar el bitmap en el almacenamiento externo
        val filename = "imagen_temporal.png"
        val file = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), filename)
        try {
            val fos = FileOutputStream(file)
            bitmap.compress(CompressFormat.PNG, 100, fos)
            fos.flush()
            fos.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        // Devolver la Uri del archivo guardado
        return Uri.fromFile(file)
    }
}
