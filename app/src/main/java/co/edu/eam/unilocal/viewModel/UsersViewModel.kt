package co.edu.eam.unilocal.viewModel

import androidx.lifecycle.ViewModel
import co.edu.eam.unilocal.model.Role
import co.edu.eam.unilocal.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class UsersViewModel: ViewModel() {
    private val _users = MutableStateFlow(emptyList<User>())
    val users: StateFlow< List<User> > = _users.asStateFlow()

    init {
        loadUsers()
    }

    fun loadUsers() {

        _users.value = listOf(
            User(
                id = "1",
                name = "Admin",
                //username = "admin",
                role = Role.ADMIN,
                city = "Armenia",
                phoneNumber = "3113509873",
                email = "admin@gmail.com",
                password = "123456",
            ),
            User(
                id = "2",
                name = "Carlos",
                //username = "carlosf",
                role = Role.USER,
                city = "Armenia",
                phoneNumber = "3113509873",
                email = "carlos@gmail.com",
                password = "123456",
            )
        )//End listOf
    }//End fun loadUsers

    fun create (user: User) {
        _users.value = _users.value + user
    }

    fun findById (id: String): User? {
        return _users.value.find { it.id == id }
    }

    fun findByEmail (email: String): User? {
        return _users.value.find { it.email == email }
    }

    fun login ( email: String, password: String ): User? {
        return _users.value.find { it.email == email && it.password == password }
    }

}