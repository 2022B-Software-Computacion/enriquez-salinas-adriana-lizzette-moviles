package com.adrianaenriquez.examen2b.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.adrianaenriquez.examen2b.R
import com.adrianaenriquez.examen2b.firestore.FirestoreHelper
import com.adrianaenriquez.examen2b.model.Genero
import android.content.Intent
import android.util.Log
import android.widget.Button
import com.adrianaenriquez.examen2b.fragment.GeneroListFragment
import com.adrianaenriquez.examen2b.model.Videojuego

class MainActivity : AppCompatActivity() {

    private val firestoreHelper = FirestoreHelper()
    private val generos: ArrayList<Genero> = ArrayList()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val generoListFragment = GeneroListFragment()
        val crearGeneroButton: Button = findViewById(R.id.btn_crear_genero)
        fragmentTransaction.add(R.id.fragment_genero,generoListFragment)
        fragmentTransaction.commit()

        crearGeneroButton.setOnClickListener{
            val intent = Intent(this,CrearGeneroActivity::class.java)
            startActivity(intent)
        }
        crearTestData()
    }

    private fun crearTestData(){
        firestoreHelper.getAllGeneros {genero ->
        generos.addAll(genero!!)
        if (generos.isEmpty()){
            val shooterGenero = Genero(
                name = "Shooter",
                subgenero = "Acción",
                fps = 64,
                camaraLibre = true
            )
            val HySGenero = Genero(
                name = "Hack and Slash",
                subgenero = "Peleas",
                fps = 16,
                camaraLibre = true
            )
            val rpgGenero = Genero(
                name = "RPG",
                subgenero = "Aventura",
                fps = 16,
                camaraLibre = false
            )
            val survivalGenero = Genero(
                name = "Survival",
                subgenero = "Manejo de recursos",
                fps = 64,
                camaraLibre = true
            )
            generos.add(shooterGenero)
            generos.add(HySGenero)
            generos.add(rpgGenero)
            generos.add(survivalGenero)

            //Crear videojuegos para los géneros
            firestoreHelper.crearGenero(shooterGenero){generoId ->
                if (generoId != null){
                    shooterGenero.id = generoId
                    Log.d("MainActivity", "Genero agregado: $shooterGenero")
                    val nuevoJuego = Videojuego(
                        name = "Overwatch 2",
                        anioRelease = 2022,
                        rating = "T",
                        casaDesarrolladora = "Blizzard"
                    )
                    firestoreHelper.crearVideojuego(nuevoJuego){videojuegoId ->
                        if(videojuegoId != null){
                            nuevoJuego.id =videojuegoId
                            Log.d("MainActivity", "Videojuego agregado: $nuevoJuego")
                        }else{
                            Log.e("MainActivity", "Error en crear juego")
                        }
                    }
                }else{
                    Log.d("MainActivity", "Error en crear Género")
                }

            }

            firestoreHelper.crearGenero(HySGenero){generoId ->
                if (generoId != null){
                    HySGenero.id = generoId
                    Log.d("MainActivity", "Genero agregado: $HySGenero")
                    val nuevoJuego = Videojuego(
                        name = "Devil May Cry 5",
                        anioRelease = 2019,
                        rating = "M",
                        casaDesarrolladora = "Capcom"
                    )
                    firestoreHelper.crearVideojuego(nuevoJuego){videojuegoId ->
                        if(videojuegoId != null){
                            nuevoJuego.id =videojuegoId
                            Log.d("MainActivity", "Videojuego agregado: $nuevoJuego")
                        }else{
                            Log.e("MainActivity", "Error en crear juego")
                        }
                    }
                }else{
                    Log.d("MainActivity", "Error en crear Género")
                }

            }

            firestoreHelper.crearGenero(rpgGenero){generoId ->
                if (generoId != null){
                    rpgGenero.id = generoId
                    Log.d("MainActivity", "Genero agregado: $rpgGenero")
                    val nuevoJuego = Videojuego(
                        name = "Persona 5 Royale",
                        anioRelease = 2022,
                        rating = "M",
                        casaDesarrolladora = "SEGA"
                    )
                    firestoreHelper.crearVideojuego(nuevoJuego){videojuegoId ->
                        if(videojuegoId != null){
                            nuevoJuego.id =videojuegoId
                            Log.d("MainActivity", "Videojuego agregado: $nuevoJuego")
                        }else{
                            Log.e("MainActivity", "Error en crear juego")
                        }
                    }
                }else{
                    Log.d("MainActivity", "Error en crear Género")
                }

            }

            firestoreHelper.crearGenero(survivalGenero){generoId ->
                if (generoId != null){
                    survivalGenero.id = generoId
                    Log.d("MainActivity", "Genero agregado: $survivalGenero")
                    val nuevoJuego = Videojuego(
                        name = "Grounded",
                        anioRelease = 2022,
                        rating = "T",
                        casaDesarrolladora = "Obsidian"
                    )
                    firestoreHelper.crearVideojuego(nuevoJuego){videojuegoId ->
                        if(videojuegoId != null){
                            nuevoJuego.id =videojuegoId
                            Log.d("MainActivity", "Videojuego agregado: $nuevoJuego")
                        }else{
                            Log.e("MainActivity", "Error en crear juego")
                        }
                    }
                }else{
                    Log.d("MainActivity", "Error en crear Género")
                }

            }

        }

        }
    }

}