package orlando.p4_mapsapp_orlandochirinos.view

import androidx.compose.foundation.layout.Box

import androidx.compose.ui.Alignment.Companion.CenterHorizontally

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding

import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Api
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState

import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.launch
import orlando.p4_mapsapp_orlandochirinos.viewmodels.MapViewmodel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun MapScreen(mapViewModel: MapViewmodel) {
    MyDrawer(mapViewModel = mapViewModel)
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun MapGoogle(mapViewModel: MapViewmodel){
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
                Row {
                    Text("MENU TO WAPO", modifier = Modifier.padding(16.dp) )
                    Icon(imageVector = Icons.Filled.Map, contentDescription = "MAP" )
                }
                Divider ()
                /* Drawer items:

                LogOut
                 */
            }
        }) {
        MyScaffold(mapViewModel, state)
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyScaffold(mapViewModel: MapViewmodel, state: DrawerState) {

    Scaffold(
        topBar = { TopBar(mapViewModel = mapViewModel, state = state) }
    ) { paddingValues ->

        Box(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues) ) {
            MapGoogle(mapViewModel = mapViewModel)
        }

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(mapViewModel: MapViewmodel, state: DrawerState) {
    val scope = rememberCoroutineScope()
    TopAppBar(
        title = {
            Row (){
                Icon(imageVector = Icons.Filled.Api, contentDescription = "API" )
                Text("MAPA ITB" )
                Icon(imageVector = Icons.Filled.Api, contentDescription = "API" ) }
                },
        navigationIcon = {
            IconButton(onClick = {
                scope.launch { state.open() } } ) {
                Icon(imageVector = Icons.Filled.Menu, contentDescription = "Menu")
            }
        }
    )
}

/*@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    P4MapsAppOrlandoChirinosTheme {
        MapScreen()
    }
}*/
