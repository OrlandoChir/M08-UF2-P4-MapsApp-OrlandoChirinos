package orlando.p4_mapsapp_orlandochirinos.view

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
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Map
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.launch
import orlando.p4_mapsapp_orlandochirinos.models.ubicationTypes
import orlando.p4_mapsapp_orlandochirinos.viewmodels.MapViewmodel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun MapScreen(mapViewModel: MapViewmodel) {
    MyDrawer(mapViewModel = mapViewModel)
    //Bottom()
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun MapGoogle(mapViewModel: MapViewmodel){
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }


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
            onMapLongClick = { showBottomSheet = !showBottomSheet } ) {

            mapViewModel.UBICACIONES.forEach { ubicacion ->
                Marker(
                    state = MarkerState(position = ubicacion.position),
                    title = ubicacion.nombre,
                    snippet = ubicacion.snippet
                )
            }
        }
    }
    if (showBottomSheet) { Bottom() }
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
            Row(
                modifier = Modifier.fillMaxWidth(0.88f), // Para ocupar el max del ancho disponible
                horizontalArrangement = Arrangement.Center // Para centrar los elementos horizontalmente
            ) {
                Icon(imageVector = Icons.Filled.Api, contentDescription = "API" )
                Text("MAPA ITB")
                Icon(imageVector = Icons.Filled.Api, contentDescription = "API" )
            }
        },
        navigationIcon = {
            IconButton(onClick = { scope.launch { state.open() } }) {
                Icon(imageVector = Icons.Filled.Menu, contentDescription = "Menu")
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Bottom(){
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
    var nameOfPlace by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    ModalBottomSheet(
        onDismissRequest = { showBottomSheet = false },
        sheetState = sheetState ) {
        // Sheet content

        Box (modifier = Modifier
            .fillMaxWidth(0.6f)
            .fillMaxHeight()
            .align(Alignment.CenterHorizontally) ) {

            Column {

                OutlinedTextField(
                    value = nameOfPlace,
                    onValueChange = { nameOfPlace = it },
                    label = { Text("Nombre del lugar") },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Green, unfocusedBorderColor = Color.Black
                    )
                )

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Descripción del lugar") },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Green, unfocusedBorderColor = Color.Black
                    )
                )

                Spacer(modifier = Modifier.fillMaxHeight(0.03f))
                SelectCategories()

                Icon(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.2f)
                        .clickable { /*TODO*/ },
                    imageVector = Icons.Filled.CameraAlt, contentDescription = "CAMERA"
                )

                Button(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth()
                        .fillMaxWidth(),
                    shape = RectangleShape,
                    colors = ButtonDefaults.buttonColors(Color.DarkGray),
                    onClick = {
                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                showBottomSheet = false
                            }
                        }
                    }
                ) {
                    Text("GO BACK") //Después tiene que añadir el marcador.
                }
                Spacer(modifier = Modifier.fillMaxHeight(0.05f))
            }
        }
    }
}

@Composable
fun SelectCategories(){
    var selectedText by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = selectedText,
        onValueChange = { selectedText = it },
        enabled = false,
        readOnly = true,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.08f)
            .clickable { expanded = true },
        trailingIcon = {
            Icon(imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = "Drop",
                modifier = Modifier.clickable { expanded = true } )
        }
    )

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false },
        modifier = Modifier.fillMaxWidth(0.6f)
    ) {
        ubicationTypes.forEach { typeOfUbication ->
            DropdownMenuItem(
                text = { Text(text = typeOfUbication) },
                onClick = { expanded = false ; selectedText = typeOfUbication }
            )
        }
    }
}
