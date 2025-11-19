package co.edu.eam.unilocal.ui.auth

import android.util.Patterns
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import co.edu.eam.uniLocal_project.R
import co.edu.eam.unilocal.model.Role
import co.edu.eam.unilocal.model.User
import co.edu.eam.unilocal.ui.components.InputText
import co.edu.eam.unilocal.ui.components.OperationResultHandler
import co.edu.eam.unilocal.ui.navigation.LocalMainViewModel
import co.edu.eam.unilocal.utils.RequestResult

@Composable
fun LoginFormScreen(onNavigateToHome: (String, Role) -> Unit) {

    val usersViewModel = LocalMainViewModel.current.usersViewModel

    val userResult: RequestResult<User>? by usersViewModel.userResult.collectAsState(initial = null)

    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    Surface {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.fillMaxSize().padding(50.dp)
        ) {

            Image(
                modifier = Modifier.width(150.dp),
                painter = painterResource(R.drawable.unilocallogo),
                contentDescription = null
            )

            InputText(
                value = email,
                label = "Email",
                supportingText = "Formato inválido",
                onValueChange = { email = it },
                onValidate = { email.isBlank() || !Patterns.EMAIL_ADDRESS.matcher(email).matches() },
                icon = Icons.Rounded.Email
            )

            InputText(
                value = password,
                isPassword = true,
                label = "Contraseña",
                supportingText = "Debe tener mínimo 5 caracteres",
                onValueChange = { password = it },
                onValidate = { password.isBlank() || password.length < 5 },
                icon = Icons.Rounded.KeyboardArrowRight
            )

            Button(onClick = { usersViewModel.login(email, password) }) {
                Icon(imageVector = Icons.Outlined.Lock, contentDescription = null)
                Spacer(Modifier.width(5.dp))
                Text("Ingresar")
            }

            OperationResultHandler(
                result = userResult,
                onSuccess = { user ->
                    onNavigateToHome(user.id, user.role)
                },
                onFailure = { }
            )
        }
    }
}