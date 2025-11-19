package co.edu.eam.unilocal.ui.places

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import co.edu.eam.uniLocal_project.R
import co.edu.eam.unilocal.model.*
import co.edu.eam.unilocal.ui.components.DropdownMenu
import co.edu.eam.unilocal.ui.components.InputText
import co.edu.eam.unilocal.ui.components.MapBox
import co.edu.eam.unilocal.ui.components.OperationResultHandler
import co.edu.eam.unilocal.ui.navigation.LocalMainViewModel
import coil.compose.AsyncImage
import com.cloudinary.Cloudinary
import com.cloudinary.utils.ObjectUtils
import com.mapbox.geojson.Point
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPlacesScreen(
    userId: String?,
    onNavigateBackTo: () -> Unit,
    context: Context
) {

    val placesViewModel = LocalMainViewModel.current.placesViewModel
    val placeResult by placesViewModel.placeResult.collectAsState()

    var clickedPoint by rememberSaveable { mutableStateOf<Point?>(null) }

    var title by rememberSaveable { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf("") }
    var address by rememberSaveable { mutableStateOf("") }
    var phone by rememberSaveable { mutableStateOf("") }

    var city by remember { mutableStateOf<DisplayableEnum>(City.ARMENIA) }
    val cities = City.entries

    var image by remember { mutableStateOf("") }
    var imageSelected by remember { mutableStateOf(false) }

    val config = mapOf(
        "cloud_name" to "djgr3sbwf",
        "api_key" to "912981869431355",
        "api_secret" to "RCtRXZktFsQuJRv3RN714XuXoyY"
    )

    val scope = rememberCoroutineScope()
    val cloudinary = Cloudinary(config)

    val fileLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                scope.launch(Dispatchers.IO) {
                    val stream = context.contentResolver.openInputStream(uri)
                    stream?.use { s ->
                        val result = cloudinary.uploader().upload(s, ObjectUtils.emptyMap())
                        image = result["secure_url"].toString()
                    }
                }
            }
        }

    val permissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) {
                fileLauncher.launch("image/*")
            } else {
                Toast.makeText(context, "Permiso denegado", Toast.LENGTH_SHORT).show()
            }
        }

    var type by remember { mutableStateOf<DisplayableEnum>(PlaceType.RESTAURANT) }
    val types = PlaceType.entries

    val schedule = remember {
        mutableStateListOf(Schedule(DayOfWeek.MONDAY, Date(), Date()))
    }

    var showExitDialog by remember { mutableStateOf(false) }

    BackHandler(enabled = !showExitDialog) {
        showExitDialog = true
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Create Place") },
                navigationIcon = {
                    IconButton(onClick = { showExitDialog = true }) {
                        Icon(Icons.Rounded.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(R.drawable.unilocallogo),
                contentDescription = null,
                modifier = Modifier.width(150.dp)
            )

            Text(
                "--------------- General Information ------------------",
                modifier = Modifier.align(Alignment.Start)
            )

            InputText(
                value = title,
                label = "Nombre del lugar",
                supportingText = "Este campo es requerido",
                onValueChange = { title = it },
                onValidate = { it.isBlank() },
                icon = Icons.Rounded.Home
            )

            Spacer(Modifier.height(16.dp))

            InputText(
                value = description,
                label = "Descripción",
                supportingText = "Este campo es requerido",
                onValueChange = { description = it },
                onValidate = { it.isBlank() },
                icon = Icons.Rounded.ExitToApp,
                multiline = true
            )

            Spacer(Modifier.height(16.dp))

            DropdownMenu(
                supportingText = "Seleccione una ciudad",
                icon = Icons.Rounded.Place,
                label = "Seleccione la ciudad",
                list = cities,
                onValueChange = { city = it }
            )

            Spacer(Modifier.height(16.dp))

            InputText(
                value = address,
                label = "Dirección",
                supportingText = "Debe ingresar al menos 10 caracteres",
                onValueChange = { address = it },
                onValidate = { it.length < 10 },
                icon = Icons.Rounded.LocationOn
            )

            Spacer(Modifier.height(16.dp))

            DropdownMenu(
                supportingText = "Seleccione una categoría",
                icon = Icons.Rounded.Menu,
                label = "Seleccione la categoría",
                list = types,
                onValueChange = { type = it }
            )

            Spacer(Modifier.height(16.dp))

            InputText(
                value = phone,
                label = "Número de teléfono",
                supportingText = "Debe tener al menos 10 dígitos",
                onValueChange = { phone = it },
                onValidate = { phone.length < 10 },
                icon = Icons.Rounded.Phone
            )

            Spacer(Modifier.height(32.dp))

            Text("---------------------------- Images --------------------------", modifier = Modifier.align(Alignment.Start))

            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .border(
                        2.dp,
                        if (imageSelected) MaterialTheme.colorScheme.primary else Color.LightGray,
                        RoundedCornerShape(12.dp)
                    )
                    .clickable {
                        imageSelected = !imageSelected

                        val permissionNeeded =
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                                Manifest.permission.READ_MEDIA_IMAGES
                            else
                                Manifest.permission.READ_EXTERNAL_STORAGE

                        val granted =
                            ContextCompat.checkSelfPermission(context, permissionNeeded) ==
                                    PackageManager.PERMISSION_GRANTED

                        if (granted) {
                            fileLauncher.launch("image/*")
                        } else {
                            permissionLauncher.launch(permissionNeeded)
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Rounded.Search,
                    contentDescription = "Agregar imagen",
                    tint = if (imageSelected) MaterialTheme.colorScheme.primary else Color.Gray
                )
            }

            Spacer(Modifier.height(32.dp))

            MapBox(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp),
                activateClick = true,
                onMapClickListener = { clickedPoint = it }
            )

            OperationResultHandler(
                result = placeResult,
                onSuccess = {
                    onNavigateBackTo()
                    placesViewModel.resetOperationResult()
                },
                onFailure = { placesViewModel.resetOperationResult() }
            )

            Button(
                onClick = {
                    val isValid = title.isNotBlank() &&
                            description.isNotBlank() &&
                            address.length >= 10 &&
                            phone.length >= 10

                    if (isValid) {

                        // si no seleccionaron un punto, usamos un punto por defecto
                        val finalPoint = clickedPoint ?: Point.fromLngLat(-75.6811, 4.5350)

                        val place = Place(
                            id = "",
                            title = title,
                            description = description,
                            address = address,
                            city = city as City,
                            location = Location(finalPoint.latitude(), finalPoint.longitude()),
                            images = if (image.isNotBlank()) listOf(image) else emptyList(),
                            phones = phone,
                            type = type as PlaceType,
                            schedules = schedule,
                            ownerId = userId ?: ""
                        )

                        placesViewModel.create(place)

                        Toast.makeText(
                            context,
                            "Lugar agregado correctamente",
                            Toast.LENGTH_LONG
                        ).show()

                        onNavigateBackTo()

                    } else {
                        Toast.makeText(
                            context,
                            "Por favor verifique los datos ingresados",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp),
                shape = RoundedCornerShape(15.dp)
            ) {
                Icon(Icons.Rounded.Check, contentDescription = "Agregar lugar")
                Spacer(Modifier.width(8.dp))
                Text("Agregar lugar")
            }

            AsyncImage(
                model = image,
                contentDescription = null,
                modifier = Modifier.width(200.dp)
            )
        }
    }

    if (showExitDialog) {
        AlertDialog(
            title = { Text("Do you want to leave?") },
            text = { Text("If you leave you will lose all changes") },
            onDismissRequest = { showExitDialog = false },
            confirmButton = {
                Button(onClick = {
                    showExitDialog = false
                    onNavigateBackTo()
                }) {
                    Text("Sure")
                }
            },
            dismissButton = {
                Button(onClick = { showExitDialog = false }) {
                    Text("Close")
                }
            }
        )
    }
}