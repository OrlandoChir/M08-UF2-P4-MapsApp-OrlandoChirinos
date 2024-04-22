package orlando.p4_mapsapp_orlandochirinos.View

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
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
import orlando.p4_mapsapp_orlandochirinos.R
import orlando.trivial.orlandochirinos_apilistapp.Navigation.Routes

@Composable
fun GalleryScreen(
    mapViewmodel: MapViewmodel,
    navigationController: NavHostController,
    cameraViewmodel: CameraViewmodel,
) {
    val context = LocalContext.current
    val img : Bitmap? = ContextCompat.getDrawable(context, R.drawable.mapa)?.toBitmap()
    var bitmap by remember { mutableStateOf(img) }
    var uriGet by remember { mutableStateOf<Uri?>(null ) }

    val launchImage = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            // Verificar si uri es nulo
            if (uri != null) {
                val contentResolver = context.contentResolver
                val mime = contentResolver.getType(uri) // Obtener el tipo MIME de la URI

                // Verificar si el tipo MIME es de una imagen
                if (mime?.startsWith("image/") == true) {
                    // Verificar si el tipo de archivo es JPEG, PNG o JPG
                    if (mime.endsWith("jpeg") || mime.endsWith("png") || mime.endsWith("jpg")) {
                        mapViewmodel.setImageUriF(uri)
                        bitmap =
                            if (Build.VERSION.SDK_INT < 28) {
                                MediaStore.Images.Media.getBitmap(contentResolver, uri) }
                            else {
                                val source = ImageDecoder.createSource(contentResolver, uri)
                                ImageDecoder.decodeBitmap(source) }
                    }
                    else {
                        // El archivo seleccionado no es de un formato admitido
                        Toast.makeText(context, "Formato de imagen no admitido", Toast.LENGTH_SHORT).show() }
                }
                else {
                    // El archivo seleccionado no es una imagen
                    Toast.makeText(context, "Selecciona una imagen", Toast.LENGTH_SHORT).show() }
            }
        }
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {

        Button(onClick = { launchImage.launch("image/*") }) {
            Text(text = "OPEN GALLERY") }

        // Utilizamos el operador de elvis (?:) para manejar el posible valor nulo de bitmap
        if (bitmap != null) {
            Image(
                bitmap = bitmap!!.asImageBitmap(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(CircleShape)
                    .size(250.dp)
                    .background(Color.Cyan)
                    .border(width = 1.dp, color = Color.White, shape = CircleShape)
            )
        }
        else { Text(text = "No image selected") }

        Button(onClick = {
            if (mapViewmodel.imageUriFirebase != null) {
                mapViewmodel.uploadImage(mapViewmodel.imageUriFirebase!!)
                navigationController.navigate(Routes.MapScreen.route)
            }
        } )
        { Text(text = "Upload Image") }

        Button(onClick = { navigationController.navigate(Routes.MapScreen.route) }) {
            Text(text = "GO BACK")
        }
    }
}