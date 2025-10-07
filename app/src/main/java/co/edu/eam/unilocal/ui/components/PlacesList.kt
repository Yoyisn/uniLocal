package co.edu.eam.unilocal.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import co.edu.eam.unilocal.model.Place

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlacesList (
    onNavigateToPlaceDetail: (String) -> Unit,
    padding: PaddingValues,
    //placesViewModel: PlacesViewModel
    places: List<Place>
) {

    Scaffold/*(
        topBar = {
            TopAppBar(
                title = { Text("My places") }
            )
        }
    )*/ { padding ->
        LazyColumn(
            modifier = Modifier.padding(padding),
        ){
            items(places) { place ->
                PlaceCard(
                    place = place,
                    onClick = { onNavigateToPlaceDetail(place.id) }
                )
            }
        }
    }

}