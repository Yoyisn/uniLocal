package co.edu.eam.unilocal.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.edu.eam.unilocal.model.User
import co.edu.eam.unilocal.utils.RequestResult
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class UsersViewModel : ViewModel() {

    // Lista de usuarios (si más adelante la usas)
    private val _users = MutableStateFlow<RequestResult<List<User>>?>(null)
    val users: StateFlow<RequestResult<List<User>>?> = _users.asStateFlow()

    // Resultado de operaciones como login, register, etc.
    private val _userResult = MutableStateFlow<RequestResult<User>?>(null)
    val userResult: StateFlow<RequestResult<User>?> = _userResult.asStateFlow()

    // Usuario actualmente logueado
    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

    val db = Firebase.firestore
    val auth: FirebaseAuth = FirebaseAuth.getInstance()

    init {
        loadUsers()
    }

    // ========================= REGISTRO ===============================

    fun create(user: User) {
        viewModelScope.launch {
            _userResult.value = RequestResult.Loading

            try {
                val uid = createFirebase(user)
                val userCopy = user.copy(id = uid, password = "")

                db.collection("users").document(uid).set(userCopy).await()

                _currentUser.value = userCopy
                _userResult.value = RequestResult.Success(userCopy)

            } catch (e: Exception) {
                _userResult.value = RequestResult.Failure(
                    e.message ?: "Error creating user"
                )
            }
        }
    }

    private suspend fun createFirebase(user: User): String {
        val authResult = auth.createUserWithEmailAndPassword(
            user.email,
            user.password
        ).await()

        return authResult.user?.uid ?: throw Exception("No UID returned from Firebase")
    }

    // ========================= LOGIN ===============================

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _userResult.value = RequestResult.Loading

            try {
                val authResult = auth.signInWithEmailAndPassword(email, password).await()
                val uid = authResult.user?.uid ?: throw Exception("User not found")

                val snapshot = db.collection("users").document(uid).get().await()
                val user = snapshot.toObject(User::class.java)?.copy(id = snapshot.id)
                    ?: throw Exception("Error loading user")

                _currentUser.value = user
                _userResult.value = RequestResult.Success(user)

            } catch (e: Exception) {
                _userResult.value = RequestResult.Failure(
                    e.message ?: "Error login user"
                )
            }
        }
    }

    // ========================= LOGOUT ===============================

    fun logout() {
        auth.signOut()
        _currentUser.value = null
    }

    fun resetOperationResult() {
        _userResult.value = null
    }

    fun loadUsers() {
        // Si en el futuro deseas cargar todos los usuarios, aquí se implementa.
    }
}
