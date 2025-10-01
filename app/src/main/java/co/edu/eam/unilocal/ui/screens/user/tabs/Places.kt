package co.edu.eam.unilocal.ui.screens.user.tabs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import co.edu.eam.unilocal.viewModel.PlacesViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun Places(onNavigateToPlaceDetail: (String) -> Unit, padding: PaddingValues, placesViewModel: PlacesViewModel ) {

    val places by placesViewModel.places.collectAsState()

    LazyColumn (
        modifier = Modifier.padding( padding )
    ) {
        items ( places ) {

            ListItem (
                modifier = Modifier
                    .clip (MaterialTheme.shapes.small)
                    .clickable { onNavigateToPlaceDetail(it.id) },
                headlineContent = { Text( text = it.title) },
                supportingContent = { Text( text = it.description) },
                //leadingContent = {}, //Image at left
                trailingContent = {
                    AsyncImage (
                        modifier = Modifier
                            .width(100.dp)
                            .height(100.dp)
                            .clip(RoundedCornerShape(16.dp)),
                        model = it.images[0],
                        contentDescription = it.title,
                        contentScale = ContentScale.Crop
                    )
                } //Image at right

            )//End ListItem
        }//End items
    }//End LazyColumn
}//End fun Places