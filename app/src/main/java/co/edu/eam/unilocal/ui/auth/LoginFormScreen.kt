package co.edu.eam.unilocal.ui.auth

import android.util.Patterns
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.res.stringResource
import co.edu.eam.uniLocal_project.R
import co.edu.eam.unilocal.model.Role
import co.edu.eam.unilocal.ui.components.InputText
import co.edu.eam.unilocal.ui.components.OperationResultHandler
import co.edu.eam.unilocal.ui.navigation.LocalMainViewModel

@Composable
fun LoginFormScreen ( onNavigateToHome: (String, Role) -> Unit ) {

    val usersViewModel = LocalMainViewModel.current.usersViewModel
    val userResult by usersViewModel.userResult.collectAsState()

    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current

    Surface {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
            modifier = Modifier
                .fillMaxSize()
                .padding(50.dp)
        ) {
            Image(
                modifier = Modifier.width(150.dp),
                painter = painterResource(R.drawable.unilocallogo),
                contentDescription = stringResource(R.string.image_txtInfoScreen)
            )//End Image

            InputText(
                value = email,
                label = stringResource( R.string.txt_email ),
                supportingText = stringResource(R.string.txt_email_error),
                onValueChange = { email = it },
                onValidate = { email.isBlank() || !Patterns.EMAIL_ADDRESS.matcher(email).matches() },
                icon = Icons.Rounded.Email
            )//End InputText

            InputText(
                value = password,
                isPassword = true,
                label = stringResource( R.string.txt_password ),
                supportingText = stringResource(R.string.txt_password_error),
                onValueChange = { password = it },
                onValidate = { password.isBlank() || password.length < 5 },
                icon = Icons.Rounded.KeyboardArrowRight
            )//InputText

            Button(
                onClick = {
                    usersViewModel.login(email, password)
                },
                //enabled = !isEmailError && !isPasswordError,
                content = {
                    Icon(
                        imageVector = Icons.Outlined.Lock,
                        contentDescription = stringResource( R.string.btn_logIn )
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text( text = stringResource( R.string.btn_logIn ) )
                }
            )//End button

            OperationResultHandler(
                result = userResult,
                onSuccess = {
                    onNavigateToHome(usersViewModel.currentUser.value!!.id, usersViewModel.currentUser.value!!.role)
                    usersViewModel.resetOperationResult()
                },
                onFailure = { usersViewModel.resetOperationResult() }
            )

        }
    }
}