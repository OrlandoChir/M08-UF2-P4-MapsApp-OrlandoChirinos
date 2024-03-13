package orlando.p4_mapsapp_orlandochirinos.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import orlando.p4_mapsapp_orlandochirinos.viewmodels.UBICACIONES

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun MapScreen() {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(5.dp) )
    {
        val itb = LatLng(41.4534265, 2.1837151)
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(itb, 10f) }

        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState) {

            UBICACIONES.forEach {
                Marker(
                    state = MarkerState(position = it.latLng),
                    title = it.nombre,
                    snippet = it.snippet )
            }

        }

    }

}

/*@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    P4MapsAppOrlandoChirinosTheme {
        MapScreen()
    }
}*/
