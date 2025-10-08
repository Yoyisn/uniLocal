package co.edu.eam.unilocal.model

enum class PlaceType (override val displayName: String): DisplayableEnum {
    RESTAURANT( "Restaurant" ),
    BAR("Bar"),
    HOTEL("Hotel"),
    PARK("Park"),
    SHOPPING("Store"),
    OTHER("Other")
}