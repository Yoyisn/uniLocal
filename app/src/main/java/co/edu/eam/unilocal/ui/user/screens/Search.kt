package co.edu.eam.unilocal.ui.user.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import co.edu.eam.unilocal.ui.navigation.LocalMainViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import co.edu.eam.unilocal.ui.components.PlacesList


@RequiresApi(Build.VERSION_CODES.O)
@OptIn( ExperimentalMaterial3Api::class )
@Composable
fun Search (
    padding: PaddingValues,
    onNavigateToPlaceDetail: (String) -> Unit
) {

    val placesViewModel = LocalMainViewModel.current.placesViewModel

    var query by rememberSaveable { mutableStateOf("") }
    var expanded by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SearchBar(
            inputField = {
                SearchBarDefaults.InputField (
                    query = query,
                    onQueryChange = { query = it },
                    onSearch = {
                        expanded = false
                    },
                    expanded = expanded,
                    onExpandedChange = { expanded = it },
                    placeholder = { Text ( text = "search" ) }
                )
            },
            expanded = expanded,
            onExpandedChange = { expanded = it }
        ) {
            if( query.isNotEmpty() ) {
                val places = placesViewModel.findByName(query)
                LazyColumn{
                    items( places ) {
                        Text(
                            modifier = Modifier.padding(5.dp)
                                .clickable{
                                    query = it.title
                                    expanded = false
                                },
                            text = it.title
                        )
                        HorizontalDivider()
                    }
                }
            }
        }//End search bar

        if (query.isNotEmpty()) {
            val places = placesViewModel.findByName(query)
            PlacesList(
                onNavigateToPlaceDetail = onNavigateToPlaceDetail,
                padding = padding,
                places = places
            )
        } else {
            Box( modifier = Modifier.fillMaxSize() ) {
                Text ( text = "No results", modifier = Modifier.align(Alignment.Center) )
            }
        }

    }//End Column
}//End fun search