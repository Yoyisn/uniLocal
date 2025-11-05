package co.edu.eam.unilocal.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.edu.eam.unilocal.model.City
import co.edu.eam.unilocal.model.Role
import co.edu.eam.unilocal.model.User
import co.edu.eam.unilocal.utils.RequestResult
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class UsersViewModel: ViewModel() {
    private val _users = MutableStateFlow(emptyList<User>())
    val users: StateFlow< List<User> > = _users.asStateFlow()

    private val _userResult = MutableStateFlow<RequestResult?>(null)
    val userResult: StateFlow<RequestResult?> = _userResult.asStateFlow()

    private val _currentUser = MutableStateFlow< User? >( null )
    val currentUser: StateFlow< User? > = _currentUser.asStateFlow()

    val db = Firebase.firestore

    init {
        loadUsers()
    }

    fun create (user: User) {
        viewModelScope.launch {
            _userResult.value = RequestResult.Loading
            _userResult.value = runCatching { createFirebase(user) }
                .fold(
                    onSuccess = { RequestResult.Success( "User created successfully" ) },
                    onFailure = { RequestResult.Failure( it.message ?: "Error creating user" ) }
                )// End fold
        }//End viewModelScope.launch
    }

    private suspend fun createFirebase (user: User) {
        db.collection("users").add(user).await()
    }

    fun findById (id: String) {
        viewModelScope.launch {
            _userResult.value = RequestResult.Loading
            _userResult.value = runCatching { findByIdFirebase(id) }
                .fold(
                    onSuccess = { RequestResult.Success( "User loged successfully" ) },
                    onFailure = { RequestResult.Failure( it.message ?: "Error login user" ) }
                )// End fold
        }//End viewModelScope.launch
    }

    private suspend fun findByIdFirebase (id: String) {
        val snapshot = db.collection("users").document(id).get().await()
        val user = snapshot.toObject(User::class.java)?.apply {
            this.id = snapshot.id
        }

        _currentUser.value = user
    }

    fun login ( email: String, password: String ){
        viewModelScope.launch {
            _userResult.value = RequestResult.Loading
            _userResult.value = runCatching { loginFirebase(email, password) }
                .fold(
                    onSuccess = { RequestResult.Success( "User loged successfully" ) },
                    onFailure = { RequestResult.Failure( it.message ?: "Error login user" ) }
                )// End fold
        }//End viewModelScope.launch
    }
    private suspend fun loginFirebase ( email: String, password: String ){
        val snapshot = db.collection("users").whereEqualTo( "email", email ).whereEqualTo( "password", password ).get().await()

        if ( snapshot.documents.isEmpty() ) {
            throw Exception ( "Email or password are wrong" )
        } else {
            snapshot.documents.mapNotNull {
                var user = it.toObject( User::class.java )?.apply{
                    this.id = it.id
                }
                _currentUser.value = user
            }
        }
    }

    fun resetOperationResult () {
        _userResult.value = null
    }

    fun loadUsers() {

        /*
        _users.value = listOf(
            User(
                id = "1",
                name = "Admin",
                role = Role.ADMIN,
                city = City.BOGOTA,
                phoneNumber = "3113509873",
                email = "admin@gmail.com",
                password = "123456",
            ),
            User(
                id = "2",
                name = "Carlos",
                role = Role.USER,
                city = City.MEDELLIN,
                phoneNumber = "3113509873",
                email = "carlos@gmail.com",
                password = "123456",
            ),
            User(
                id = "3",
                name = "Jordy",
                role = Role.USER,
                city = City.MANIZALES,
                phoneNumber = "3113509873",
                email = "jordy@gmail.com",
                password = "123456",
            )
        )//End listOf
        */
    }//End fun loadUsers

}