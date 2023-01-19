class Genero (
    var nombreGenero: String,
    var camaraLibre : Boolean,
    var subgenero: String,
    var fps : Int,
    var online : Boolean


){
    override fun toString(): String {
        return "El genero $nombreGenero posee el subgenero $subgenero y va a $fps FPS. Es el genero de cámara libre? $camaraLibre. Es online? $online"
    }
}