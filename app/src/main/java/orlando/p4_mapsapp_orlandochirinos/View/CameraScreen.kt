package orlando.p4_mapsapp_orlandochirinos.View

import android.Manifest
import android.app.Activity
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.navigation.NavHostController
import orlando.p4_mapsapp_orlandochirinos.ModelView.CameraViewmodel
import orlando.p4_mapsapp_orlandochirinos.ModelView.MapViewmodel
import orlando.trivial.orlandochirinos_apilistapp.Navigation.Routes


@Composable
fun CameraScreen(mapViewmodel: MapViewmodel,
                 navigationController: NavHostController,
                 cameraViewmodel: CameraViewmodel )
{
    val context = LocalContext.current
    val isCameraPermissionGranted by cameraViewmodel.cameraPermissionGranted.observeAsState(false)
    val shouldShowPermissionRationale by cameraViewmodel.shouldShowPermissionRationale.observeAsState(false)
    val showPermissionDenied by cameraViewmodel.showPermissionDenied.observeAsState(false)

    //DIAPO 9
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted){ cameraViewmodel.setCameraPermissionGranted(true) }
            else { cameraViewmodel.setShouldShowPermissionRationale(
                shouldShowRequestPermissionRationale(
                    context as Activity,
                    Manifest.permission.CAMERA )
            )
                if (!shouldShowPermissionRationale) {
                    Log.i("CameraScreen", "UNABLE TO ASK FOR PERMISSION AGAIN")
                    cameraViewmodel.setshowPermissionDenied(true)
                }
             }
        }
    )

 //   Column( modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
  //    Button(onClick = {

    Icon(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.2f)
            .clickable {
                if (!isCameraPermissionGranted) { launcher.launch(Manifest.permission.CAMERA) }
                else { navigationController.navigate(Routes.TakePhotoScreen.route) } },
                imageVector = Icons.Filled.CameraAlt, contentDescription = "CAMERA" )
/*           } ) { Text(text = "TAKE PHOTO") }
        }*/
    if (showPermissionDenied) { PermissionDeclinedScreen() }

}