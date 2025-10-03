package co.edu.eam.unilocal.ui.screens.user.tabs

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import co.edu.eam.unilocal.viewModel.PlacesViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Modifier
import co.edu.eam.unilocal.ui.components.PlaceCard


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Places(
    onNavigateToPlaceDetail: (String) -> Unit,
    padding: PaddingValues,
    placesViewModel: PlacesViewModel
) {

    val places by placesViewModel.places.collectAsState()

    LazyColumn(
        modifier = Modifier.padding(padding)
    ) {
        items(places) { place ->
            PlaceCard(
                place = place,
                onClick = { id -> onNavigateToPlaceDetail(place.id) }
            )
        }//ENd items
    } // End fun Places
}//End places