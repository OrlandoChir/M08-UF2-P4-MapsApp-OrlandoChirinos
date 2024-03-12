package orlando.p4_mapsapp_orlandochirinos.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.launch
import orlando.p4_mapsapp_orlandochirinos.ui.theme.P4MapsAppOrlandoChirinosTheme
import orlando.p4_mapsapp_orlandochirinos.viewmodels.UBICACIONES

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen() { //EQUIVALENTE DE MyDrawer(myViewModel:APIViewModel)
    val navigationController = rememberNavController() ; val scope = rememberCoroutineScope()
    val state: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    ModalNavigationDrawer(drawerState = state, gesturesEnabled = true, drawerContent = {
        ModalDrawerSheet {
            Text("MIS UBICACIONES", modifier = Modifier.padding(16.dp))
            Divider()
            UBICACIONES.forEach { ubicacion ->
                ListItem(
                    modifier = Modifier.clickable(onClick = {
                        // Aquí puedes realizar alguna acción cuando se hace clic en la ubicación
                        scope.launch { state.close() }
                    }),
                    headlineText = { Text(ubicacion.nombre) } // Añade este parámetro con un texto vacío
                )
            }
        }
    }) {
        NavigationDrawerItem(
            label = { Text(text = "Drawer item") },
            selected = false ,
            onClick = {
                scope.launch {
                    state.close()
                }
                //navigation
            }
        )

        Scaffold (
            topBar = { MyTopAppBar(state) }
        ) { paddingValues ->
            Box( modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.Cyan)) {
                Map()
            }
        }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar (state: DrawerState) {
    val scope = rememberCoroutineScope()
    TopAppBar(
        title = { Text(text = "MAPAITB DIGIEVOLUCION") },
        navigationIcon = {
            IconButton(onClick = {
                scope.launch { state.open() } }
            ) {
                Icon(imageVector = Icons.Filled.Menu, contentDescription = "Menu")
            }
        }
    )
}



@Composable
fun Map(){
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp) )
    {
        val itb = LatLng(41.4534265, 2.1837151)
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(itb, 10f)
        }
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            onMapClick = { /*TODO*/ },
            onMapLongClick = { /*TODO*/ } ) {  }

    }

}

@Composable
fun Markers(){

    val itb = LatLng(41.4534265, 2.1837151)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(itb, 10f) }

    Marker(
        state = MarkerState(position = itb),
        title = "ITB",
        snippet = "Marker at ITB" )


}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    P4MapsAppOrlandoChirinosTheme {
        MapScreen()
    }
}
