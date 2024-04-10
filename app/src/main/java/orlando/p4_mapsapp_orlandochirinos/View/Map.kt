package orlando.p4_mapsapp_orlandochirinos.View

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import orlando.p4_mapsapp_orlandochirinos.ModelView.CameraViewmodel
import orlando.p4_mapsapp_orlandochirinos.ModelView.MapViewmodel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun MapScreen(
    mapViewModel: MapViewmodel,
    navigationController: NavHostController,
    cameraViewmodel: CameraViewmodel
) {
    MenuLateral(mapViewModel = mapViewModel,navigationController,cameraViewmodel)
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun MapGoogle(mapViewModel: MapViewmodel,navigationController: NavHostController,cameraViewmodel: CameraViewmodel){
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    mapViewModel.getAllUbications()

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(5.dp) )
    {
        //Posicion por defecto && Posicionamiento y zoom de cámara.
        val defaultPosition = mapViewModel.positionToSee
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(defaultPosition, 15f) }

        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            onMapLongClick = {  coordinates ->
                mapViewModel.onMapLongClick(coordinates)
                mapViewModel.showBottomSheet()
            }
        ) {

            //REEMPLAZAR POR EL REPOSITORY
            mapViewModel.availableLocations.forEach { ubicacion ->
                val position = LatLng(ubicacion.latitud, ubicacion.longitud)
                Marker(
                    state = MarkerState(position = position),
                    title = ubicacion.ubicationName,
                    snippet = ubicacion.snippet,
                    tag = ubicacion.tag,
                )
            }
        }
    }
    if (mapViewModel.bottomSheet) { Bottom(mapViewModel,navigationController,cameraViewmodel) }
}