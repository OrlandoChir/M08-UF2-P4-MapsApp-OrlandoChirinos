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
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Api
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ArrowDropDown
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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import kotlinx.coroutines.launch
import orlando.p4_mapsapp_orlandochirinos.ModelView.CameraViewmodel
import orlando.p4_mapsapp_orlandochirinos.ModelView.MapViewmodel
import orlando.p4_mapsapp_orlandochirinos.Models.tryAddNewLocation
import orlando.trivial.orlandochirinos_apilistapp.Navigation.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuLateral(
    mapViewModel: MapViewmodel,
    navigationController: NavHostController,
    cameraViewmodel: CameraViewmodel
) {
    val scope = rememberCoroutineScope()
    val state: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val loggedUser by mapViewModel.loggedUser.observeAsState("")

    ModalNavigationDrawer(
        drawerState = state, gesturesEnabled = false,
        drawerContent = {
            ModalDrawerSheet {
                Row(modifier = Modifier.fillMaxWidth(),verticalAlignment = Alignment.CenterVertically) {

                    Column(modifier = Modifier.fillMaxHeight(0.15f).align(CenterVertically)) {
                        Text(text = "MENU DE NAVEGACIÓN", modifier = Modifier
                            .padding(10.dp) )

                        Text(text = "Usuario: $loggedUser", modifier = Modifier
                        //    .weight(1f)
                            .padding(10.dp) )
                    }

                    Spacer(modifier = Modifier.weight(0.2f))

                    Icon( /*Cerrar el menú lateral*/
                        modifier = Modifier
                            .padding(16.dp)
                            .clickable { scope.launch { state.close() } },
                        imageVector = Icons.Filled.ArrowBackIosNew,
                        contentDescription = "CLOSE")
                }
                
                Divider ()
                Column (modifier = Modifier
                    .padding(top = 5.dp)
                    .align(Alignment.CenterHorizontally) ) {

                    Button(
                        modifier = Modifier.fillMaxWidth(0.7f),
                        shape = RectangleShape,
                        colors = ButtonDefaults.buttonColors(Color.Magenta),
                        onClick = { mapViewModel.clearTag() })
                    { Text(text = "CLEAR TAGS",color = Color.White) }

                    Box(modifier = Modifier.fillMaxWidth(0.7f) ) {
                        SelectCategories(mapViewModel = mapViewModel)
                    }

                    Button(
                        modifier = Modifier.fillMaxWidth(0.7f),
                        shape = RectangleShape,
                        colors = ButtonDefaults.buttonColors(Color.Magenta),
                        onClick = {
                            mapViewModel.screenSelect(mapViewModel.screenList[2])
                            scope.launch { state.close() }
                            /*navigationController.navigate(Routes.MarkerListScreen.route)*/ })
                    {
                        Text(text = "MIS MARCADORES",color = Color.White)
                    }

                    Button(
                        modifier = Modifier.fillMaxWidth(0.7f),
                        shape = RectangleShape,
                        colors = ButtonDefaults.buttonColors(Color.Magenta),
                        onClick = {
                            mapViewModel.screenSelect(mapViewModel.screenList[1])
                            scope.launch { state.close() }}) {
                        Text(text = "MAPA",color = Color.White)
                    }

                    Button(
                        modifier = Modifier.fillMaxWidth(0.7f),
                        shape = RectangleShape,
                        colors = ButtonDefaults.buttonColors(Color.Magenta),
                        onClick = {
                            mapViewModel.screenSelect(mapViewModel.screenList[0])
                            navigationController.navigate(Routes.LoginScreen.route)
                            mapViewModel.signOut()
                             }) {
                        Text(text = "CERRAR SESIÓN",color = Color.White)
                    }
                }
            }
        } ) {
        ScreenAdmin(mapViewModel, state,navigationController,cameraViewmodel)
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenAdmin (
    mapViewModel: MapViewmodel,
    state: DrawerState,
    navigationController: NavHostController,
    cameraViewmodel: CameraViewmodel
) {
    Scaffold(
        topBar = { TopBar(mapViewModel = mapViewModel, state = state) } ) { paddingValues ->

        Box(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues) ) {

            when(mapViewModel.currentScreen){
                mapViewModel.screenList[0] -> { LoginScreen(mapViewModel,navigationController) }
                mapViewModel.screenList[1] -> { MapGoogle( mapViewModel,navigationController,cameraViewmodel ) }
                mapViewModel.screenList[2] -> { MarkerListScreen(mapViewModel,navigationController) }
                mapViewModel.screenList[3] -> {  }
                mapViewModel.screenList[4] -> { CameraScreen( mapViewModel, navigationController, cameraViewmodel ) }
            }
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

@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Bottom(
    mapViewModel: MapViewmodel,
    navigationController: NavHostController,
    cameraViewmodel: CameraViewmodel
){
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    val selectedLocation by mapViewModel.selectedPosition.collectAsState()

    ModalBottomSheet(
        onDismissRequest = { mapViewModel.showBottomSheet()
                             mapViewModel.clearTag()
                             mapViewModel.clearModal()
                             mapViewModel.clearImageAndUri()
                           },
        sheetState = sheetState ) {

        // Sheet content
        Box (modifier = Modifier
            .fillMaxWidth(0.6f)
            .fillMaxHeight()
            .align(Alignment.CenterHorizontally) ) {

            Column {

                if (mapViewModel.imageUriFirebase != null) {
                    GlideImage(
                        model = mapViewModel.imageUri,
                        contentDescription = "Image preview",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(100.dp)
                            .align(CenterHorizontally)
                    )
                    Spacer(modifier = Modifier.fillMaxHeight(0.02f))
                }

                OutlinedTextField(
                    value = mapViewModel.nameOfPlace,
                    onValueChange = { mapViewModel.nameOfPlace = it },
                    label = { Text("Nombre del lugar") },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Magenta, unfocusedBorderColor = Color.Black )
                )

                OutlinedTextField(
                    value = mapViewModel.description,
                    onValueChange = { mapViewModel.description = it },
                    label = { Text("Descripción del lugar") },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Magenta, unfocusedBorderColor = Color.Black )
                )

                Spacer(modifier = Modifier.fillMaxHeight(0.03f))

                SelectCategories(mapViewModel)

                CameraScreen( mapViewModel, navigationController, cameraViewmodel )

                Button(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth()
                        .fillMaxWidth(),
                    shape = RectangleShape,
                    colors = ButtonDefaults.buttonColors(Color.DarkGray),
                    onClick = {
                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            //Añadir (si se puede) el marcador
                            tryAddNewLocation(mapViewModel, selectedLocation)

                            //Cerrar bottomsheet
                            if (!sheetState.isVisible) {
                                mapViewModel.showBottomSheet()
                                mapViewModel.selectTag("")
                                mapViewModel.clearTag()
                                mapViewModel.clearModal()
                                mapViewModel.clearImageAndUri()
                            }
                        }
                    }
                ) {
                    Text("AÑADIR MARCADOR") //Después tiene que añadir el marcador.
                }
                Spacer(modifier = Modifier.fillMaxHeight(0.05f))
            }
        }
    }
}

@OptIn(ExperimentalStdlibApi::class)
@Composable
fun SelectCategories(mapViewModel: MapViewmodel){
    var selectedText by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = mapViewModel.tagSelected,
        onValueChange = { selectedText = it },
        enabled = false,
        readOnly = true,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.09f)
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
        mapViewModel.tagList.forEach { typeOfUbication ->
            DropdownMenuItem(
                text = { Text(text = typeOfUbication) },
                onClick = { expanded = false ; mapViewModel.selectTag(typeOfUbication) }
            )
        }
    }
}
