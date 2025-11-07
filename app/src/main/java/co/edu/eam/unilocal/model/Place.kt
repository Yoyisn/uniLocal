package co.edu.eam.unilocal.model

data class Place (
    var id: String = "",
    val title: String = "",
    val description: String = "",
    val city: City = City.DEFAULT,
    val address: String = "",
    val location: Location = Location(),
    val images: List<String> = emptyList<String>(),
    val phones: String = "",
    val type: PlaceType = PlaceType.DEFAULT,
    val schedules: List<Schedule> = emptyList<Schedule>(),
    val ownerId: String = "",
)