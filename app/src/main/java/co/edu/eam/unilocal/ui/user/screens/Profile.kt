package co.edu.eam.unilocal.ui.user.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import co.edu.eam.uniLocal_project.R
import co.edu.eam.unilocal.ui.navigation.LocalMainViewModel

@Composable
fun Profile(
    onNavigateToEditProfile: () -> Unit
) {
    val usersViewModel = LocalMainViewModel.current.usersViewModel
    val currentUser = usersViewModel.currentUser.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = 150.dp,   // 👈 ESTE ES EL FIX PARA QUE NO SE CORTE LA IMAGEN
                start = 20.dp,
                end = 20.dp,
                bottom = 20.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        // *** FOTO DE PERFIL DEFAULT ***
        Image(
            painter = painterResource(id = R.drawable.profile),
            contentDescription = "Foto de Perfil",
            modifier = Modifier
                .size(130.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.height(20.dp))

        // *** CARD CON INFORMACIÓN DEL USUARIO ***
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {

            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                // NOMBRE
                Text(
                    text = currentUser?.name ?: "Usuario sin nombre",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                // EMAIL
                Text(
                    text = "Correo: ${currentUser?.email ?: "Sin correo"}",
                    style = MaterialTheme.typography.bodyLarge
                )

                // TELÉFONO
                Text(
                    text = "Teléfono: ${currentUser?.phoneNumber ?: "Sin teléfono"}",
                    style = MaterialTheme.typography.bodyLarge
                )

                // ROL
                Text(
                    text = "Rol: ${currentUser?.role?.name ?: "Sin rol"}",
                    style = MaterialTheme.typography.bodyLarge
                )

                // CIUDAD FORMATEADA
                val cityFormatted = currentUser?.city?.name
                    ?.lowercase()
                    ?.replaceFirstChar { it.uppercase() }
                    ?: "Ciudad no registrada"

                Text(
                    text = "Ciudad: $cityFormatted",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        Button(onClick = onNavigateToEditProfile) {
            Text(text = "Editar Perfil")
        }
    }
}