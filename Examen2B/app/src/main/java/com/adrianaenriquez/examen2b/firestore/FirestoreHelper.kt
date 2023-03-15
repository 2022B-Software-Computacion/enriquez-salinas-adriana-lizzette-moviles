package com.adrianaenriquez.examen2b.firestore

import android.content.ContentValues.TAG
import android.util.Log
import com.adrianaenriquez.examen2b.model.Genero
import com.adrianaenriquez.examen2b.model.Videojuego
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class FirestoreHelper {
    private val db = Firebase.firestore

    /*Métodos*/
    fun getAllGeneros(onComplete : (List<Genero>?) -> Unit){
        db.collection("generos").get().addOnSuccessListener { result ->
            val generos = mutableListOf<Genero>()
            for (document in result){
                val genero = document.toObject(Genero::class.java)
                genero.id = document.id
                generos.add(genero)
            }
            onComplete(generos)
        }
            .addOnFailureListener { exception -> Log.d(TAG, "ERROR EN CONSEGUIR GENEROS: ${exception.message}")
                onComplete(emptyList())
            }
    }

    /*CRUD GENERO*/

    fun crearGenero(genero: Genero, onComplete: (String?) -> Unit){
      db.collection("generos").add(genero).addOnSuccessListener {
          documentReference ->
          val generoId = documentReference.id
          db.collection("generos").document(generoId).update("id", generoId)
              .addOnSuccessListener {
                  onComplete(documentReference.id)
              }.addOnFailureListener {
                  onComplete(null)
              }.addOnFailureListener {  }
          onComplete(null)
      }
    }

    fun getGeneroById(generoId: String, onComplete: (Genero?) -> Unit){
        db.collection("generos").document(generoId).get()
            .addOnSuccessListener { document ->
                val genero = document.toObject(Genero::class.java)
                genero?.id = document.id
                onComplete(genero)
            }.addOnFailureListener { exception ->
                Log.d(TAG, "Error en conseguir genero por id: ${exception.message}")
                onComplete(null)
            }
    }

    fun editarGenero(generoId: String, updatedGenero:Genero, onComplete: (Boolean) -> Unit){
        db.collection("generos").document(generoId).set(updatedGenero)
            .addOnSuccessListener {
                onComplete(true)
            }.addOnFailureListener { exception ->Log.w( TAG, "Error en actualizar Genero", exception)
            onComplete(false)
            }
    }
    fun eliminarGenero(generoId: String, onComplete: (Boolean) -> Unit){
        db.collection("generos").document(generoId).delete()
            .addOnSuccessListener {
                onComplete(true)
            }.addOnFailureListener {
                onComplete(false)
            }
    }


  /*CRUD Videojuego*/

    fun crearVideojuego(videojuego: Videojuego, onComplete: (String?) -> Unit){
        db.collection("generos").document(videojuego.generoId!!)
            .collection("videojuegos").add(videojuego)
            .addOnSuccessListener {documentReference ->
            val videojuegoId = documentReference.id
            db.collection("generos").document(videojuego.generoId!!)
                .collection("Videojuegos").document(videojuegoId)
                .update("id", videojuegoId).addOnSuccessListener{
                    onComplete(documentReference.id)
                }.addOnFailureListener {
                    onComplete(null)
                }
                .addOnFailureListener {
                    onComplete(null)
                }
            }
    }

    /*Obtener los videojuegos por generoid*/
    fun obtenerVideojuegoByGeneroId(generoId: String, onComplete: (List<Videojuego>?) -> Unit){
        db.collection("generos").document(generoId)
            .collection("videojuegos").get()
            .addOnSuccessListener { documents ->
                val videojuegos = mutableListOf<Videojuego>()
                for (document in documents){
                    val videojuego = document.toObject(Videojuego::class.java)
                    videojuegos.add(videojuego)
                }
                onComplete(videojuegos)
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error en recuperar los Generos: ${exception.message}")
                onComplete(emptyList())
            }
    }

    fun obtenerVideojuegoById(generoId: String, videojuegoId: String,onComplete: (Videojuego?) -> Unit){
        db.collection("generos").document(generoId)
            .collection("videojuegos").document(videojuegoId)
            .get().addOnSuccessListener { document ->
                val videojuego = document.toObject(Videojuego::class.java)
                onComplete(videojuego)
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error en recuperar los videojuegos: ${exception.message}")
                onComplete(null)
            }
    }

    fun actualizarVideojuego(videojuego: Videojuego, onComplete: (Boolean) -> Unit){
        db.collection("genero").document(videojuego.generoId!!)
            .collection("videojuegos").document(videojuego.id!!)
            .set(videojuego).addOnSuccessListener {
                onComplete(true)
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error en actualizar videojuego: ${exception.message}")
                onComplete(false)
            }
    }

    fun eliminarVideojuego(videojuegoId: String, generoId: String, onComplete: (Boolean) -> Unit){
        db.collection("generos").document(generoId).collection("videojuegos")
            .document(videojuegoId).delete().addOnSuccessListener {
                onComplete(true)
            }
            .addOnFailureListener {exception ->
                Log.d(TAG, "Error en eliminar videojuego: ${exception.message}")
                onComplete(false)
            }
    }

}
