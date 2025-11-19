package co.edu.eam.unilocal.ui.user.screens

import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
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
import co.edu.eam.unilocal.ui.navigation.LocalMainViewModel

@Composable
fun EditUserScreen(onNavigateToEditProfile: () -> Unit) {

    val usersViewModel = LocalMainViewModel.current.usersViewModel
    val currentUser by usersViewModel.currentUser.collectAsState()

    val context = LocalContext.current

    var registerName by rememberSaveable { mutableStateOf(currentUser?.name ?: "") }
    var phoneNumber by rememberSaveable { mutableStateOf(currentUser?.phoneNumber ?: "") }
    var email by rememberSaveable { mutableStateOf(currentUser?.email ?: "") }
    var city by rememberSaveable {
        mutableStateOf<DisplayableEnum>(currentUser?.city ?: City.ARMENIA)
    }

    val cities = City.entries
    val scrollState = rememberScrollState()

    Surface(modifier = Modifier.fillMaxSize()) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState)
                .padding(horizontal = 40.dp, vertical = 20.dp)
        ) {

            Spacer(modifier = Modifier.height(85.dp))

                    Image(
                        modifier = Modifier
                            .size(110.dp)
                            .clip(CircleShape),
                        painter = painterResource(R.drawable.profile),
                        contentScale = ContentScale.Crop,
                        contentDescription = stringResource(R.string.image_txtInfoScreen)
                    )

            Spacer(modifier = Modifier.height(35.dp))

            InputText(
                value = registerName,
                label = stringResource(R.string.txt_registerName),
                supportingText = stringResource(R.string.txt_registerNameError),
                onValueChange = { registerName = it },
                onValidate = { registerName.isBlank() },
                icon = Icons.Rounded.Person
            )

            InputText(
                value = phoneNumber,
                label = stringResource(R.string.txt_phoneNumber),
                supportingText = stringResource(R.string.txt_phoneNumberError),
                onValueChange = { phoneNumber = it },
                onValidate = { phoneNumber.isBlank() || phoneNumber.length < 10 },
                icon = Icons.Rounded.Call
            )

            InputText(
                value = email,
                label = stringResource(R.string.txt_email),
                supportingText = stringResource(R.string.txt_email_error),
                onValueChange = { email = it },
                onValidate = {
                    email.isBlank() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()
                },
                icon = Icons.Rounded.Email
            )

            DropdownMenu(
                supportingText = stringResource(R.string.txt_cityError),
                icon = Icons.Rounded.Place,
                label = stringResource(R.string.txt_city),
                list = cities,
                onValueChange = { newVal -> city = newVal }
            )

            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick = {
                    val isValid =
                        registerName.isNotBlank() &&
                                phoneNumber.length >= 10 &&
                                email.isNotBlank() &&
                                Patterns.EMAIL_ADDRESS.matcher(email).matches()

                    if (isValid) {
                        val updatedUser = currentUser?.copy(
                            name = registerName,
                            phoneNumber = phoneNumber,
                            email = email,
                            city = city as City
                        )

                        usersViewModel.updateUser(updatedUser)

                        Toast.makeText(context, "Edited successfully", Toast.LENGTH_LONG).show()

                        onNavigateToEditProfile()

                    } else {
                        Toast.makeText(
                            context,
                            "Something is wrong in your data, check it",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Rounded.Check,
                    contentDescription = stringResource(R.string.btn_editUser)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = stringResource(R.string.btn_editUser))
            }

            Spacer(modifier = Modifier.height(50.dp))
        }
    }
}