package co.edu.eam.unilocal.model

enum class City (override val displayName: String) : DisplayableEnum {
    DEFAULT(" Default "),
    ARMENIA("Medellin"),
    BOGOTA("Bogota"),
    MEDELLIN("Medellin"),
    PEREIRA("Pereira"),
    CALI("Cali"),
    MANIZALES("Manizales")
}