package com.adrianaenriquez.proyecto2b

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.adrianaenriquez.proyecto2b.adapter.VideojuegoAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.component_button_insert.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_insert_videojuego.setOnClickListener{
            val intent = Intent(this, InsertActivity::class.java)
            startActivity(intent)
        }
        val dataHelper = DataHelper(this)
        Log.d("Leer", "Leero todos los datos")
        val videojuegoList = dataHelper.getAllVideojuegos()
        var videojuegoAdapter = VideojuegoAdapter(this@MainActivity, videojuegoList)
        rv_videojuego.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = videojuegoAdapter
        }
        refresh_swipe.setOnRefreshListener{
            refresh_swipe.isRefreshing=false
            videojuegoAdapter.getUpdate()
        }
    }
}