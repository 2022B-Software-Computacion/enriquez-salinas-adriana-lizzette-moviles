package com.adrianaenriquez.proyecto2b

import android.icu.number.IntegerWidth
import android.os.Bundle
import android.os.PersistableBundle
import android.provider.MediaStore.Video
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_insert.*
import kotlinx.android.synthetic.main.component_button_insert_conf.*

class InsertActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert)
        val dataHelper = DataHelper(this)
        btn_insert.setOnClickListener{
            val alertDialogBuilder = AlertDialog.Builder(this)
            alertDialogBuilder.setTitle("Confirmar")
                .setMessage("Seguro que desea crear nuevo videojuego")
                .setCancelable(true)
                .setPositiveButton("No"){
                    dialog,which ->

                }
                .setNegativeButton("Si"){dialog,which ->
                    val name = videojuego_nombre.text.toString()
                    val anio = Integer.parseInt(anio.text.toString())
                    val genero = genero_videojuego.text.toString()
                    val casaD = casa.text.toString()
                    dataHelper.addVideojuego(Videojuego(name,anio,genero,casaD))
                    videojuego_nombre.setText("")
                    casa.setText("")
                    genero_videojuego.setText("")
                    finish()
                }
            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
        }
    }


}