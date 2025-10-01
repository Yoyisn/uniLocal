package co.edu.eam.unilocal.ui.screens.user.tabs

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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

@OptIn( ExperimentalMaterial3Api::class )
@Composable
fun Search ( padding: PaddingValues) {
    var expanded by rememberSaveable { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxWidth()
            .padding( padding )
    ) {
        SearchBar(
            modifier = Modifier.align(Alignment.TopCenter),
            inputField = {
                SearchBarDefaults.InputField (
                    query = "",
                    onQueryChange = {},
                    onSearch = {
                        ""
                        expanded = false
                    },
                    expanded = expanded,
                    onExpandedChange = { expanded = it },
                    placeholder = { Text ( text = "search" ) }
                )
            },
            expanded = expanded,
            onExpandedChange = { expanded = it }
        ) {}//End search bar
    }//End box
}//End fun search