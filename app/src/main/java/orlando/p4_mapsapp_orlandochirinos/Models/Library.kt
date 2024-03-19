package orlando.p4_mapsapp_orlandochirinos.Models

import com.google.android.gms.maps.model.LatLng
import orlando.p4_mapsapp_orlandochirinos.ModelView.MapViewmodel

fun tryAddNewLocation(
    mapViewModel: MapViewmodel,
    selectedLocation: LatLng?,
    nameOfPlace: String,
    description: String ) {
    if (selectedLocation != null && nameOfPlace.length > 1 &&
        mapViewModel.tagSelected in mapViewModel.tagList) {
        mapViewModel.addLocation(
            Ubicacion(
                nameOfPlace,
                description,
                selectedLocation,
                mapViewModel.tagSelected )
        )
    }
}