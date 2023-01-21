import java.io.File
import java.io.FileWriter
import java.io.PrintWriter
import java.util.*
import kotlin.collections.ArrayList
class OperacionesVideojuego {
  //Creación
    fun creacionVideojuego():Videojuego{
        println("Ingrese el nombre del videojuego a crear")
        val nombre = readLine()!!
        println("Ingrese el año en el que salió el videojuego")
        val anio = readLine()!!.toInt()
        println("Ingrese el rating del videojuego")
        val rating = readLine()!!
        println("Ingrese la empresa donde se creó el videojuego")
        val casa = readLine()!!
        println("Ingrese el precio del juego")
        val precio = readLine()!!.toDouble()
      val nuevoVideojuego = Videojuego(nombre,anio,rating,casa,precio)
      return nuevoVideojuego
    }

    //Escribir videojuego a partir de archivo
    fun escribirVideojuego(pathFile: String, videojuego: Videojuego, listVideojuego: ArrayList<Videojuego>){
        listVideojuego.add(videojuego)
        val file: File?
        var fw: FileWriter? = null
        var pw: PrintWriter? = null
        var text = ""
        try {
            file = File(pathFile)
            fw = FileWriter(file, true)//true
            pw = PrintWriter(fw)
            text = text + videojuego.nombreJuego + ","
            text = text + videojuego.anioRelease + ","
            text = text + videojuego.rating + ","
            text = text + videojuego.casaDesarrolladora + ","
            text  = text + videojuego.precio + "\n"
            fw.write(text)
            println("El Videojuego ha sido creado de forma satisfactoria")
        }catch (e: Exception){
            println("Error en escribir videojuego $e")
        }finally {
            try {
                fw?.close()
            }catch (e: Exception){
                println("Error en escribir videojuego $e")
            }
        }
    }

    //operacion read
    fun leerVideojuego(pathFile: String): java.util.ArrayList<Videojuego> {
        val listVideojuego = java.util.ArrayList<Videojuego>()
        try {
            var result = ""
            var line: String
            val reader = File(pathFile).bufferedReader()
            while (reader.readLine().also { line = it } != null) {
                val tokens = StringTokenizer(line, ",")
                var data = tokens.nextToken()
                val nombre = data
                data = tokens.nextToken()
                val anio = data.toInt()
                data = tokens.nextToken()
                val rating = data
                data = tokens.nextToken()
                val desarrolladora = data
                data = tokens.nextToken()
                val precio = data.toDouble()

                val newVideojuegoFromFile = Videojuego(nombre, anio, rating, desarrolladora,
                    precio)
                listVideojuego.add(newVideojuegoFromFile)
                result += line
            }
            reader.close()
        } catch (e: java.lang.Exception) {

        }
        return listVideojuego
    }

    //Actualizar
    fun actualizarVideojuego(findVideojuego: String, listVideojuego: ArrayList<Videojuego>, pathFile: String):
            ArrayList<Videojuego>{
        try {
            for (videojuegoFind in listVideojuego){
                if (videojuegoFind.nombreJuego == findVideojuego){
                    val index = listVideojuego.indexOf(videojuegoFind)
                    println("*** Información sobre el videojuego  "+"\n")
                    println("1. Nombre del videojuego: "+videojuegoFind.nombreJuego)
                    println("2. Año de release: "+videojuegoFind.anioRelease)
                    println("3. Rating: "+videojuegoFind.rating)
                    println("4. Casa desarrolladora: "+videojuegoFind.casaDesarrolladora)
                    println("5. Precio: "+videojuegoFind.precio)
                    println("Seleccione la información deseas cambiar: ")
                    when (readLine()!!.toInt()){
                        1 -> {
                            println("Ingrese la nueva información de nombre:")
                            val newName = readLine()
                            videojuegoFind.nombreJuego = newName.toString()
                            listVideojuego[index] = videojuegoFind
                            writeUpdateData(listVideojuego, pathFile)
                            println("El nombre del videojuego se ha actualizado con éxito")
                            break
                        }
                        2 -> {
                            println("Ingrese la nueva información de año de release:")
                            val newId = readLine()!!.toInt()
                            videojuegoFind.anioRelease = newId
                            listVideojuego.set(index,videojuegoFind)
                            writeUpdateData(listVideojuego, pathFile)
                            println("El año de release se ha actualizado con éxito")
                            break
                        }
                        3 -> {
                            println("Ingrese la nueva información del Rating del videojuego:")
                            val ratingVideojuego = readLine()
                            videojuegoFind.rating = ratingVideojuego.toString()
                            listVideojuego.set(index,videojuegoFind)
                            writeUpdateData(listVideojuego, pathFile)
                            println("El rating del videojuego se ha actualizado con exito")
                            break
                        }
                        4 -> {
                            println("Ingrese la nueva información de la empresa de desarrollo")
                            val casaVideojuego = readLine()
                            videojuegoFind.casaDesarrolladora = casaVideojuego.toString()
                            listVideojuego.set(index,videojuegoFind)
                            writeUpdateData(listVideojuego, pathFile)
                            println("La empresa de desarrollo se ha actualizado con éxito")
                            break
                        }
                        5 -> {
                            println("Ingrese la nueva información sobre el precio del videojuego:")
                            val newPrecio = readLine()!!.toDouble()
                            videojuegoFind.precio = newPrecio
                            listVideojuego.set(index,videojuegoFind)
                            writeUpdateData(listVideojuego, pathFile)
                            println("El precio del videojuego se ha actualizado con exito!")
                            break
                        }
                        else -> {
                            println("La operación de actualización escogida no existe")
                            break
                        }
                    }
                }else{
                    println("El videojuego ingresado no existe.")
                }
            }
        }catch (e:Exception){
            println("Error Update $e")
        }
        return listVideojuego
    }

    fun writeUpdateData(listVideojuego: ArrayList<Videojuego>, pathFile: String) {
        try {
            var file: File?
            var fw: FileWriter? = null
            var pw: PrintWriter? = null
            var text = ""
            for (videojuego in listVideojuego){
                try {
                    file = File(pathFile)
                    fw = FileWriter(file)//true
                    pw = PrintWriter(fw)
                    text = text + videojuego.nombreJuego + ","
                    text = text + videojuego.anioRelease + ","
                    text = text + videojuego.rating + ","
                    text = text + videojuego.casaDesarrolladora+ ","
                    text = text + videojuego.precio+"\n"
                    fw.write(text)
                    fw.write("\n")
                }catch (e: Exception){
                    println("Error Write Update Videojuego $e")
                }finally {
                    try {
                        if(fw !=null){
                            fw.close()
                        }
                    }catch (e: Exception){
                        println("Error Write Update Videojuego $e")
                    }
                }
            }
        }catch (e: Exception){
            println("Error Update $e")
        }
    }

    //Eliminar
    fun eliminarVideojuego(findVideojuego: String, listVideojuego: ArrayList<Videojuego>, pathFile: String):
            ArrayList<Videojuego>{
        try {
            for(encontrarVideojuego in listVideojuego){
                if(encontrarVideojuego.nombreJuego == findVideojuego){
                    listVideojuego.remove(encontrarVideojuego)
                    writeUpdateData(listVideojuego, pathFile)

                    println("Videojuego eliminado con exito")
                    break
                }else{
                    println("El videojuego ingresado no existe, intente con otro")
                }
            }
        }catch (e: Exception){
            println("Error Delete $e")
        }
        return listVideojuego
    }


}
