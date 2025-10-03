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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Modifier
import co.edu.eam.unilocal.ui.components.PlaceCard

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Places(
    onNavigateToPlaceDetail: (String) -> Unit,
    padding: PaddingValues,
    placesViewModel: PlacesViewModel
) {
    val places by placesViewModel.places.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My places") }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.padding(padding)
        ) {
            items(places) { place ->
                PlaceCard(
                    place = place,
                    onClick = { onNavigateToPlaceDetail(place.id) }
                )
            }
        }
    }
}