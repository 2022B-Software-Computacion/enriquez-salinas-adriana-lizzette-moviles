package com.adrianaenriquez.examen2b.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.*
import androidx.core.app.NavUtils
import com.adrianaenriquez.examen2b.R
import com.adrianaenriquez.examen2b.adapter.VideojuegoAdapter
import com.adrianaenriquez.examen2b.firestore.FirestoreHelper
import com.adrianaenriquez.examen2b.model.Videojuego

class EditVideojuegoActivity : AppCompatActivity() {

    private val firestoreHelper = FirestoreHelper()
    private var videojuegoId: String? = null
    private var generoId: String? = null
    private lateinit var videojuegoAdapter: VideojuegoAdapter

    @SuppressLint("NotifyDataSetChanged", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_videojuego)

        val nombreVideojuegoEdit: EditText = findViewById(R.id.edit_videojuego_nombre)
        val anioReleaseEdit: EditText = findViewById(R.id.edit_videojuego_anio)
        val casaDesarrolladoraEdit: EditText = findViewById(R.id.edit_videojuego_casa)
        val spinnerRatingVideojuego: Spinner = findViewById(R.id.spinner_videojuego_rating)
        val buttonSaveVideojuego: Button = findViewById(R.id.button_guardar_videojuego)

        initSpinnerOptions(spinnerRatingVideojuego)

        generoId = intent.getStringExtra("generoId")
        videojuegoId = intent.getStringExtra("videojuegoId")

        if (generoId!= null && videojuegoId != null){
            setVideojuegoNombre(generoId!!, videojuegoId!!, nombreVideojuegoEdit)
            setAnioRelease(generoId!!, videojuegoId!!, anioReleaseEdit)
            setCasaDesarrolladora(generoId!!, videojuegoId!!, casaDesarrolladoraEdit)
            setVideojuegoRatingToSpinner(generoId!!, videojuegoId!!, spinnerRatingVideojuego)

        }

        buttonSaveVideojuego.setOnClickListener{
            val videojuegoName = nombreVideojuegoEdit.text.toString()
            val videojuegoRelease = anioReleaseEdit.text.toString().toInt()
            val casa = casaDesarrolladoraEdit.text.toString()
            val videojuegoRating = spinnerRatingVideojuego.selectedItem.toString()

            val updatedVideojuego = Videojuego(
                id = videojuegoId!!,
                generoId = generoId!!,
                name = videojuegoName,
                anioRelease = videojuegoRelease,
                casaDesarrolladora = casa,
                rating = videojuegoRating,
            )

            firestoreHelper.actualizarVideojuego(updatedVideojuego){exitoso ->
                if (exitoso){
                    videojuegoAdapter = VideojuegoAdapter(emptyList())
                    firestoreHelper.obtenerVideojuegoByGeneroId(generoId!!){videojuegos ->
                    if (videojuegos != null) {
                        videojuegoAdapter.updateData(videojuegos)
                    }
                    videojuegoAdapter.notifyDataSetChanged()
                    Toast.makeText(this, "Videojuego creado con exito", Toast.LENGTH_SHORT)
                        .show()
                        val intent = Intent(this, VideojuegoListActivity::class.java)
                        intent.putExtra("generoId", generoId)
                        startActivity(intent)
                    }
                } else{
                    Toast.makeText(this, "Error en actualizar Videojuego", Toast.LENGTH_SHORT).show()
                }

            }
        }


    }

    private fun initSpinnerOptions(spinner: Spinner) {
        val options = resources.getStringArray(R.array.rating_options).toMutableList()
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, options)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
    }

    private fun setVideojuegoRatingToSpinner(
        generoId: String, videojuegoId: String, spinner: Spinner
    ){
        firestoreHelper.obtenerVideojuegoById(generoId,videojuegoId){videojuego ->
            videojuego?.let{
                val videojuegoRatingOptions = resources.getStringArray(R.array.rating_options)
                val defaultRatinIndex = videojuegoRatingOptions.indexOf(it.rating)
                spinner.setSelection(defaultRatinIndex)
            }
        }
    }

    private fun setVideojuegoNombre(generoId: String, videojuegoId: String, editText: EditText){
        firestoreHelper.obtenerVideojuegoById(generoId,videojuegoId){
            videojuego -> videojuego?.let{
                editText.setText(it.name)
            }
        }
    }

    private fun setAnioRelease(generoId: String, videojuegoId: String, editText: EditText){
        firestoreHelper.obtenerVideojuegoById(generoId,videojuegoId){
            videojuego -> videojuego?.let{
                editText.setText(it.anioRelease.toString())
            }
        }
    }

    private fun setCasaDesarrolladora(generoId: String, videojuegoId: String, editText: EditText) {
        firestoreHelper.obtenerVideojuegoById(generoId, videojuegoId) { videojuego ->
            videojuego?.let {
                editText.setText(it.casaDesarrolladora)
            }
        }
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