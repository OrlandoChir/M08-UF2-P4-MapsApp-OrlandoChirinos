package orlando.p4_mapsapp_orlandochirinos.View

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import orlando.p4_mapsapp_orlandochirinos.ModelView.MapViewmodel
import orlando.p4_mapsapp_orlandochirinos.Models.tryLogin
import orlando.p4_mapsapp_orlandochirinos.R
import orlando.trivial.orlandochirinos_apilistapp.Navigation.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(mapViewmodel: MapViewmodel, navigationController: NavHostController) {

    val goToNext by mapViewmodel.goToNext.observeAsState(false)
    val error by mapViewmodel.errorMessage.observeAsState("")
    val context = LocalContext.current

    Box( modifier = Modifier.fillMaxSize() ){
        Column(modifier = Modifier
            .align(Alignment.Center)
            .fillMaxWidth(0.7f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
            ) {

            Image(painter = painterResource(R.drawable.mapa), contentDescription = "Logo")

            //User email
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                value = mapViewmodel.loginMail,
                onValueChange = { mapViewmodel.setMail(it) },
                label = { Text("Email") },
                maxLines = 1,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Magenta,
                    unfocusedBorderColor = Color.Black )
            )

            Spacer(modifier = Modifier.fillMaxHeight(0.02f))

            PasswordField(mapViewmodel, 1)

            Spacer(modifier = Modifier.fillMaxHeight(0.02f))

            Button(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .fillMaxHeight(0.08f)
                    .align(CenterHorizontally),
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(Color.Blue),
                onClick = {
                    tryLogin(mapViewmodel.loginMail,mapViewmodel.loginPasswd,mapViewmodel) } )
            { Text(text = "Login", fontSize = 15.sp, color = Color.White) }

            Spacer(modifier = Modifier.fillMaxHeight(0.02f))

            Button(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .fillMaxHeight(0.085f)
                    .align(CenterHorizontally),
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(Color.DarkGray),
                onClick = { navigationController.navigate(Routes.RegisterScreen.route) }
            ) { Text(text = "Register", fontSize = 15.sp, color = Color.White) }
        }
    }

    BackHandler(enabled = true) {
        if (!goToNext) {
            Toast.makeText(context, "Error: No se ha iniciado sesión", Toast.LENGTH_SHORT).show() }
    }

    if (goToNext) {
        navigationController.navigate(Routes.MapScreen.route)
        mapViewmodel.screenSelect("map")
    }
    if (mapViewmodel.registerError){
        ErrorDialog(mapViewmodel.registerError,error) { mapViewmodel.showError()} }

}


@Composable
fun PasswordField(mapViewmodel: MapViewmodel, fase: Int) {
    var passwordVisibility by remember { mutableStateOf(false) }

    //User password
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = if (fase == 1){ mapViewmodel.loginPasswd }
                else { mapViewmodel.loginPasswdConfirm },
        onValueChange = { if (fase == 1) { mapViewmodel.setPasswd(it) }
                          else mapViewmodel.setPasswdConfirm(it) },
        label = { if (fase == 1) { Text("Password") }
                  else { Text(text = "Confirm Password") }
                },
        maxLines = 1,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            val image = if (passwordVisibility) { Icons.Filled.VisibilityOff }
            else { Icons.Filled.Visibility }
            IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                Icon(imageVector = image, contentDescription = "Password visibility")
            }
                       },
        visualTransformation =
             if (passwordVisibility) { VisualTransformation.None }
             else { PasswordVisualTransformation() },

        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Magenta,
            unfocusedBorderColor = Color.Black )
    )
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
                Text(text = errorMessage, color = Color.Black)
            }
        }
    }
}

