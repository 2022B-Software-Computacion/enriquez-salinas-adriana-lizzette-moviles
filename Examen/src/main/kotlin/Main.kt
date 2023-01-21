fun main() {

    val pathVideojuegoFile = "videojuegos.txt"
    var pathGenerosFile: String
    val menuVideojuego = MenuOpciones()
    val menuGenero = MenuOpciones()
    val listGenerosClass = OperacionesGenero()
    val listVideojuegos = OperacionesVideojuego()
    var optionMenuVideojuego = true
    var optionMenuGenero = true

    println("****************************************")
    println("***Programa de Gestion de Videojuegos***")

    try {
        while (optionMenuVideojuego){
            println()
            menuGenero.mostrarMenuVideojuegos()
            when(readLine()!!.toInt()){
                1 -> {
                    val newVideojuego = listVideojuegos.creacionVideojuego()
                    val aux = listVideojuegos.leerVideojuego(pathVideojuegoFile)
                    listVideojuegos.escribirVideojuego(pathVideojuegoFile, newVideojuego, aux)
                    println()
                }
                2 -> {
                    println("Los Videojuegos que han sido registrados son: ")
                    val auxLeerVideojuego = listVideojuegos.leerVideojuego(pathVideojuegoFile)
                    println(auxLeerVideojuego)
                }
                3 -> {
                    println()
                    println("Operaciones: ")
                    println("1. Ir al Modulo de actualizar Videojuego: ")
                    println("2. Ir al Modulo de gestion de Genero: ")
                    println("Ingrese el número de la operación a realizar: ")
                    when(readLine()!!.toInt()){
                        1 -> {
                            println()
                            println("Los videojuegos registrados actualmente son: ")
                            val auxLeerVideojuego = listVideojuegos.leerVideojuego(pathVideojuegoFile)
                            println(auxLeerVideojuego)
                            println()
                            println("Ingresé el nombre del videojuego a actualizar:")
                            val actualizar = readLine()!!
                            println()
                            println("Actualizar información del Videojuego")
                            listVideojuegos.actualizarVideojuego(actualizar,auxLeerVideojuego,
                                pathVideojuegoFile)
                        }
                        2 -> {
                            try {
                                println()
                                println("Ingresé el nombre del Videojuego al que se le va a adjuntar el genero:")
                                val restaurantUpdate = readLine()!!
                                pathGenerosFile = "$restaurantUpdate.txt"
                                while (optionMenuGenero){
                                    println()
                                    menuVideojuego.mostrarGeneros()
                                    when(readLine()!!.toInt()){
                                        1 -> {
                                            val nuevoGenero = listGenerosClass.creacionGenero()
                                            val aux = listGenerosClass.leerGenero( pathGenerosFile)
                                            listGenerosClass.escribirGenero(pathGenerosFile, nuevoGenero, aux)
                                            println()
                                        }
                                        2 -> {
                                            println("Los generos que han sido creados son: ")
                                            val auxLeerGenero = listGenerosClass.leerGenero(pathGenerosFile)
                                            println(auxLeerGenero)
                                        }
                                        3 -> {
                                            println("Los generos registrados son: ")
                                            val auxReadDishes = listGenerosClass.leerGenero(pathGenerosFile)
                                            println(auxReadDishes)
                                            println()
                                            println("Ingresé el nombre del genero a actualizar: ")
                                            val dishUpdate = readLine()!!
                                            println()
                                            println("Actualizar información del genero")
                                            listGenerosClass.actualizarGenero(dishUpdate, auxReadDishes, pathGenerosFile)
                                        }
                                        4 -> {
                                            println("Los generos registrados son: ")
                                            val auxLeerGenero = listGenerosClass.leerGenero(pathGenerosFile)
                                            println(auxLeerGenero)
                                            println()
                                            println("Ingresé el nombre del genero que desea eliminar: ")
                                            val generoEliminar = readLine()!!
                                            listGenerosClass.eliminarGenero(generoEliminar, auxLeerGenero,pathGenerosFile)
                                            println()
                                            println("Los generos registrados actualizado son: ")
                                            val auxReadDishesI = listGenerosClass.leerGenero(pathGenerosFile)
                                            println(auxReadDishesI)
                                        }
                                        5 -> {
                                            println("Atrás... ")
                                            println()
                                            optionMenuGenero = false
                                        }
                                        else -> {
                                            println("Error la operación ingresada no existe !!!")
                                        }
                                    }
                                }
                            }catch (e: Exception){
                                println(" Error Módulo Generos !! $e")
                            }
                        }
                    }
                }
                4 -> {
                    println("Los videojuegos que se han registrado son: ")
                    val auxLeerVideojuego = listVideojuegos.leerVideojuego(pathVideojuegoFile)
                    println(auxLeerVideojuego)
                    println()
                    println("Ingresé el nombre del Genero que desea eliminar: ")
                    val videojuegoDel = readLine()!!
                    val auxI = listVideojuegos.eliminarVideojuego(videojuegoDel,auxLeerVideojuego,pathVideojuegoFile)
                    println("Los generos registrados son: ")
                    println(auxI)
                }
                5 -> {
                    println("Gracias por usar este programa")
                    optionMenuVideojuego = false
                }
            }
        }
    }catch (e: Exception){
        println("Error Menu $e")
    }
}
