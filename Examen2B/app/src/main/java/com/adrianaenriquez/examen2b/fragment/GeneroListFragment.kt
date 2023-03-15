package com.adrianaenriquez.examen2b.fragment

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.adrianaenriquez.examen2b.R
import com.adrianaenriquez.examen2b.activity.EditGeneroActivity
import com.adrianaenriquez.examen2b.activity.EditVideojuegoActivity
import com.adrianaenriquez.examen2b.activity.VideojuegoListActivity
import com.adrianaenriquez.examen2b.adapter.GeneroAdapter
import com.adrianaenriquez.examen2b.adapter.VideojuegoAdapter
import com.adrianaenriquez.examen2b.firestore.FirestoreHelper

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class GeneroListFragment: Fragment(), GeneroAdapter.OnGeneroClickListener{

    private var param1: String? = null
    private var param2: String? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var generoAdapter: GeneroAdapter
    private val firestoreHelper = FirestoreHelper()
    private var selectedGeneroId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    override fun onGeneroClick(generoId: String) {
        selectedGeneroId = generoId
        generoAdapter.notifyDataSetChanged()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_genero_list,container,false)
        recyclerView = view.findViewById(R.id.generoRV)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        generoAdapter = GeneroAdapter(emptyList())
        generoAdapter.setOnGeneroClickListener(this)
        recyclerView.adapter = generoAdapter

        firestoreHelper.getAllGeneros { generos ->
        generoAdapter.updateData(generos!!)

        }
    }


    @SuppressLint("NotifyDataSetChanged")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.editar_videojuego_menu_item -> {
                val intent = Intent(context, EditGeneroActivity::class.java)
                intent.putExtra("generoId", selectedGeneroId)
                startActivity(intent)
                true
            }
            R.id.eliminar_genero_menu_item ->{
                AlertDialog.Builder(context).setMessage("Quiere eliminar el genero?")
                    .setPositiveButton("Eliminar"){dialogo, _ ->
                        firestoreHelper.eliminarGenero(selectedGeneroId!!){isSuccess ->
                            if (isSuccess){
                                firestoreHelper.getAllGeneros{ generos ->
                                    generoAdapter.updateData(generos!!)
                                    generoAdapter.notifyDataSetChanged()
                                    Toast.makeText(
                                        context,"Genero Eliminado con éxito", Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }else {
                                Toast.makeText(
                                    context,"Error en eliminar el Genero",
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
            R.id.ver_videojuegos_menu_item ->{
                val intent = Intent(context, VideojuegoListActivity::class.java)
                intent.putExtra("generoId", selectedGeneroId)
                startActivity(intent)
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

}