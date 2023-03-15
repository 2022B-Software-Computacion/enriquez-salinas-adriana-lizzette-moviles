package com.adrianaenriquez.examen2b.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.adrianaenriquez.examen2b.R
import com.adrianaenriquez.examen2b.firestore.FirestoreHelper
import com.adrianaenriquez.examen2b.fragment.VideojuegoListFragment
import com.google.firebase.firestore.ktx.firestoreSettings

class VideojuegoListActivity : AppCompatActivity() {
    private val firestoreHelper = FirestoreHelper()

@SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_videojuego_list)
        val titleTextView: TextView = findViewById(R.id.videojuegosNameTitle)

        val generoId = intent.getStringExtra("generoId")
        val bundle = Bundle()
        val fragmentTransaction = fragmentManager.beginTransaction()
        val videojuegoListFragment = VideojuegoListFragment()

        setGeneroName(generoId!!, titleTextView)
        bundle.putInt("crearVideojuego", R.id.nuevoVideojuego)
        bundle.putString("GeneroId", generoId)
        videojuegoListFragment.arguments = bundle
        fragmentTransaction.add(R.id.fragment_videojuego, videojuegoListFragment)
        fragmentTransaction.commit()
    }
    @SuppressLint("SetTextI18n")
    private fun setGeneroName(brandId: String, textView: TextView) {
        firestoreHelper.getGeneroById(brandId) { brand ->
            brand?.let {
                textView.text = "${it.name} Videojuegos"
            }
        }
    }

}