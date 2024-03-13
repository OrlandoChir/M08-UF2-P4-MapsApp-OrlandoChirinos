package orlando.p4_mapsapp_orlandochirinos.view

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState


import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import orlando.p4_mapsapp_orlandochirinos.viewmodels.MapViewmodel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun MapScreen(mapViewModel: MapViewmodel) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(5.dp) )
    {
        //Posicion por defecto && Posicionamiento y zoom de cámara.
        val defaultPosition = mapViewModel.UBICACIONES[0].position
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(defaultPosition, 15f) }

        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            onMapClick = { /*TODO*/ },
            onMapLongClick = { /*TODO*/ } ) {

            mapViewModel.UBICACIONES.forEach { ubicacion ->
                Marker(
                    state = MarkerState(position = ubicacion.position),
                    title = ubicacion.nombre,
                    snippet = ubicacion.snippet )
            }

        }

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyDrawer(mapViewModel : MapViewmodel) {
    val navigationController = rememberNavController()
    val scope = rememberCoroutineScope()
    val state: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    ModalNavigationDrawer(
        drawerState = state, gesturesEnabled = false,
        drawerContent = {
            ModalDrawerSheet {
                Text("Drawer title", modifier = Modifier.padding(16.dp) )
                Divider ()
                //Drawer items
            }
        }) {
        MyScaffold(mapViewModel, state)
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyScaffold(mapViewModel: MapViewmodel, state: DrawerState) {

}


/*@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    P4MapsAppOrlandoChirinosTheme {
        MapScreen()
    }
}*/
