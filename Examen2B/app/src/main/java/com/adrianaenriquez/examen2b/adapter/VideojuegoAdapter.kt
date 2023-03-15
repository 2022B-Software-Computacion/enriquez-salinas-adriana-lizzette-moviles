package com.adrianaenriquez.examen2b.adapter

import com.adrianaenriquez.examen2b.model.Videojuego
import android.annotation.SuppressLint
import android.content.Context
import android.provider.MediaStore.Video
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.adrianaenriquez.examen2b.R
import com.adrianaenriquez.examen2b.firestore.FirestoreHelper
import com.adrianaenriquez.examen2b.model.Genero
import com.google.firebase.firestore.ListenerRegistration

class VideojuegoAdapter (
    /*Atributos*/
    private var videojuegos: List<Videojuego>
        ): RecyclerView.Adapter<VideojuegoAdapter.ViewHolder>(){
    private lateinit var context: Context
    private lateinit var firestoreHelper: FirestoreHelper
    private lateinit var listenerRegistration: ListenerRegistration
    private lateinit var videojuegoAdapter: VideojuegoAdapter
    private var onVideojuegoClickListener: OnVideojuegoClickListener? = null

    /*Metodos*/
    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        context = recyclerView.context
        firestoreHelper = FirestoreHelper()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideojuegoAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_videojuego, parent, false
        )
        videojuegoAdapter = VideojuegoAdapter(videojuegos)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: VideojuegoAdapter.ViewHolder, position: Int) {
        val videojuego = videojuegos[position]
        holder.bind(videojuego)
    }

    override fun getItemCount(): Int {
        return videojuegos.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newVideojuegos: List<Videojuego>){
        videojuegos = newVideojuegos
        notifyDataSetChanged()
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        listenerRegistration.remove()
    }

    fun setOnVideojuegoClickListener(listener: OnVideojuegoClickListener){
        onVideojuegoClickListener = listener
    }

    interface OnVideojuegoClickListener{
        fun onVideojuegoClick(videojuegoId: String)
        fun onOptionsItemSelected(item: MenuItem): Boolean
    }

/*Clases*/

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val videojuegoNameTextView : TextView = itemView.findViewById(
            R.id.videojuegoNameTextView
        )
        private val videojuegoOptionsButtons: ImageButton = itemView.findViewById(
            R.id.videojuego_options
        )
        fun bind (videojuego: Videojuego){
            videojuegoNameTextView.text = videojuego.name
            videojuegoOptionsButtons.setOnClickListener{
                showVideojuegoOptionsMenu(videojuego)
            }
        }

        private fun showVideojuegoOptionsMenu(videojuego: Videojuego){
            val menuPopUp = PopupMenu(itemView.context, videojuegoOptionsButtons)
            menuPopUp.menuInflater.inflate(R.menu.videojuego_options_menu,menuPopUp.menu)
            menuPopUp.setOnMenuItemClickListener { menuItem ->
                when(menuItem.itemId){
                    R.id.editar_genero_menu_item -> {
                        onVideojuegoClickListener?.onVideojuegoClick(videojuego.id!!)
                        onVideojuegoClickListener?.onOptionsItemSelected(menuItem)
                        true
                    }R.id.eliminar_genero_menu_item -> {
                        onVideojuegoClickListener?.onVideojuegoClick(videojuego.id!!)
                    onVideojuegoClickListener?.onOptionsItemSelected(menuItem)
                    true
                    }else -> false
                }
            }
            menuPopUp.show()
        }

    }

}