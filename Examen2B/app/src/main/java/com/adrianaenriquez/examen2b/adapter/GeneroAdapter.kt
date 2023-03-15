package com.adrianaenriquez.examen2b.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.View.OnGenericMotionListener
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.adrianaenriquez.examen2b.R
import com.adrianaenriquez.examen2b.firestore.FirestoreHelper
import com.adrianaenriquez.examen2b.model.Genero
import com.google.firebase.firestore.ListenerRegistration

class GeneroAdapter (
     private var generos: List<Genero>
): RecyclerView.Adapter<GeneroAdapter.GeneroViewHolder>(){
    /*Atributos*/
    private lateinit var context: Context
    private lateinit var firestoreHelper: FirestoreHelper
    private lateinit var listenerRegistration: ListenerRegistration
    private lateinit var generoAdapter: GeneroAdapter
    private var onGeneroClickListener: OnGeneroClickListener? = null

    /*Metodos*/
    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        context = recyclerView.context
        firestoreHelper = FirestoreHelper()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GeneroAdapter.GeneroViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_genero, parent, false
        )
        generoAdapter = GeneroAdapter(generos)
        return GeneroViewHolder(view)
    }

    override fun onBindViewHolder(holder: GeneroViewHolder, position: Int) {
        val genero = generos[position]
        holder.bind(genero)
    }

    override fun getItemCount(): Int {
        return generos.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newGeneros: List<Genero>){
        generos = newGeneros
        notifyDataSetChanged()
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        listenerRegistration.remove()
    }

    fun setOnGeneroClickListener(listener: OnGeneroClickListener){
        onGeneroClickListener = listener
    }

    interface OnGeneroClickListener{
        fun onGeneroClick(generoId: String)
        fun onOptionsItemSelected(item: MenuItem): Boolean
    }

   /*Clases*/
    inner class GeneroViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
        private val generoNameTextView: TextView = itemView.findViewById(R.id.generoNameTextView)
        private val generoOptionsButton: ImageButton = itemView.findViewById(R.id.genero_options)

        fun bind(genero : Genero){
            generoNameTextView.text = genero.name
            generoOptionsButton.setOnClickListener{
                showGeneroOptionsMenu(genero)
            }
        }

        @SuppressLint("NotifyDataSetChanged")
        private fun showGeneroOptionsMenu(genero : Genero){
            val menuPopUp = PopupMenu(itemView.context, generoOptionsButton)
            menuPopUp.menuInflater.inflate(R.menu.genero_options_menu, menuPopUp.menu)
            menuPopUp.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId){
                    R.id.editar_genero_menu_item -> {
                        onGeneroClickListener?.onGeneroClick(genero.id!!)
                        onGeneroClickListener?.onOptionsItemSelected(menuItem)
                        true
                    }
                    R.id.eliminar_genero_menu_item -> {
                        onGeneroClickListener?.onGeneroClick(genero.id!!)
                        onGeneroClickListener?.onOptionsItemSelected(menuItem)
                        true
                    }
                    R.id.ver_videojuegos_menu_item -> {
                        onGeneroClickListener?.onGeneroClick(genero.id!!)
                        onGeneroClickListener?.onOptionsItemSelected(menuItem)
                        true
                    }else -> false
                }
            }
            menuPopUp.show()
        }

    }
}

