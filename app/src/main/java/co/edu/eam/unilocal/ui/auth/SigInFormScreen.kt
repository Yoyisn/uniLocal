package co.edu.eam.unilocal.ui.auth

import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Call
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Place
import androidx.compose.material3.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import co.edu.eam.uniLocal_project.R
import co.edu.eam.unilocal.model.Role
import co.edu.eam.unilocal.model.User
import co.edu.eam.unilocal.ui.components.DropdownMenu
import co.edu.eam.unilocal.ui.components.InputText
import co.edu.eam.unilocal.ui.navigation.LocalMainViewModel
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun SigInFormScreen ( onNavigateToHome: () -> Unit ) {

    val usersViewModel = LocalMainViewModel.current.usersViewModel

    var registerName by rememberSaveable { mutableStateOf("") }
    var phoneNumber by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }

    var country by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }

    val countries = listOf("Colombia", "Peru", "Venezuela","Ecuador")
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
            Image(
                modifier = Modifier.width(150.dp),
                painter = painterResource(R.drawable.unilocallogo),
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

            InputText(
                value = password,
                label = stringResource( R.string.txt_password ),
                supportingText = stringResource(R.string.txt_password_error),
                onValueChange = { password = it },
                onValidate = { password.isBlank() || password.length < 5 },
                icon = Icons.Rounded.Lock
            )//End InputText

            InputText(
                value = confirmPassword,
                label = stringResource( R.string.txt_confirmPassword ),
                supportingText = stringResource(R.string.txt_confirmPasswordError),
                onValueChange = { confirmPassword = it },
                onValidate = { confirmPassword.isBlank() },
                icon = Icons.Rounded.Check
            )//End InputText

            Button (
                onClick = {
                    val isValid = registerName.isNotBlank() &&
                            city.isNotBlank() &&
                            phoneNumber.length >= 10 &&
                            email.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(email).matches() &&
                            password.length >= 5 &&
                            confirmPassword == password

                    if (isValid) {
                        val user = User(
                        id = UUID.randomUUID().toString(),
                        name = registerName,
                        city = city,
                        phoneNumber = phoneNumber,
                        email = email,
                        role = Role.USER,
                        password = password
                        )
                        usersViewModel.create(user)
                        onNavigateToHome()
                        Toast.makeText(context, "Successfully registered", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(context, "Something is wrong in your data, check it", Toast.LENGTH_LONG).show()
                    }
                }//End onClick
            ){
                Icon (
                    imageVector = Icons.Rounded.Check,
                    contentDescription = stringResource( R.string.txt_sigIn )
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text( text = stringResource( R.string.txt_sigIn ) )
            }//End button

        }//End column
    }//End surface
}//End fun SigInFormScreen