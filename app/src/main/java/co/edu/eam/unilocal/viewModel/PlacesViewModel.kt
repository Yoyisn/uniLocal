package co.edu.eam.unilocal.viewModel

import androidx.lifecycle.ViewModel
import co.edu.eam.unilocal.model.Location
import co.edu.eam.unilocal.model.Place
import co.edu.eam.unilocal.model.PlaceType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class PlacesViewModel: ViewModel () {
    private val _places = MutableStateFlow(emptyList<Place>())
    val places: StateFlow< List<Place> > = _places.asStateFlow()

    init {
        loadPlaces()
    }

    fun loadPlaces() {
        _places.value = listOf(
            Place (
                id = "1",
                title = "Paisa restaurant",
                description = "The bes paisa restaurant",
                address = "Cra 12 # 12 - 12",
                location = Location(1.23, 2.34),
                images = listOf("https://www.hiddenboston.com/ElPaisaPhoto.html"),
                phones = listOf("3234513029", "3234513028"),
                type = PlaceType.RESTAURANT,
                schedules = listOf()
            ),
            Place (
                id = "2",
                title = "Bar test",
                description = "Un bar test",
                address = "Calle 12 # 12 - 12",
                location = Location(1.23, 2.34),
                images = listOf("https://stock.adobe.com/co/search?k=bar+design&asset_id=658054201"),
                phones = listOf("3234513029", "3234513028"),
                type = PlaceType.BAR,
                schedules = listOf()
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
        return _places.value.filter { it.title.contains(name) }
    }

}