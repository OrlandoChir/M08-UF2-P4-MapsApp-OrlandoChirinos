package orlando.p4_mapsapp_orlandochirinos.View

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.navigation.NavHostController
import orlando.p4_mapsapp_orlandochirinos.ModelView.CameraViewmodel
import orlando.p4_mapsapp_orlandochirinos.ModelView.MapViewmodel
import orlando.trivial.p4_mapsapp_orlandochirinos.R

/*
@Composable
fun GalleryScreen(
    mapViewmodel: MapViewmodel,
    navigationController: NavHostController,
    cameraViewmodel: CameraViewmodel,
) {
    val context = LocalContext.current
    val img : Bitmap? = ContextCompat.getDrawable(context, R.drawable.mapa)?.toBitmap()
    var bitmap by remember { mutableStateOf(img) }
    val launchImage = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult ={
         bitmap =
             if (Build.VERSION.SDK_INT < 28) { MediaStore.Images.Media.getBitmap(context.contentResolver,it) }
             else {
             val source =
                 it?.let { it1 -> ImageDecoder.createSource(context.contentResolver, it1) }
             source?.let { it1-> ImageDecoder.decodeBitmap(it1) }!!
             }
        }
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {

        Button(onClick = { launchImage.launch("image/*") }
        ) { Text(text = "OPEN GALLERY") }

        Image(
            bitmap = bitmap!!.asImageBitmap(), contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(CircleShape)
                .background(Color.Cyan)
                .border(width = 1.dp, color = Color.White, shape = CircleShape)
        )
    }
}
*/

 */

@Composable
fun GalleryScreen(
    mapViewmodel: MapViewmodel,
    navigationController: NavHostController,
    cameraViewmodel: CameraViewmodel,
) {
    val context = LocalContext.current
    val img : Bitmap? = ContextCompat.getDrawable(context, R.drawable.mapa)?.toBitmap()
    var bitmap by remember { mutableStateOf(img) }
    val launchImage = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { result ->
            result?.let { uri ->
                // Verificar si result es null o no
                bitmap =
                    if (Build.VERSION.SDK_INT < 28) {
                        MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
                    } else {
                        val source = ImageDecoder.createSource(context.contentResolver, uri)
                        ImageDecoder.decodeBitmap(source)
                    }
            }
        }
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {

        Button(onClick = { launchImage.launch("image/*") }
        ) { Text(text = "OPEN GALLERY") }

        // Utilizamos el operador de elvis (?:) para manejar el posible valor nulo de bitmap
        bitmap?.let { imageBitmap ->
            Image(
                bitmap = imageBitmap.asImageBitmap(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(CircleShape)
                    .background(Color.Cyan)
                    .border(width = 1.dp, color = Color.White, shape = CircleShape)
            )
        }
    }
}
