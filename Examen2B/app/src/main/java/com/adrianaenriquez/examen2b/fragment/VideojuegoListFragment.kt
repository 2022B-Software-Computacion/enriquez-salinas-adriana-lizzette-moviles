package com.adrianaenriquez.examen2b.fragment

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.AbsSavedState
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.adrianaenriquez.examen2b.R
import com.adrianaenriquez.examen2b.activity.CrearVideojuegoActivity
import com.adrianaenriquez.examen2b.activity.CrearGeneroActivity
import com.adrianaenriquez.examen2b.activity.EditVideojuegoActivity
import com.adrianaenriquez.examen2b.adapter.VideojuegoAdapter
import com.adrianaenriquez.examen2b.firestore.FirestoreHelper

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class VideojuegoListFragment: Fragment(), VideojuegoAdapter.OnVideojuegoClickListener {

    private var param1: String? = null
    private var param2: String? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var videojuegoAdapter: VideojuegoAdapter
    private val firestoreHelper = FirestoreHelper()
    private var selectedVideojuegoId: String? = null
    private var generoId: String? = null

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onVideojuegoClick(videojuegoId: String) {
        selectedVideojuegoId = videojuegoId
        videojuegoAdapter.notifyDataSetChanged()
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_videojuego_list,container,false)
        recyclerView = view.findViewById(R.id.videojuegoRV)
        recyclerView.layoutManager = LinearLayoutManager(context)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        videojuegoAdapter = VideojuegoAdapter(emptyList())
        videojuegoAdapter.setOnVideojuegoClickListener(this)

        generoId = arguments?.getString("generoId")
        if (generoId != null){
            firestoreHelper.obtenerVideojuegoByGeneroId(generoId!!){videojuegos ->
                if (videojuegos != null) {
                    videojuegoAdapter.updateData(videojuegos)
                }
            }
        }

        val crearVideojuegoButtonId = arguments?.getInt("crearVideojuegoButtonId")
        activity?.findViewById<Button>(crearVideojuegoButtonId!!)?.setOnClickListener(){
            val intent = Intent(context, CrearVideojuegoActivity::class.java)
            intent.putExtra("generoId", generoId)
            startActivity(intent)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.editar_videojuego_menu_item -> {
                val intent = Intent(context, EditVideojuegoActivity::class.java)
                intent.putExtra("generoId", generoId)
                intent.putExtra("videojuegoId",selectedVideojuegoId)
                startActivity(intent)
                true
            }
            R.id.eliminar_videojuego_menu_item ->{
                AlertDialog.Builder(context).setMessage("Quiere eliminar el videojuego?")
                    .setPositiveButton("Eliminar"){dialogo, _ ->
                        firestoreHelper.eliminarVideojuego(
                            selectedVideojuegoId!!, generoId!!
                        ){isSuccess ->
                            if (isSuccess){
                                firestoreHelper.obtenerVideojuegoByGeneroId(selectedVideojuegoId!!){
                                    videojuegos ->
                                    if (videojuegos != null) {
                                        videojuegoAdapter.updateData(videojuegos)
                                    }
                                    Toast.makeText(
                                        context,"Videojuego Eliminado con éxito", Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }else {
                                Toast.makeText(
                                    context,"Error en eliminar el videojuego",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                        dialogo.dismiss()
                    }
                    .setNegativeButton("Cancelar") { dialogo, _ ->
                        dialogo.dismiss()
                    }
                    .show()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

}