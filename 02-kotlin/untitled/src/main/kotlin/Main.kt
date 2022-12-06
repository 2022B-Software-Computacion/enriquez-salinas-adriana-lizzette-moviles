import java.util.*
fun main() {
    println("Hola mundo")

    //tips de variables
    //inmutables (no reasignar)
    val inmutable : String = "Adriana"
   // inmutable = "lizzette"; (no se puede re asignar)

    //Variable mutable
    var mutable : String = "Adri"
    mutable = "liz"

    // val > var

    //duck typing
    val ejemploVariable = "ejemplo"
    val edadEjemplo : int = 12
    ejemploVariable.trim()

    //Variables primitivas
    val nombreProfesor : String = "Adrian Eguez"
    val sueldo : double = 1.2
    val estadoCivil : char = 's'
    val mayorEdad : boolean = true
    //clases java
    val fechaNacimiento : Date = Date()

    //no tiene truthy ni falsy
    if (true) {

    }else {
    }

    //Switch no existe
    val estadoCivilWhen = 's'
    when(estadoCivilWhen){

        ('S') -> {
    println("Acercarse")
        }
        ('C') -> {
            println("Alejarse")
        }
        "UN" -> println("Preguntar")
        else -> println("No reconocido")
    }

    val coqueteo = if (estadoCivilWhen == 's')"SI" else "NO"


    //funciones empiezan con fun
    // Unit == void
    fun imprimirNombre(nombre: String): Unit {
        println("Nombre : ${nombre}")
    }

    fun calcularSueldo(
        sueldo: Double, // Requerido
        tasa: Double = 12.00,   // Opcional (Defecto)
        bonoEspecial: Double? = null,   //Opcional (Null) -> nullable
    ): Double {
        // String -> String? (Puede ser Null)
        // Int -> Int?
        // Date -> Date?
        if (bonoEspecial == null){
            return sueldo * (100 / tasa)
        } else {
            return sueldo * (100 / tasa) + bonoEspecial
        }
    }
}
