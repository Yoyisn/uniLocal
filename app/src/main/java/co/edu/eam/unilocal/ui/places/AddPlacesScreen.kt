package co.edu.eam.unilocal.ui.places

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.ExitToApp
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Phone
import androidx.compose.material.icons.rounded.Place
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import co.edu.eam.uniLocal_project.R
import co.edu.eam.unilocal.model.City
import co.edu.eam.unilocal.model.DayOfWeek
import co.edu.eam.unilocal.model.DisplayableEnum
import co.edu.eam.unilocal.model.Location
import co.edu.eam.unilocal.model.Place
import co.edu.eam.unilocal.model.PlaceType
import co.edu.eam.unilocal.model.Schedule
import co.edu.eam.unilocal.ui.components.DropdownMenu
import co.edu.eam.unilocal.ui.components.InputText
import co.edu.eam.unilocal.ui.components.MapBox
import co.edu.eam.unilocal.ui.navigation.LocalMainViewModel
import com.mapbox.geojson.Point
import java.time.LocalTime
import java.util.UUID
import kotlin.enums.EnumEntries

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPlacesScreen( userId: String?, onNavigateBackTo: () -> Unit ) {

    var clickedPoint by rememberSaveable { mutableStateOf<Point?>(null) }

    var title by rememberSaveable { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf("") }
    var address by rememberSaveable { mutableStateOf("") }
    var phone by rememberSaveable { mutableStateOf("") }

    var city by remember { mutableStateOf<DisplayableEnum>(City.ARMENIA) }
    val cities = City.entries

    var type by remember { mutableStateOf<DisplayableEnum>(PlaceType.RESTAURANT) }
    val types = PlaceType.entries

    val placesViewModel = LocalMainViewModel.current.placesViewModel

    val schedule = remember { mutableStateListOf(
        Schedule(DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(18, 0))
    ) }

    //var category by remember { mutableStateOf("") }
    var imageSelected by remember { mutableStateOf(false) }
    //val cities = listOf("Bogotá", "Lima", "Caracas", "Quito", "Armenia")
    //val categories = listOf("Restaurante", "Hotel", "Museo", "Parque")

    val context = LocalContext.current

    var showExitDialog by remember { mutableStateOf(false) }

    BackHandler (
        enabled = !showExitDialog
    ){
        showExitDialog = true
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Create Place") },
                navigationIcon = {
                    IconButton(onClick = { showExitDialog = true }) {
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

            Image(
                modifier = Modifier.width(150.dp),
                painter = painterResource(R.drawable.unilocallogo),
                contentDescription = stringResource(R.string.image_txtInfoScreen)
            )//End Image

            Text(
                text = " --------------- General Information ------------------",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(bottom = 8.dp)
            )

            InputText(
                value = title,
                label = "Nombre del lugar",
                supportingText = "Este campo es requerido",
                onValueChange = { title = it },
                onValidate = { it.isBlank() },
                icon = Icons.Rounded.Home
            )

            Spacer(modifier = Modifier.height(16.dp))

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

            DropdownMenu(
                supportingText = "Seleccione una ciudad",
                icon = Icons.Rounded.Place,
                label = "Seleccione la ciudad",
                list = cities,
                onValueChange = { city = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            InputText(
                value = address,
                label = "Dirección",
                supportingText = "Debe ingresar al menos 10 caracteres",
                onValueChange = { address = it },
                onValidate = { it.length < 10 },
                icon = Icons.Rounded.LocationOn
            )

            Spacer(modifier = Modifier.height(16.dp))

            DropdownMenu(
                supportingText = "Seleccione una categoría",
                icon = Icons.Rounded.Menu,
                label = "Seleccione la categoría",
                list = types,
                onValueChange = { type = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            InputText(
                value = phone,
                label = "Número de teléfono",
                supportingText = "Debe tener al menos 10 dígitos",
                onValueChange = { phone = it },
                onValidate = { it.length < 10 },
                icon = Icons.Rounded.Phone
            )

            Spacer(modifier = Modifier.height(32.dp))

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

            MapBox (
                modifier = Modifier.fillMaxWidth()
                    .height(400.dp),
                activateClick = true,
                onMapClickListener = { l ->
                    clickedPoint = l
                }
            )

            Button(
                onClick = {
                    val isValid = title.isNotBlank() && description.isNotBlank() && address.length >= 10 && phone.length >= 10 &&
                            city.displayName.isNotBlank() && type.displayName.isNotBlank()

                    if (isValid && clickedPoint != null) {
                        Toast.makeText(context, "Lugar agregado correctamente", Toast.LENGTH_LONG).show()
                        val place = Place(
                            id = UUID.randomUUID().toString(),
                            title = title,
                            description = description,
                            address = address,
                            city = city as City,
                            location = Location(clickedPoint!!.latitude(), clickedPoint!!.longitude()),
                            images = listOf(),
                            phones = listOf(phone),
                            type = type as PlaceType,
                            schedules = schedule,
                            ownerId = userId ?: ""
                        )
                        placesViewModel.create(place)
                        onNavigateBackTo()
                    } else {
                        Toast.makeText(context, "Por favor verifique los datos ingresados, no pueden estar vacios", Toast.LENGTH_LONG).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp),
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
    }//End Scaffold

    if(showExitDialog) {
        AlertDialog(
            title = { Text( text = "Do you want to leave?" ) },
            text = { Text( text = "If you leave will lose all changes") },
            onDismissRequest = { showExitDialog = false },
            confirmButton = {
                Button(
                    onClick = {
                        showExitDialog = false
                        onNavigateBackTo()
                    }
                ) {
                    Text(text = "Sure")
                }
            },
            dismissButton = {
                Button(
                    onClick = { showExitDialog = false }
                ) {
                    Text(text = "Close")
                }
            }
        )
    }//End if

}//End fun AddPlacesScreen