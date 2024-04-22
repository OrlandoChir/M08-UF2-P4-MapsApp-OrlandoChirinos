package orlando.p4_mapsapp_orlandochirinos.View

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import orlando.p4_mapsapp_orlandochirinos.ModelView.MapViewmodel
import orlando.p4_mapsapp_orlandochirinos.Models.tryAddUser
import orlando.p4_mapsapp_orlandochirinos.Models.tryLogin
import orlando.trivial.orlandochirinos_apilistapp.Navigation.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(mapViewmodel: MapViewmodel, navigationController: NavHostController) {

    val goToNext by mapViewmodel.goToNext.observeAsState(false)
    val error by mapViewmodel.errorMessage.observeAsState("")
    val context = LocalContext.current

    var userEmail by rememberSaveable { mutableStateOf("") }
    var userPassword by rememberSaveable { mutableStateOf("") }

    Box( modifier = Modifier.fillMaxSize() ){
        Column(modifier = Modifier
            .align(Alignment.Center)
            .fillMaxWidth(0.7f) ) {

            //User email
            OutlinedTextField(
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                value = userEmail,
                onValueChange = { userEmail = it },
                label = { Text("Email") },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Magenta,
                    unfocusedBorderColor = Color.Black )
            )

            Spacer(modifier = Modifier.fillMaxHeight(0.02f))

            //User password
            OutlinedTextField(
                value = userPassword,
                onValueChange = { userPassword = it },
                label = { Text("Password") },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Magenta,
                    unfocusedBorderColor = Color.Black )
            )

            Spacer(modifier = Modifier.fillMaxHeight(0.02f))

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.07f)
                    .align(CenterHorizontally),
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(Color.Blue),
                onClick = {
                    tryLogin(userEmail,userPassword,mapViewmodel)
                }) {

                Text(text = "Login", fontSize = 15.sp, color = Color.White)
            }

            Spacer(modifier = Modifier.fillMaxHeight(0.02f))

            Button(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .fillMaxHeight(0.07f)
                    .align(CenterHorizontally),
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(Color.DarkGray),
                onClick = {
                    tryAddUser(userEmail,userPassword,mapViewmodel)
                }
            ) {

                Text(text = "Register", fontSize = 15.sp, color = Color.White)
            }
        }
    }

    BackHandler(enabled = true) {
        // Este bloque de código se ejecutará cada vez que se presione el botón de retroceso

        // Si deseas evitar que la navegación retroceda, puedes verificar una condición, por ejemplo:
        if (!goToNext) {
            // No hagas nada o muestra un mensaje de advertencia, dependiendo de tu caso
            Toast.makeText(context, "Error: No se ha iniciado sesión", Toast.LENGTH_SHORT).show()
        }
    }

    if (goToNext) {
        navigationController.navigate(Routes.MapScreen.route)
        mapViewmodel.screenSelect("map")
    }
    if (mapViewmodel.registerError){
        ErrorDialog(mapViewmodel.registerError,error) { mapViewmodel.showError()} }

}

@Composable
fun ErrorDialog(show: Boolean, errorMessage: String, onDismiss: () -> Unit) {
    if(show){
        Dialog( onDismissRequest = { onDismiss() } ,
                properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true )
        ) {
            Column(
                Modifier
                    .background(Color.White)
                    .padding(24.dp)
                    .fillMaxWidth()) {
                Text(text = errorMessage)
            }
        }
    }
}

