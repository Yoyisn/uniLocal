package co.edu.eam.unilocal.viewModel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.edu.eam.unilocal.model.Place
import co.edu.eam.unilocal.model.PlaceType
import co.edu.eam.unilocal.utils.RequestResult
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@RequiresApi(Build.VERSION_CODES.O)
class PlacesViewModel : ViewModel() {

    // LISTA GENERAL DE PLACES
    private val _places = MutableStateFlow<List<Place>>(emptyList())
    val places: StateFlow<List<Place>> = _places.asStateFlow()

    // LISTA DE PLACES DEL USUARIO
    private val _myPlaces = MutableStateFlow<List<Place>>(emptyList())
    val myPlaces: StateFlow<List<Place>> = _myPlaces.asStateFlow()

    val db = Firebase.firestore

    // RESULTADO DE OPERACIONES (CREAR, EDITAR, ETC.)
    private val _placeResult = MutableStateFlow<RequestResult<Place>?>(null)
    val placeResult: StateFlow<RequestResult<Place>?> = _placeResult.asStateFlow()

    // PLACE SELECCIONADO ACTUALMENTE
    private val _currentPlace = MutableStateFlow<Place?>(null)
    val currentPlace: StateFlow<Place?> = _currentPlace.asStateFlow()

    init {
        getAll()
    }

    // ==================== GET ALL ====================
    fun getAll() {
        viewModelScope.launch {
            _places.value = getAllFirebase()
        }
    }

    private suspend fun getAllFirebase(): List<Place> {
        val snapshot = db.collection("places").get().await()

        return snapshot.documents.mapNotNull {
            it.toObject(Place::class.java)?.apply { this.id = it.id }
        }
    }

    // ==================== GET MY PLACES ====================
    fun getMyPlaces(ownerId: String) {
        viewModelScope.launch {
            _myPlaces.value = getMyPlacesFirebase(ownerId)
        }
    }

    private suspend fun getMyPlacesFirebase(ownerId: String): List<Place> {
        val snapshot = db.collection("places")
            .whereEqualTo("ownerId", ownerId)
            .get()
            .await()

        return snapshot.documents.mapNotNull {
            it.toObject(Place::class.java)?.apply { this.id = it.id }
        }
    }

    // ==================== CREATE PLACE ====================
    fun create(place: Place) {
        viewModelScope.launch {
            _placeResult.value = RequestResult.Loading

            try {
                // Guardar en Firestore
                val docRef = db.collection("places").add(place).await()

                // Objeto con ID correcto
                val savedPlace = place.copy(id = docRef.id)

                // Actualizar listas locales
                _places.value = _places.value + savedPlace

                // SI EL PLACE ES DEL OWNER, AGREGARLO TAMBIÉN A SU LISTA
                if (place.ownerId != null) {
                    _myPlaces.value = _myPlaces.value + savedPlace
                }

                // Resultado de éxito
                _placeResult.value = RequestResult.Success(savedPlace)

            } catch (e: Exception) {
                _placeResult.value = RequestResult.Failure(
                    e.message ?: "Error creating place"
                )
            }
        }
    }

    // ==================== HELPERS ====================
    fun findById(id: String): Place? {
        return _places.value.find { it.id == id }
    }

    fun findByType(type: PlaceType): List<Place> {
        return _places.value.filter { it.type == type }
    }

    fun findByName(name: String): List<Place> {
        return _places.value.filter { it.title.contains(name, ignoreCase = true) }
    }

    fun resetOperationResult() {
        _placeResult.value = null
    }
}