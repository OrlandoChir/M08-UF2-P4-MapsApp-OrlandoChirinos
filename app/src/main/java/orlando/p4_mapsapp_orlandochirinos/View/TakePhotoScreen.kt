package orlando.p4_mapsapp_orlandochirinos.View

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cameraswitch
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import orlando.p4_mapsapp_orlandochirinos.ModelView.CameraViewmodel
import orlando.p4_mapsapp_orlandochirinos.ModelView.MapViewmodel
import orlando.p4_mapsapp_orlandochirinos.R
import orlando.trivial.orlandochirinos_apilistapp.Navigation.Routes
import java.io.File

@Composable
fun TakePhotoScreen(mapViewmodel: MapViewmodel,
                    navigationController: NavHostController,
                    cameraViewmodel: CameraViewmodel )
{
    var galleryOpen by rememberSaveable { mutableStateOf(false) }

    val context = LocalContext.current
    val controller = remember { LifecycleCameraController(context).apply { CameraController.IMAGE_CAPTURE } }
    Box(modifier = Modifier.fillMaxSize() ) {
        CameraPreview(controller = controller, modifier = Modifier.fillMaxSize() )
        IconButton(
            modifier = Modifier.offset(16.dp,16.dp),
            onClick = {
            controller.cameraSelector =
                if ( controller.cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA ){
                    CameraSelector.DEFAULT_FRONT_CAMERA }
                else { CameraSelector.DEFAULT_BACK_CAMERA }
            } ) {
            Icon(modifier = Modifier.size(60.dp),imageVector = Icons.Default.Cameraswitch, contentDescription = "Switch Camera")
        }
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row (verticalAlignment = Alignment.CenterVertically,
             horizontalArrangement = Arrangement.SpaceAround,
             modifier = Modifier.fillMaxWidth() ) {

            //ABRIR GALERÍA
            IconButton(modifier = Modifier.size(60.dp),onClick = {
                galleryOpen = true
                navigationController.navigate(Routes.GalleryScreen.route) } )
            { Icon(modifier = Modifier.size(50.dp), imageVector = Icons.Default.Photo, contentDescription = "OpenGallery") }


            //HACER LA FOTICO
            IconButton(modifier = Modifier.size(60.dp),onClick = {
                takePhoto(context,controller,mapViewmodel,navigationController) { photo,uri ->
                    //HACER ALGO CON LA FOTICO (no se el que, sigo el tutorial)
                    mapViewmodel.storeImageBitmap(photo)
                }
            } )
            { Icon(modifier = Modifier.size(50.dp),imageVector = Icons.Default.PhotoCamera, contentDescription = "Take Photo") }
        }
    }
}

//Modificaciones gracias a chat gipiti, pero la base es mia
private fun takePhoto(
    context: Context,
    controller: LifecycleCameraController,
    mapViewmodel: MapViewmodel,
    navigationController: NavHostController,
    onPhotoTaken: (Bitmap, Uri) -> Unit
) {
    val outputDirectory = getOutputDirectory(context)
    val photoFile = File(outputDirectory, "${System.currentTimeMillis()}.jpg")

    val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

    controller.takePicture(outputOptions, ContextCompat.getMainExecutor(context),
        object : ImageCapture.OnImageSavedCallback {
            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                val savedUri = Uri.fromFile(photoFile)
                val bitmap = BitmapFactory.decodeFile(photoFile.absolutePath)

                // Guardar la imagen en el ViewModel
                mapViewmodel.storeImageBitmap(bitmap)
                mapViewmodel.setImageUriF(savedUri)

                // Navegar a la siguiente pantalla después de guardar la imagen
                navigationController.navigate(Routes.GalleryScreen.route)
            }

            override fun onError(exception: ImageCaptureException) {
                Log.e("Camera", "Error al tomar la foto", exception)
            }
        }
    )
}


private fun getOutputDirectory(context: Context): File {
    val mediaDir = context.externalMediaDirs.firstOrNull()?.let {
        File(it, context.getString(R.string.app_name)).apply { mkdirs() }
    }
    return if (mediaDir != null && mediaDir.exists())
        mediaDir else context.filesDir
}

@Composable
fun CameraPreview(controller: LifecycleCameraController, modifier: Modifier = Modifier) {
    val lifeCycleOwner = LocalLifecycleOwner.current
    AndroidView(
        factory = {
            PreviewView(it).apply {
                this.controller = controller
                controller.bindToLifecycle(lifeCycleOwner)
        }
    }, modifier = modifier )
}
