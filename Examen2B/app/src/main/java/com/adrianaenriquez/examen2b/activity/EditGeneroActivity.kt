package com.adrianaenriquez.examen2b.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.adrianaenriquez.examen2b.R
import com.adrianaenriquez.examen2b.adapter.GeneroAdapter
import com.adrianaenriquez.examen2b.firestore.FirestoreHelper
import com.adrianaenriquez.examen2b.model.Genero

class EditGeneroActivity : AppCompatActivity() {

    private var generoId: String? = null
    private val firestoreHelper = FirestoreHelper()
    private lateinit var generoAdapter: GeneroAdapter

    @SuppressLint("NotifyDataSetChanged", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_genero)

        val nameEditText : EditText = findViewById(R.id.editTxt_genero_name)
        val subgeneroEditText: EditText =findViewById(R.id.editTxt_subgenero)
        val spinnerFps: Spinner = findViewById(R.id.spinner_select_fps)
        val camaraLibre: CheckBox = findViewById(R.id.checkbox_camara_libre)
        val guardar: Button = findViewById(R.id.button_guardar_genero)

        initSpinnerOptions(spinnerFps)

        generoId = intent.getStringExtra("GeneroId")
        if (generoId != null){
            setGeneroName(generoId!!,nameEditText)
            setGeneroSubgenero(generoId!!,subgeneroEditText)
            setFpsSpinner(generoId!!,spinnerFps)
            setCamaraLibreCheckbox(generoId!!, camaraLibre)
        }

        guardar.setOnClickListener{
            val generoName = nameEditText.text.toString()
            val subgeneroName = subgeneroEditText.text.toString()
            val fps = spinnerFps.selectedItem.toString().toInt()
            val camaraLibre = camaraLibre.isChecked

            val updatedGenero = Genero(
                id = generoId,
                name = generoName,
                subgenero = subgeneroName,
                fps = fps,
                camaraLibre = camaraLibre
            )
            firestoreHelper.editarGenero(generoId!!, updatedGenero) {isSuccess ->
                if (isSuccess) {
                    generoAdapter = GeneroAdapter(emptyList())
                    firestoreHelper.getAllGeneros { generos ->
                        generoAdapter.updateData(generos!!)
                        generoAdapter.notifyDataSetChanged()
                        Toast.makeText(
                            this,
                            "Genero Actualizado con exito", Toast.LENGTH_SHORT
                        ).show()

                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }
                } else {
                    Toast.makeText(
                        this,
                        "Error actualizando genero",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun initSpinnerOptions(spinner: Spinner){
        val options = resources.getStringArray(R.array.fps_opciones).toMutableList()
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, options)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
    }

    private fun setCamaraLibreCheckbox(generoId: String, checkBox: CheckBox) {
        firestoreHelper.getGeneroById(generoId) { genero ->
            genero?.let {
                checkBox.isChecked = it.camaraLibre!!
            }
        }
    }

    private fun setFpsSpinner(generoId: String, spinner: Spinner) {
        firestoreHelper.getGeneroById(generoId) { genero ->
            genero?.let {
                val statusOptions = resources.getIntArray(R.array.fps_opciones)
                val defaultFpsIndex = it.fps?.let { it1 -> statusOptions.indexOf(it1) }
                if (defaultFpsIndex != null) {
                    spinner.setSelection(defaultFpsIndex)
                }
            }
        }
    }

    private fun setGeneroName(generoId: String, editText: EditText) {
        firestoreHelper.getGeneroById(generoId) { brand ->
            brand?.let {
                editText.setText(it.name)
            }
        }
    }

    private fun setGeneroSubgenero(brandId: String, editText: EditText) {
        firestoreHelper.getGeneroById(brandId) { genero ->
            genero?.let {
                editText.setText(it.subgenero)
            }
        }
    }

}