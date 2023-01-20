import java.io.*
import java.util.*
class OperacionesGenero {
    //Creación
    fun creacionGenero():Genero{
        println("Ingrese el nombre del genero a crear")
        val nombre = readLine()!!
        println("Si el genero tiene camara libre escriba 1 sino 0")
        val camara = readLine()!!.toInt()
        var cambool = true
        if (camara ==1){
            cambool = true
        }else{
            cambool = false
        }
        println("Ingrese el Subgenero del genero")
        val subgenero = readLine()!!
        println("Ingrese los fps establecidos del genero")
        val fps = readLine()!!.toInt()
        println("Si el genero es online escriba 1 sino 0")
        val online = readLine()!!.toInt()
        var onlbool = true
        if (online ==1){
            onlbool = true
        }else{
            onlbool = false
        }
        val nuevoGenero = Genero(nombre,cambool,subgenero,fps,onlbool)
        return nuevoGenero

    }
    //Escribir genero a partir de archivo
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

    //operacion read
    fun leerGenero(pathFile: String): java.util.ArrayList<Genero> {
        val listGenero = java.util.ArrayList<Genero>()
        try {
            var result = ""
            var line = ""
            val reader = File(pathFile).bufferedReader()
            while (reader.readLine().also { line = it } != null) {
                val tokens = StringTokenizer(line, ",")
                var data = tokens.nextToken()
                val nombre = data;
                data = tokens.nextToken()
                val camara = data.toBoolean();
                data = tokens.nextToken()
                val subgenero = data
                data = tokens.nextToken()
                val fps = data.toInt();
                data = tokens.nextToken()
                val online = data.toBoolean()

                val newGeneroFromFile = Genero(nombre,camara,subgenero,fps,online)
                listGenero.add(newGeneroFromFile)
                result += line
            }
            reader.close()
        } catch (e: java.lang.Exception) {

        }
        return listGenero
    }





}