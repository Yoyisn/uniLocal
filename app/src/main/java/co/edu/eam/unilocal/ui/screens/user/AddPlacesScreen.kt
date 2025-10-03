package co.edu.eam.unilocal.ui.screens.user

import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountBox
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Call
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.ExitToApp
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Phone
import androidx.compose.material.icons.rounded.Place
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import co.edu.eam.uniLocal_project.R
import co.edu.eam.unilocal.ui.components.DropdownMenu
import co.edu.eam.unilocal.ui.components.InputText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPlacesScreen(onNavigateToMyPlaces: () -> Unit) {
    var title by rememberSaveable { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf("") }
    var address by rememberSaveable { mutableStateOf("") }
    var phone by rememberSaveable { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var imageSelected by remember { mutableStateOf(false) }

    val cities = listOf("Bogotá", "Lima", "Caracas", "Quito", "Armenia")
    val categories = listOf("Restaurante", "Hotel", "Museo", "Parque")

    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Crear lugar") },
                navigationIcon = {
                    IconButton(onClick = { onNavigateToMyPlaces() }) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            // Información general
            Text(
                text = " --------------- General Information ------------------",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(bottom = 8.dp)
            )

            // Nombre del lugar
            InputText(
                value = title,
                label = "Nombre del lugar",
                supportingText = "Este campo es requerido",
                onValueChange = { title = it },
                onValidate = { it.isBlank() },
                icon = Icons.Rounded.Home
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Descripción multilinea
            InputText(
                value = description,
                label = "Descripción",
                supportingText = "Este campo es requerido",
                onValueChange = { description = it },
                onValidate = { it.isBlank() },
                icon = Icons.Rounded.ExitToApp,
                multiline = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Dropdown de ciudad
            DropdownMenu(
                supportingText = "Seleccione una ciudad",
                icon = Icons.Rounded.Place,
                label = "Seleccione la ciudad",
                list = cities,
                onValueChange = { city = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Dirección
            InputText(
                value = address,
                label = "Dirección",
                supportingText = "Debe ingresar al menos 10 caracteres",
                onValueChange = { address = it },
                onValidate = { it.length < 10 },
                icon = Icons.Rounded.LocationOn
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Dropdown de categoría
            DropdownMenu(
                supportingText = "Seleccione una categoría",
                icon = Icons.Rounded.Menu,
                label = "Seleccione la categoría",
                list = categories,
                onValueChange = { category = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Número de teléfono
            InputText(
                value = phone,
                label = "Número de teléfono",
                supportingText = "Debe tener al menos 10 dígitos",
                onValueChange = { phone = it },
                onValidate = { it.length < 10 },
                icon = Icons.Rounded.Phone
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Sección de imágenes
            Text(
                text = " ---------------------------- Images --------------------------",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(bottom = 8.dp)
            )

            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .border(
                        width = 2.dp,
                        color = if (imageSelected) MaterialTheme.colorScheme.primary else Color.LightGray,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .clickable {
                        imageSelected = !imageSelected
                        // Aquí puedes abrir galería o cámara
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Rounded.Search,
                    contentDescription = "Agregar imagen",
                    tint = if (imageSelected) MaterialTheme.colorScheme.primary else Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Botón de agregar lugar
            Button(
                onClick = {
                    val isValid = title.isNotBlank() &&
                            description.isNotBlank() &&
                            address.length >= 10 &&
                            phone.length >= 10 &&
                            city.isNotBlank() &&
                            category.isNotBlank()

                    if (isValid) {
                        Toast.makeText(context, "Lugar agregado correctamente", Toast.LENGTH_LONG).show()
                        onNavigateToMyPlaces()
                    } else {
                        Toast.makeText(context, "Por favor verifique los datos ingresados", Toast.LENGTH_LONG).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp), // Espacio final para evitar que el botón quede cortado
                shape = RoundedCornerShape(15.dp)
            ) {
                Icon(
                    imageVector = Icons.Rounded.Check,
                    contentDescription = "Agregar lugar"
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Agregar lugar")
            }
        }
    }
}


/*
@Composable
fun AddPlacesScreen(onNavigateToMyPlaces: () -> Unit) {

    var title by rememberSaveable { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf("") }
    var address by rememberSaveable { mutableStateOf("") }
    var phone by rememberSaveable { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var imageSelected by remember { mutableStateOf(false) }

    val cities = listOf("Bogotá", "Lima", "Caracas", "Quito", "Armenia")
    val categories = listOf("Restaurante", "Hotel", "Museo", "Parque")

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        Text(
            text = " --------------- General Information ------------------",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(bottom = 8.dp)
        )

        // Nombre del lugar
        InputText(
            value = title,
            label = "Nombre del lugar",
            supportingText = "Este campo es requerido",
            onValueChange = { title = it },
            onValidate = { it.isBlank() },
            icon = Icons.Rounded.Home
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Descripción multilinea
        InputText(
            value = description,
            label = "Descripción",
            supportingText = "Este campo es requerido",
            onValueChange = { description = it },
            onValidate = { it.isBlank() },
            icon = Icons.Rounded.ExitToApp,
            multiline = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Dropdown de ciudad
        DropdownMenu(
            supportingText = "Seleccione una ciudad",
            icon = Icons.Rounded.Place,
            label = "Seleccione la ciudad",
            list = cities,
            onValueChange = { city = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Dirección
        InputText(
            value = address,
            label = "Dirección",
            supportingText = "Debe ingresar al menos 10 caracteres",
            onValueChange = { address = it },
            onValidate = { it.length < 10 },
            icon = Icons.Rounded.LocationOn
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Dropdown de categoría
        DropdownMenu(
            supportingText = "Seleccione una categoría",
            icon = Icons.Rounded.Menu,
            label = "Seleccione la categoría",
            list = categories,
            onValueChange = { category = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Número de teléfono
        InputText(
            value = phone,
            label = "Número de teléfono",
            supportingText = "Debe tener al menos 10 dígitos",
            onValueChange = { phone = it },
            onValidate = { it.length < 10 },
            icon = Icons.Rounded.Phone
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Sección de imágenes
        Text(
            text = " ---------------------------- Images --------------------------",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(bottom = 8.dp)
        )

        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(RoundedCornerShape(12.dp))
                .border(
                    width = 2.dp,
                    color = if (imageSelected) colorScheme.primary else Color.LightGray,
                    shape = RoundedCornerShape(12.dp)
                )
                .clickable {
                    imageSelected = !imageSelected
                    // Aquí puedes abrir galería o cámara
                },
            contentAlignment = Alignment.Center
        ) {
            Spacer(modifier = Modifier.height(15.dp))
            Icon(
                imageVector = Icons.Rounded.Search,
                contentDescription = "Agregar imagen",
                tint = if (imageSelected) colorScheme.primary else Color.Gray
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Botón de agregar lugar
        Button(
            onClick = {
                val isValid = title.isNotBlank() &&
                        description.isNotBlank() &&
                        address.length >= 10 &&
                        phone.length >= 10 &&
                        city.isNotBlank() &&
                        category.isNotBlank()

                if (isValid) {
                    Toast.makeText(context, "Lugar agregado correctamente", Toast.LENGTH_LONG).show()
                    onNavigateToMyPlaces()
                } else {
                    Toast.makeText(context, "Por favor verifique los datos ingresados", Toast.LENGTH_LONG).show()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(15.dp)
        ) {
            Icon(
                imageVector = Icons.Rounded.Check,
                contentDescription = "Agregar lugar"
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Agregar lugar")
        }
    }
}
*/