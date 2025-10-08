package co.edu.eam.unilocal.model

data class User (
    val id: String,
    val name: String,
    //val username:  String,
    val role: Role,
    val phoneNumber: String,
    val email: String,
    val password: String,
    val city: City,
)