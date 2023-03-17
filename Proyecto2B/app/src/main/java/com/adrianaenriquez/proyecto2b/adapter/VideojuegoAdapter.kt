package com.adrianaenriquez.proyecto2b.adapter

import android.app.AlertDialog
import android.content.Context
import android.provider.MediaStore.Video
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.adrianaenriquez.proyecto2b.DataHelper
import com.adrianaenriquez.proyecto2b.R
import com.adrianaenriquez.proyecto2b.Videojuego
import kotlinx.android.synthetic.main.item_videojuego.view.*


class VideojuegoAdapter
    (private val context: Context, private var videojuegoList: ArrayList<Videojuego>)
    : RecyclerView.Adapter<VideojuegoAdapter.ViewHolder>()
{
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): VideojuegoAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_videojuego,parent,false))
    }

    override fun getItemCount(): Int {
        return videojuegoList.size
    }

    override fun onBindViewHolder(holder: VideojuegoAdapter.ViewHolder, position: Int) {
        holder.onBind(videojuegoList[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun onBind(videojuego: Videojuego){
            itemView.item_anio.text = videojuego.anioRelease.toString()
            itemView.item_name.text = videojuego.name
            itemView.item_casa.text = videojuego.casaDesarrolladora
            itemView.item_genero.text = videojuego.genero
            itemView.setOnLongClickListener{
                val alertDialogBuilder = AlertDialog.Builder(itemView.context)
                alertDialogBuilder.setTitle("Confirmar")
                    .setMessage("Está seguro de eliminar "+videojuego.name+"?")
                    .setCancelable(true)
                    .setPositiveButton("No"){dialog,which->
                        Toast.makeText(itemView.context, "Cancelar la eliminacion ", Toast.LENGTH_SHORT).show()
                    }
                    .setNegativeButton("Yes"){dialog,which->
                        val db = DataHelper(itemView.context)
                        db.deleteVideojuego(videojuego)
                        videojuegoList.remove(videojuego)
                        notifyDataSetChanged()
                        Toast.makeText(itemView.context,"Eliminación exitosa",Toast.LENGTH_SHORT).show()
                    }
                val alertDialog = alertDialogBuilder.create()
                alertDialog.show()
                true
            }
        }
    }

    fun getUpdate(){
        val db = DataHelper(context)
        videojuegoList = db.getAllVideojuegos()
        notifyDataSetChanged()
    }
}