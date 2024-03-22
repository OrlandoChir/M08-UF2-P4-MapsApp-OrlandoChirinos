package orlando.p4_mapsapp_orlandochirinos.View

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
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
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cameraswitch
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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

@Composable
fun TakePhotoScreen(mapViewmodel: MapViewmodel,
                    navigationController: NavHostController,
                    cameraViewmodel: CameraViewmodel )
{
    val context = LocalContext.current
    val controller = remember {
        LifecycleCameraController(context).apply { CameraController.IMAGE_CAPTURE }
    }
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
            Icon(imageVector = Icons.Default.Cameraswitch, contentDescription = "Switch Camera")
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
            IconButton(onClick = { /*TODO*/ }) { //ABRIR GALERÍA
                Icon(imageVector = Icons.Default.Photo, contentDescription = "Open Gallery") }

            //HACER LA FOTICO
            IconButton(onClick = {
                takePhoto(context,controller) { photo ->
                    //HACER ALGO CON LA FOTICO (no se el que, sigo el tutorial)
                }
            } ) {
                Icon(imageVector = Icons.Default.PhotoCamera, contentDescription = "Open Gallery") }
        }
    }
}

private fun takePhoto(context: Context,
                      controller: LifecycleCameraController,
                      onPhotoTaken: (Bitmap) -> Unit )
{
    controller.takePicture(ContextCompat.getMainExecutor(context),
        object : ImageCapture.OnImageCapturedCallback(){
            override fun onCaptureSuccess(image: ImageProxy) {
                super.onCaptureSuccess(image)
                onPhotoTaken( image.toBitmap() )
            }

            override fun onError(exception: ImageCaptureException) {
                super.onError(exception)
                Log.e("Camera","Error taken photo",exception)
            }
        }
    )
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
