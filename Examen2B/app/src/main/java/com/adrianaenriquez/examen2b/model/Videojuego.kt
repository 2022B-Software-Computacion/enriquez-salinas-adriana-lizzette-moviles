package com.adrianaenriquez.examen2b.model

data class Videojuego (
    var id: String? = null,
    var name: String? = null,
    var anioRelease: Int? = null,
    var rating: String? = null,
    var casaDesarrolladora: String? = null,

    /*un genero tiene varios videojuegos*/
    var generoId: String? = null
)
