package co.edu.eam.unilocal.viewModel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import co.edu.eam.unilocal.model.City
import co.edu.eam.unilocal.model.DayOfWeek
import co.edu.eam.unilocal.model.Location
import co.edu.eam.unilocal.model.Place
import co.edu.eam.unilocal.model.PlaceType
import co.edu.eam.unilocal.model.Schedule
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalTime

@RequiresApi(Build.VERSION_CODES.O)
class PlacesViewModel: ViewModel () {
    private val _places = MutableStateFlow(emptyList<Place>())
    val places: StateFlow< List<Place> > = _places.asStateFlow()

    init {
        loadPlaces()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun loadPlaces() {
        _places.value = listOf(
            Place (
                id = "1",
                title = "Paisa restaurant",
                description = "The bes paisa restaurant",
                city = City.BOGOTA,
                address = "Cra 12 # 12 - 12",
                location = Location(1.23, 2.34),
                images = listOf("https://i.pinimg.com/736x/50/3e/ae/503eae9dfc5803d8cf0891b8c7093ce4.jpg"),
                phones = listOf("3234513029", "3234513028"),
                type = PlaceType.RESTAURANT,
                schedules = listOf(
                    Schedule(DayOfWeek.MONDAY, open = LocalTime.of(10, 0), close = LocalTime.of( 20, 0)),
                    Schedule(DayOfWeek.THURSDAY, open = LocalTime.of(10, 0), close = LocalTime.of(20,0)),
                    Schedule(DayOfWeek.FRIDAY, open = LocalTime.of(10, 0), close = LocalTime.of(20, 0))
                ),
                ownerId = "3"
            ),
            Place (
                id = "2",
                title = "Bar test",
                description = "Un bar test",
                city = City.MEDELLIN,
                address = "Calle 12 # 12 - 12",
                location = Location(1.23, 2.34),
                images = listOf("https://i.pinimg.com/736x/3e/4c/da/3e4cda7e2989c2a2c54b4657ed7d2fc5.jpg"),
                phones = listOf("3234513029", "3234513028"),
                type = PlaceType.BAR,
                schedules = listOf(
                    Schedule(DayOfWeek.MONDAY, open = LocalTime.of(10, 0), close = LocalTime.of( 20, 0)),
                    Schedule(DayOfWeek.THURSDAY, open = LocalTime.of(10, 0), close = LocalTime.of(20,0)),
                    Schedule(DayOfWeek.FRIDAY, open = LocalTime.of(10, 0), close = LocalTime.of(20, 0))
                ),
                ownerId = "2"
            ),
            Place (
                id = "3",
                title = "Hot Dogs restaurant",
                description = "Hot Dogs",
                city = City.ARMENIA,
                address = "Cra 7 # 45 - 76",
                location = Location(1.23, 2.34),
                images = listOf("https://images.unsplash.com/photo-1550547660-d9450f859349"),
                phones = listOf("3234513029", "3234513028"),
                type = PlaceType.RESTAURANT,
                schedules = listOf(
                    Schedule(DayOfWeek.MONDAY, open = LocalTime.of(10, 0), close = LocalTime.of( 20, 0)),
                    Schedule(DayOfWeek.THURSDAY, open = LocalTime.of(10, 0), close = LocalTime.of(20,0)),
                    Schedule(DayOfWeek.FRIDAY, open = LocalTime.of(10, 0), close = LocalTime.of(20, 0))
                ),
                ownerId = "3"
            ),
            Place (
                id = "4",
                title = "Falabella",
                description = "Store",
                city = City.BOGOTA,
                address = "Cra 89 # 23 - 100",
                location = Location(1.23, 2.34),
                images = listOf("https://i.pinimg.com/1200x/4f/f3/04/4ff3048cdc1fc897f6a84cfe2a0784e1.jpg"),
                phones = listOf("3234513029", "3234513028"),
                type = PlaceType.RESTAURANT,
                schedules = listOf(
                    Schedule(DayOfWeek.MONDAY, open = LocalTime.of(10, 0), close = LocalTime.of( 20, 0)),
                    Schedule(DayOfWeek.THURSDAY, open = LocalTime.of(10, 0), close = LocalTime.of(20,0)),
                    Schedule(DayOfWeek.FRIDAY, open = LocalTime.of(10, 0), close = LocalTime.of(20, 0))
                ),
                ownerId = "2"
            )
        )//End listOf
    }//End fun loadPlaces

    fun create (place: Place) {
        _places.value = _places.value + place
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

}