package co.edu.eam.unilocal.ui.screens.user

import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountBox
import androidx.compose.material.icons.rounded.Call
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Place
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import co.edu.eam.uniLocal_project.R
import co.edu.eam.unilocal.ui.components.DropdownMenu
import co.edu.eam.unilocal.ui.components.InputText

@Composable
fun AddPlacesScreen ( onNavigateToMyPlaces: () -> Unit ) {

    var title by rememberSaveable { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf("") }
    var address by rememberSaveable { mutableStateOf("") }
    var phone by rememberSaveable { mutableStateOf("") }
    var city by remember { mutableStateOf("") }

    val cities = listOf("Bogota", "Lima", "Caracas","Quito")

    val context = LocalContext.current

    Surface {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(-10.dp, Alignment.Top),
            modifier = Modifier
                .fillMaxSize()
                .padding(50.dp)
        ){
            InputText(
                value = title,
                label = stringResource( R.string.txt_titlePlace),
                supportingText = stringResource(R.string.txt_titlePlace),
                onValueChange = { title= it },
                onValidate = { title.isBlank() },
                icon = Icons.Rounded.Person
            )//End InputText

            InputText(
                value = description,
                label = stringResource( R.string.txt_descriptionAddPlaces),
                supportingText = stringResource(R.string.txt_descriptionAddPlaces),
                onValueChange = { description = it },
                onValidate = { description.isBlank() },
                icon = Icons.Rounded.Person
            )//End InputText

            DropdownMenu( supportingText = stringResource(R.string.txt_cityError), icon = Icons.Rounded.Place, label = stringResource( R.string.txt_city ), list =  cities, onValueChange = {city = it} )

            InputText(
                value = address,
                label = stringResource( R.string.txt_address),
                supportingText = stringResource(R.string.txt_address),
                onValueChange = { address = it },
                onValidate = { address.isBlank() },
                icon = Icons.Rounded.Person
            )//End InputText

            InputText(
                value = phone,
                label = stringResource( R.string.txt_phoneNumberPlace),
                supportingText = stringResource(R.string.txt_phoneNumberPlace),
                onValueChange = { phone = it },
                onValidate = { phone.isBlank() },
                icon = Icons.Rounded.Person
            )//End InputText

            Button (
                onClick = {
                    val isValid = title.isNotBlank() &&
                            description.isNotBlank() &&
                            address.length >= 10 &&
                            phone.isNotBlank() && phone.length >= 10
                    if (isValid) {
                        Toast.makeText(context, "One more place added", Toast.LENGTH_LONG).show()
                        onNavigateToMyPlaces()
                    } else {
                        Toast.makeText(context, "Something is wrong in your data, check it", Toast.LENGTH_LONG).show()
                    }
                }
            ){
                Icon (
                    imageVector = Icons.Rounded.Check,
                    contentDescription = stringResource( R.string.btn_addPlace )
                )
                Text (text = stringResource( R.string.btn_addPlace ))
            }

        }//End column
    }
}