package orlando.p4_mapsapp_orlandochirinos.View

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Api
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.launch
import orlando.p4_mapsapp_orlandochirinos.ModelView.MapViewmodel
import orlando.p4_mapsapp_orlandochirinos.Models.tryAddNewLocation
import orlando.trivial.orlandochirinos_apilistapp.Navigation.Routes

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun MapScreen(mapViewModel: MapViewmodel, navigationController: NavHostController) {
    MenuLateral(mapViewModel = mapViewModel,navigationController)
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun MapGoogle(mapViewModel: MapViewmodel){
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

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
            } ) {

            mapViewModel.availableLocations.forEach { ubicacion ->
                Marker(
                    state = MarkerState(position = ubicacion.position),
                    title = ubicacion.nombre,
                    snippet = ubicacion.snippet,
                    tag = ubicacion.tag,
                )
            }
        }
    }
    if (mapViewModel.bottomSheet) { Bottom(mapViewModel) }
}