package co.edu.eam.unilocal.ui.user.screens

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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import co.edu.eam.uniLocal_project.R
import co.edu.eam.unilocal.ui.navigation.LocalMainViewModel

@Composable
fun Profile(
    onNavigateToEditProfile: () -> Unit
) {
    val usersViewModel = LocalMainViewModel.current.usersViewModel
    val currentUser = usersViewModel.currentUser.collectAsState().value

    val context = LocalContext.current

    val defaultProfileImage = R.drawable.profile

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = 120.dp,
                start = 20.dp,
                end = 20.dp,
                bottom = 20.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(defaultProfileImage)
                .crossfade(true)
                .size(300)
                .build(),
            contentDescription = "Foto de Perfil",
            placeholder = painterResource(R.drawable.profile),
            error = painterResource(R.drawable.profile),
            modifier = Modifier
                .size(140.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(25.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {

            Column(
                modifier = Modifier.padding(22.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {


                Text(
                    text = currentUser?.name ?: "Usuario sin nombre",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )


                Text(
                    text = "Correo: ${currentUser?.email ?: "Sin correo"}",
                    style = MaterialTheme.typography.bodyLarge
                )


                Text(
                    text = "Teléfono: ${currentUser?.phoneNumber ?: "Sin teléfono"}",
                    style = MaterialTheme.typography.bodyLarge
                )

                Text(
                    text = "Rol: ${currentUser?.role?.name ?: "Sin rol"}",
                    style = MaterialTheme.typography.bodyLarge
                )

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