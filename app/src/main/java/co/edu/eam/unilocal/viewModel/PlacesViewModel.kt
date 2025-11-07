package co.edu.eam.unilocal.viewModel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.edu.eam.unilocal.model.City
import co.edu.eam.unilocal.model.DayOfWeek
import co.edu.eam.unilocal.model.Location
import co.edu.eam.unilocal.model.Place
import co.edu.eam.unilocal.model.PlaceType
import co.edu.eam.unilocal.model.Schedule
import co.edu.eam.unilocal.model.User
import co.edu.eam.unilocal.utils.RequestResult
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.time.LocalTime

@RequiresApi(Build.VERSION_CODES.O)
class PlacesViewModel: ViewModel () {

    private val _places = MutableStateFlow(emptyList<Place>())
    val places: StateFlow< List<Place> > = _places.asStateFlow()

    private val _myPlaces = MutableStateFlow(emptyList<Place>())
    val myPlaces: StateFlow< List<Place> > = _myPlaces.asStateFlow()

    val db = Firebase.firestore

    private val _placeResult = MutableStateFlow<RequestResult?>(null)
    val placeResult: StateFlow<RequestResult?> = _placeResult.asStateFlow()

    private val _currentPlace = MutableStateFlow< Place? >( null )
    val currentPlace: StateFlow< Place? > = _currentPlace.asStateFlow()

    init {
     getAll()
    }

    fun getAll () {
        viewModelScope.launch {
            _places.value = getAllFirebase()
        }
    }

    private suspend fun getAllFirebase (): List<Place> {
        val snapshot = db.collection("places" ).get().await()

        return snapshot.documents.mapNotNull {
            it.toObject( Place::class.java )?.apply{
                this.id = it.id
            }
        }
    }

    fun getMyPlaces ( ownerId: String ) {
        viewModelScope.launch {
            _myPlaces.value = getMyPlacesFirebase( ownerId )
        }
    }

    private suspend fun getMyPlacesFirebase (ownerId: String): List<Place> {
        val snapshot = db.collection("places" ).whereEqualTo("ownerId", ownerId).get().await()

        return snapshot.documents.mapNotNull {
            it.toObject( Place::class.java )?.apply{
                this.id = it.id
            }
        }
    }

    fun create (place: Place) {
        _places.value = _places.value + place
    }

    private suspend fun createFirebase ( place: Place ) {
        db.collection("places" ).add( place ).await()
        viewModelScope.launch {
            _placeResult.value = RequestResult.Loading
            _placeResult.value = runCatching { createFirebase(place) }
                .fold(
                    onSuccess = { RequestResult.Success( "Place successfully created" ) },
                    onFailure = { RequestResult.Failure( it.message ?: "Error creating place" ) }
                )// End fold
        }//End viewModelScope.launch
    }

    fun findById (id: String): Place? {
        return _places.value.find { it.id == id }
    }

    fun findByType (type: PlaceType): List<Place> {
        return _places.value.filter { it.type == type }
    }

    fun findByName ( name: String) : List<Place> {
        return _places.value.filter { it.title.contains(other = name, ignoreCase = true) }
    }

    fun resetOperationResult () {
        _placeResult.value = null
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun loadPlaces() {
        /*
        _places.value = listOf(
            Place(
                id = "1",
                title = "Paisa Restaurant",
                description = "El mejor restaurante paisa de Armenia",
                city = City.ARMENIA,
                address = "Carrera 14 # 9N - 24, Armenia, Quindío",
                location = Location(4.551256, -75.663174), // Restaurante La Fogata (zona norte)
                images = listOf("https://i.pinimg.com/736x/50/3e/ae/503eae9dfc5803d8cf0891b8c7093ce4.jpg"),
                phones = listOf("3234513029", "3234513028"),
                type = PlaceType.RESTAURANT,
                schedules = listOf(
                    Schedule(DayOfWeek.MONDAY, open = LocalTime.of(10, 0), close = LocalTime.of(20, 0)),
                    Schedule(DayOfWeek.THURSDAY, open = LocalTime.of(10, 0), close = LocalTime.of(20, 0)),
                    Schedule(DayOfWeek.FRIDAY, open = LocalTime.of(10, 0), close = LocalTime.of(20, 0))
                ),
                ownerId = "3"
            ),
            Place(
                id = "2",
                title = "Bar Test",
                description = "Un bar con ambiente moderno en Armenia",
                city = City.ARMENIA,
                address = "Calle 21 # 14-45, Armenia, Quindío",
                location = Location(4.538896, -75.670315), // Cerca del Parque Sucre
                images = listOf("https://i.pinimg.com/736x/3e/4c/da/3e4cda7e2989c2a2c54b4657ed7d2fc5.jpg"),
                phones = listOf("3234513029", "3234513028"),
                type = PlaceType.BAR,
                schedules = listOf(
                    Schedule(DayOfWeek.MONDAY, open = LocalTime.of(16, 0), close = LocalTime.of(23, 0)),
                    Schedule(DayOfWeek.THURSDAY, open = LocalTime.of(16, 0), close = LocalTime.of(23, 0)),
                    Schedule(DayOfWeek.FRIDAY, open = LocalTime.of(16, 0), close = LocalTime.of(23, 59))
                ),
                ownerId = "2"
            ),
            Place(
                id = "3",
                title = "Hot Dogs Restaurant",
                description = "Deliciosos perros calientes artesanales",
                city = City.ARMENIA,
                address = "Avenida Bolívar # 8 Norte - 45, Armenia, Quindío",
                location = Location(4.542981, -75.657433), // Sobre Av. Bolívar, zona comercial
                images = listOf("https://images.unsplash.com/photo-1550547660-d9450f859349"),
                phones = listOf("3234513029", "3234513028"),
                type = PlaceType.RESTAURANT,
                schedules = listOf(
                    Schedule(DayOfWeek.MONDAY, open = LocalTime.of(11, 0), close = LocalTime.of(22, 0)),
                    Schedule(DayOfWeek.THURSDAY, open = LocalTime.of(11, 0), close = LocalTime.of(22, 0)),
                    Schedule(DayOfWeek.FRIDAY, open = LocalTime.of(11, 0), close = LocalTime.of(23, 0))
                ),
                ownerId = "3"
            ),
            Place(
                id = "4",
                title = "Falabella Armenia",
                description = "Tienda por departamentos en el centro comercial Unicentro",
                city = City.ARMENIA,
                address = "Centro Comercial Unicentro, Armenia, Quindío",
                location = Location(4.533609, -75.675739), // Falabella Unicentro Armenia
                images = listOf("https://i.pinimg.com/1200x/4f/f3/04/4ff3048cdc1fc897f6a84cfe2a0784e1.jpg"),
                phones = listOf("3234513029", "3234513028"),
                type = PlaceType.SHOPPING,
                schedules = listOf(
                    Schedule(DayOfWeek.MONDAY, open = LocalTime.of(10, 0), close = LocalTime.of(21, 0)),
                    Schedule(DayOfWeek.THURSDAY, open = LocalTime.of(10, 0), close = LocalTime.of(21, 0)),
                    Schedule(DayOfWeek.FRIDAY, open = LocalTime.of(10, 0), close = LocalTime.of(21, 0))
                ),
                ownerId = "2"
            )
        )//End listOf
        */
    }//End fun loadPlaces
}