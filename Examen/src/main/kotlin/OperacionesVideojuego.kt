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
        listVideojuego.add(videojuego);
        var file: File? = null
        var fw: FileWriter? = null
        var pw: PrintWriter? = null
        var text = ""
        try {
            file = File(pathFile)
            fw = FileWriter(file, true)//true
            pw = PrintWriter(fw)
            text = text + videojuego.nombreJuego + ",";
            text = text + videojuego.anioRelease + ",";
            text = text + videojuego.rating + ",";
            text = text + videojuego.casaDesarrolladora + ",";
            text  = text + videojuego.precio + "\n";
            fw.write(text);
            println("El Videojuego ha sido creado de forma satisfactoria")
        }catch (e: Exception){
            println("Error en escribir videojuego $e")
        }finally {
            try {
                if(fw !=null){
                    fw.close()
                }
            }catch (e: Exception){
                println("Error en escribir videojuego $e")
            }
        }
    }




}
