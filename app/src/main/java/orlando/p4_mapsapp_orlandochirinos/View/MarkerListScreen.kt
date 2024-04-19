package orlando.p4_mapsapp_orlandochirinos.View

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.google.android.gms.maps.model.LatLng
import orlando.p4_mapsapp_orlandochirinos.ModelView.MapViewmodel
import orlando.p4_mapsapp_orlandochirinos.Models.Ubicacion
import orlando.p4_mapsapp_orlandochirinos.Models.filterMarker
import orlando.trivial.orlandochirinos_apilistapp.Navigation.Routes


@Composable
fun MarkerListScreen(mapViewmodel: MapViewmodel, navigationController: NavHostController) {
    val availableLocations: List<Ubicacion> by mapViewmodel.firestoreAvailableLocations.observeAsState(listOf())

    Box(modifier = Modifier.padding(5.dp) ) {
        Column {

            Row {
                Button(
                    modifier = Modifier.fillMaxWidth(0.4f),
                    shape = RectangleShape,
                    colors = ButtonDefaults.buttonColors(Color.Magenta),
                    onClick = { mapViewmodel.clearTag() })
                { Text(text = "CLEAR TAGS") }
                Spacer(modifier = Modifier.padding(5.dp))
                SelectCategories(mapViewModel = mapViewmodel)
            }

            Spacer(modifier = Modifier.size(10.dp))

            if (mapViewmodel.tagSelected != "") { mapViewmodel.getUbicationsFiltered(mapViewmodel.tagSelected) }
            else { mapViewmodel.getAllUbications() }

            LazyColumn(verticalArrangement = Arrangement.spacedBy(5.dp)) {
                items(availableLocations) { location ->
                    LocationItem(location, mapViewmodel, navigationController)
                }
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun LocationItem(location: Ubicacion, mapViewmodel: MapViewmodel, navigationController: NavHostController) {
    Card( border = BorderStroke(2.dp, Color.Black),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 5.dp)
            .clickable {
                //  navigationController.navigate(Routes.MapScreen.spawnOnPosition(location.position))
                mapViewmodel.screenSelect(mapViewmodel.screenList[1])
                mapViewmodel.changePosition(LatLng(location.latitud, location.longitud))
                navigationController.navigate(Routes.MapScreen.route)
            }
    )
    {
        Row {
            //TENGO IMAGEN QUE MOSTRAR.

            GlideImage(
                model = location.image,
                contentDescription = "Image from storage",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth(0.25f)
            )

            Box(modifier = Modifier.fillMaxWidth(0.80f)) {
                Column(modifier = Modifier.padding(start = 5.dp, bottom = 10.dp)) {
                    Text(
                        text = location.ubicationName,
                        fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 5.dp)
                    )
                    Text(text = "Descripción: ${location.snippet}\n" +
                                "Etiqueta: ${location.tag}" )
                }
            }
            Column( modifier = Modifier.align(CenterVertically) ) {
                Icon(
                    modifier = Modifier
                        .size(30.dp)
                        .clickable { } ,
                    imageVector = Icons.Filled.Edit, contentDescription = "EDIT" )

                Spacer(modifier = Modifier.size(10.dp))

                Icon(
                    modifier = Modifier
                        .size(30.dp)
                        .clickable {
                            location.ubicationId?.let { mapViewmodel.deleteUbication(it) }
                            filterMarker(mapViewmodel)
                                   } ,
                    imageVector = Icons.Filled.DeleteForever, contentDescription = "DELETE" )
            }
        }
    }
}