class Videojuego (
    var nombreJuego: String,
    var anioRelease: Int,
    var rating : String,
    var casaDesarrolladora : String,
    var precio: Double,

){
    override fun toString(): String {
        return "El juego $nombreJuego salió en el año $anioRelease en la empresa $casaDesarrolladora. Su rating es $rating y su costo es $precio."
    }
}
