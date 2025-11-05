package co.edu.eam.unilocal.model

data class User (
    var id: String = "",
    val name: String = "",
    //val username:  String,
    val role: Role = Role.USER,
    val phoneNumber: String = "",
    val email: String = "",
    val password: String = "",
    val city: City = City.ARMENIA,
)