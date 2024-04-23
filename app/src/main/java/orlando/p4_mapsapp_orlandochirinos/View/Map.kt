package orlando.p4_mapsapp_orlandochirinos.View

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import orlando.p4_mapsapp_orlandochirinos.ModelView.CameraViewmodel
import orlando.p4_mapsapp_orlandochirinos.ModelView.MapViewmodel
import orlando.p4_mapsapp_orlandochirinos.Models.Ubicacion
import orlando.p4_mapsapp_orlandochirinos.Models.filterMarker
import orlando.trivial.orlandochirinos_apilistapp.Navigation.Routes

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun MapScreen(
    mapViewModel: MapViewmodel,
    navigationController: NavHostController,
    cameraViewmodel: CameraViewmodel
) {
    MenuLateral(mapViewModel = mapViewModel,navigationController,cameraViewmodel)
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class,
    ExperimentalPermissionsApi::class
)
@Composable
fun MapGoogle(mapViewModel: MapViewmodel,navigationController: NavHostController,cameraViewmodel: CameraViewmodel){
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    val userId by mapViewModel.userId.observeAsState("")
    val availableLocations: List<Ubicacion> by mapViewModel.firestoreAvailableLocations.observeAsState(listOf())

    filterMarker(mapViewModel,userId)

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
            availableLocations.forEach { ubicacion ->
                val position = LatLng(ubicacion.latitud, ubicacion.longitud)
                Marker(
                    state = MarkerState(position = position),
                    title = ubicacion.ubicationName,
                    snippet = ubicacion.snippet,
                    tag = ubicacion.tag,
                )
            }
        }
    /////////////////////////////////////////////////////////////////////////////////////////

/*        val context = LocalContext.current
        val fusedLocationProviderClient =
            remember { LocationServices.getFusedLocationProviderClient(context) }
        val lastKnownLocation by remember { mutableStateOf<Ubicacion?>(null) }
        val deviceLatLng by remember { mutableStateOf(LatLng(mapViewModel.positionToSee.latitude, mapViewModel.positionToSee.longitude)) }
        val localResult = fusedLocationProviderClient.getCurrentLocation(100,null)
   */

    }

    if (mapViewModel.bottomSheet) { Bottom(mapViewModel,navigationController,cameraViewmodel) }
}