package com.adrianaenriquez.examen2b.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.*
import androidx.core.app.NavUtils
import com.adrianaenriquez.examen2b.firestore.FirestoreHelper
import com.adrianaenriquez.examen2b.R
import com.adrianaenriquez.examen2b.adapter.VideojuegoAdapter
import com.adrianaenriquez.examen2b.model.Videojuego

class CrearVideojuegoActivity : AppCompatActivity() {

    private val firestoreHelper = FirestoreHelper()
    private var generoId: String? = null
    private lateinit var videojuegoAdapter: VideojuegoAdapter

    /* Methods */
    /* ---------------------------------------------- */
    @SuppressLint("MissingInflatedId", "NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_videojuego)

        val nombreVideojuegoEditText: EditText = findViewById(R.id.create_videojuego_nombre)
        val anioReleaseEditText: EditText = findViewById(R.id.create_videojuego_anio)
        val casaDesarrolladoraEditText: EditText = findViewById(R.id.create_videojuego_casa)
        val buttonCrearVideojuego: Button = findViewById(R.id.button_guardar_videojuego)


        val spinnerRatingVideojuego: Spinner = findViewById(R.id.spinner_videojuego_rating)

        // Set the spinner options
        setSpinnerOptions(spinnerRatingVideojuego)

        // Load data from Firestore and set it to the adapter
        generoId = intent.getStringExtra("generoId")

        buttonCrearVideojuego.setOnClickListener {
            val nombre = nombreVideojuegoEditText.text.toString()
            val anioRelease = anioReleaseEditText.text.toString().toInt()
            val rating = spinnerRatingVideojuego.selectedItem.toString()
            val casa = casaDesarrolladoraEditText.text.toString()

            val nuevoVideojuego = Videojuego(
                name = nombre,
                anioRelease = anioRelease,
                rating = rating,
                casaDesarrolladora = casa
            )

            firestoreHelper.crearVideojuego(nuevoVideojuego) { videojuegoId ->
                if (videojuegoId != null) {
                    videojuegoAdapter = VideojuegoAdapter(emptyList())
                    nuevoVideojuego.id = videojuegoId
                    firestoreHelper.obtenerVideojuegoByGeneroId(generoId!!) { videojuegos ->
                        videojuegoAdapter.updateData(videojuegos!!)
                        videojuegoAdapter.notifyDataSetChanged()
                        Toast.makeText(
                            this, "Videojuego creado con satisfacción",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    val intent =
                        Intent(this@CrearVideojuegoActivity, VideojuegoListActivity::class.java)
                    intent.putExtra("GeneroId", generoId)
                    startActivity(intent)
                    Log.d("MainActivity", "Agregado el videojuego: $nuevoVideojuego")
                } else {
                    Toast.makeText(
                        this,
                        "Error creando el videojuego", Toast.LENGTH_SHORT
                    ).show()
                    Log.e("MainActivity", "Error agregando videojuego")
                }
            }
        }
    }

    private fun setSpinnerOptions(spinner: Spinner) {
        val options = resources.getStringArray(R.array.rating_options).toMutableList()
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, options)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                val intent = Intent(this, VideojuegoListActivity::class.java)
                intent.putExtra("generoId", generoId)
                NavUtils.navigateUpTo(this, intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
