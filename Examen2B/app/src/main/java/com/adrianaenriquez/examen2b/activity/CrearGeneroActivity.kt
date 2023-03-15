package com.adrianaenriquez.examen2b.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import com.adrianaenriquez.examen2b.R
import com.adrianaenriquez.examen2b.adapter.GeneroAdapter
import com.adrianaenriquez.examen2b.firestore.FirestoreHelper
import com.adrianaenriquez.examen2b.model.Genero

class CrearGeneroActivity : AppCompatActivity() {

    private val firestoreHelper = FirestoreHelper()
    private lateinit var generoAdapter: GeneroAdapter


    @SuppressLint("MissingInflatedId","NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_genero)

       val nameEditText: EditText = findViewById(R.id.editarTxt_crear_genero_name)
       val subEditTest: EditText = findViewById(R.id.editarTxt_crear_subg_name)
       val fpsGenero: Spinner = findViewById(R.id.spinner_select_fps)
       val camaraLibre: CheckBox = findViewById(R.id.checkbox_camara_libre)
       val saveGenero: Button = findViewById(R.id.button_guardar_genero)


       setSpinnerOptions(fpsGenero)

       saveGenero.setOnClickListener{
           val generoName = nameEditText.toString()
           val subName = subEditTest.toString()
           val fpsSelect = fpsGenero.selectedItem.toString().toInt()
           val camaraLibre = camaraLibre.isChecked

           val newGenero = Genero(
               name = generoName,
               subgenero = subName,
               fps = fpsSelect,
               camaraLibre = camaraLibre
           )

           firestoreHelper.crearGenero(newGenero){ generoId ->
               if (generoId!= null){
                   newGenero.id = generoId
                   generoAdapter = GeneroAdapter(emptyList())
                   firestoreHelper.getAllGeneros { generos ->
                       generoAdapter.updateData(generos!!)
                       generoAdapter.notifyDataSetChanged()
                       Toast.makeText(this, "Genero creado con exito", Toast.LENGTH_SHORT)
                           .show()
                       val intent = Intent(this,MainActivity::class.java)
                       startActivity(intent)
                   }
                   Log.d("MainActivity", "Genero Creado: $newGenero")
               } else {
                   Toast.makeText(
                       this, "Error creando el género", Toast.LENGTH_SHORT
                   ).show()
                   Log.e("MainActivity", "Error creando el género")
               }

               val intent = Intent(this, MainActivity::class.java)
               startActivity(intent)
           }
       }
    }

    private fun setSpinnerOptions(spinner: Spinner) {
        val options = resources.getStringArray(R.array.fps_opciones).toMutableList()
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, options)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
    }


}