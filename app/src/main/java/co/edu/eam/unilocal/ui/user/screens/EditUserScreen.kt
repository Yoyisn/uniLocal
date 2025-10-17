package co.edu.eam.unilocal.ui.user.screens

import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import co.edu.eam.uniLocal_project.R
import co.edu.eam.unilocal.model.City
import co.edu.eam.unilocal.model.DisplayableEnum
import co.edu.eam.unilocal.ui.components.DropdownMenu
import co.edu.eam.unilocal.ui.components.InputText

@Composable
fun EditUserScreen( onNavigateToEditProfile: () -> Unit ) {

    var registerName by rememberSaveable { mutableStateOf("") }
    var phoneNumber by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var userName by rememberSaveable { mutableStateOf("") }

    var country by remember { mutableStateOf("") }
    //var city by remember { mutableStateOf("") }

    var city by remember { mutableStateOf<DisplayableEnum>(City.ARMENIA) }
    val cities = City.entries

    val countries = listOf("Colombia", "Peru", "Venezuela","Ecuador")
    //val cities = listOf("Bogota", "Lima", "Caracas","Quito")

    val context = LocalContext.current

    Surface {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(3.dp, Alignment.Top),
            modifier = Modifier
                .fillMaxSize()
                .padding(50.dp)
        ){
            Image(
                modifier = Modifier.width(150.dp)
                                    .clip(CircleShape),
                painter = painterResource(R.drawable.profile),
                contentScale = ContentScale.Crop,
                contentDescription = stringResource(R.string.image_txtInfoScreen)
            )//End Image

            InputText(
                value = registerName,
                label = stringResource( R.string.txt_registerName ),
                supportingText = stringResource(R.string.txt_registerNameError),
                onValueChange = { registerName = it },
                onValidate = { registerName.isBlank() },
                icon = Icons.Rounded.Person
            )//End InputText

            InputText(
                value = userName,
                label = stringResource( R.string.txt_userName ),
                supportingText = stringResource(R.string.txt_registerNameError),
                onValueChange = { userName = it },
                onValidate = { userName.isBlank() },
                icon = Icons.Rounded.AccountBox
            )//End InputText

            //DropdownMenu( label = stringResource( R.string.text_country ), list =  countries, onValueChange = {country = it} )
            DropdownMenu( supportingText = stringResource(R.string.txt_cityError), icon = Icons.Rounded.Place, label = stringResource( R.string.txt_city ), list =  cities, onValueChange = {city = it} )

            InputText(
                value = phoneNumber,
                label = stringResource( R.string.txt_phoneNumber ),
                supportingText = stringResource(R.string.txt_phoneNumberError),
                onValueChange = { phoneNumber = it },
                onValidate = { phoneNumber.isBlank() || phoneNumber.length < 10 },
                icon = Icons.Rounded.Call
            )//End InputText

            InputText(
                value = email,
                label = stringResource( R.string.txt_email ),
                supportingText = stringResource(R.string.txt_email_error),
                onValueChange = { email = it },
                onValidate = { email.isBlank() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()},
                icon = Icons.Rounded.Email
            )//End InputText

            Button (
                onClick = {
                    val isValid = registerName.isNotBlank() &&
                            phoneNumber.length >= 10 &&
                            email.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
                    if (isValid) {
                        Toast.makeText(context, "Edited succesfully", Toast.LENGTH_LONG).show()
                        onNavigateToEditProfile()
                    } else {
                        Toast.makeText(context, "Something is wrong in your data, check it", Toast.LENGTH_LONG).show()
                    }
                }
            ){
                Icon (
                    imageVector = Icons.Rounded.Check,
                    contentDescription = stringResource( R.string.btn_editUser )
                )
                Text (text = stringResource( R.string.btn_editUser ))
            }

        }//End column
    }
}