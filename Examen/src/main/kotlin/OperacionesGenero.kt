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
    fun escribirGenero(pathFile: String, genero: Genero, listVideojuego: ArrayList<Genero>){
        listVideojuego.add(genero);
        var file: File? = null
        var fw: FileWriter? = null
        var pw: PrintWriter? = null
        var text = ""
        try {
            file = File(pathFile)
            fw = FileWriter(file, true)//true
            pw = PrintWriter(fw)
            text = text + genero.nombreGenero + ","
            text = text + genero.camaraLibre + ","
            text = text + genero.subgenero + ","
            text = text + genero.fps + ","
            text  = text + genero.online + "\n"
            fw.write(text)
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
                val nombre = data
                data = tokens.nextToken()
                val camara = data.toBoolean()
                data = tokens.nextToken()
                val subgenero = data
                data = tokens.nextToken()
                val fps = data.toInt()
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

    //Actualizar
    fun actualizarGenero(findGenero: String, listGenero: ArrayList<Genero>, pathFile: String):
            ArrayList<Genero>{
        try {
            for (generoFind in listGenero){
                if (generoFind.nombreGenero == findGenero){
                    val index = listGenero.indexOf(generoFind)
                    println("*** Información sobre el genero  "+"\n")
                    println("1. Nombre del género: "+generoFind.nombreGenero)
                    println("2. Tiene cámara libre?: "+generoFind.camaraLibre)
                    println("3. Subgenero: "+generoFind.subgenero)
                    println("4. FPS: "+generoFind.fps)
                    println("5. Es online?: "+generoFind.online)
                    println("Seleccione la información deseas cambiar: ")
                    when (readLine()!!.toInt()){
                        1 -> {
                            println("Ingrese la nueva información de nombre:")
                            val newName = readLine()
                            generoFind.nombreGenero = newName.toString()
                            listGenero[index] = generoFind
                            writeUpdateData(listGenero, pathFile)
                            println("El nombre del videojuego se ha actualizado con éxito")
                            break
                        }
                        2 -> {
                            println("Ingrese la nueva información sobre la camara libre:")
                            val camara = readLine()!!.toBoolean()
                            generoFind.camaraLibre = camara
                            listGenero .set(index,generoFind)
                            writeUpdateData(listGenero, pathFile)
                            println("La actualización del estado sobre la camara se ha actualizado con exito!")
                            break
                        }
                        3 -> {
                            println("Ingrese la nueva información del subgenero de videojuego:")
                            val subgenero = readLine()
                            generoFind.subgenero = subgenero.toString()
                            listGenero.set(index,generoFind)
                            writeUpdateData(listGenero, pathFile)
                            println("El subgenero se ha actualizado con éxito")
                            break
                        }
                        4 -> {
                            println("Ingrese la nueva información sobre los fps")
                            val fps = readLine()!!
                            generoFind.fps = fps.toInt()
                            listGenero.set(index,generoFind)
                            writeUpdateData(listGenero, pathFile)
                            println("La información de fps se ha actualizado con éxito")
                            break
                        }
                        5 -> {
                            println("Ingrese la nueva información sobre si es online:")
                            val online = readLine()!!.toBoolean()
                            generoFind.camaraLibre = online
                            listGenero .set(index,generoFind)
                            writeUpdateData(listGenero, pathFile)
                            println("La actualización del estado sobre si es online o no se ha actualizado con exito!")
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
        return listGenero
    }

    fun writeUpdateData(listGenero: ArrayList<Genero>, pathFile: String) {
        try {
            var file: File? = null
            var fw: FileWriter? = null
            var pw: PrintWriter? = null
            var text = ""
            for (genero in listGenero){
                try {
                    file = File(pathFile)
                    fw = FileWriter(file)//true
                    pw = PrintWriter(fw)
                    text = text + genero.nombreGenero + ","
                    text = text + genero.camaraLibre + ","
                    text = text + genero.subgenero+ ","
                    text = text + genero.fps+ ","
                    text = text + genero.online+"\n"
                    fw.write(text);
                    fw.write("\n")
                }catch (e: Exception){
                    println("Error Write Update Genero $e")
                }finally {
                    try {
                        if(fw !=null){
                            fw.close()
                        }
                    }catch (e: Exception){
                        println("Error Write Update Genero $e")
                    }
                }
            }
        }catch (e: Exception){
            println("Error Update $e")
        }
    }

    //Eliminar
    //Delete Dish
    fun eliminarGenero(findVideojuego: String, listGenero: ArrayList<Genero>, pathFile: String):
            ArrayList<Genero>{
        try {
            for(encontrarGenero in listGenero){
                if(encontrarGenero.nombreGenero == findVideojuego){
                    listGenero.remove(encontrarGenero)
                    writeUpdateData(listGenero, pathFile)

                    println("Genero eliminado con exito")
                    break
                }else{
                    println("El genero ingresado no existe, intente con otro")
                }
            }
        }catch (e: Exception){
            println("Error Delete $e")
        }
        return listGenero
    }

}