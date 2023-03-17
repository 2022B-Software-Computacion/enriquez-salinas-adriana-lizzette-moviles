package com.adrianaenriquez.proyecto2b

import android.content.Context
import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.adrianaenriquez.proyecto2b.Videojuego

class DataHelper(private val context : Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object{
        private val DATABASE_NAME = "proyecto.db"
        private val DATABASE_VERSION = 1
        val TABLE_NAME = "VIDEOJUEGO"
        val KEY_NAME = "nombre"
        val KEY_ANIO = "anio de salida"
        val KEY_GENERO = "genero"
        val KEY_CASA = "casa desarrolladora"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_TABLE = ("CREATE TABLE" +
                TABLE_NAME + "("
                + "NOMBRE" + "PRIMARY KEY," +
                "AÑO" + "INTEGER" +
                "GENERO" + "TEXT" +
                "CASA DESARROLLADORA" + "TEXT"
                )
        db.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME)
        onCreate(db)
    }

    fun addVideojuego(videojuego: Videojuego): Boolean{
        var db = this.writableDatabase
        var values = ContentValues()
        values.put(KEY_NAME,videojuego.name)
        values.put(KEY_ANIO,videojuego.anioRelease)
        values.put(KEY_GENERO, videojuego.genero)
        values.put(KEY_CASA, videojuego.casaDesarrolladora)
        val success = db.insert(TABLE_NAME, null,values)
        db.close()
        if(success.toInt() ==-1){
            Toast.makeText(context,"Error al crear nuevo videojuego, intente de nuevo",
            Toast.LENGTH_SHORT).show()
            return false
        }else{
            Toast.makeText(context,"Se ha creado el videojuego con exito",
            Toast.LENGTH_SHORT).show()
            return true
        }
    }
    fun deleteVideojuego(videojuego: Videojuego){
        var db = this.writableDatabase
        var selectionArgs = arrayOf(videojuego.name.toString())
        db.delete(TABLE_NAME, KEY_NAME+"=?",selectionArgs)

    }

    fun getAllVideojuegos():ArrayList<Videojuego>{
        var db = this.writableDatabase
        var videojuegoList : ArrayList<Videojuego> = ArrayList()
        val selectAll = "SELECT * FROM "+ TABLE_NAME
        val cursor = db.rawQuery(selectAll,null)
        if (cursor.moveToFirst()){
            do{
                val videojuego = Videojuego()
                videojuego.name = cursor.getString(0)
                videojuego.anioRelease = cursor.getInt(1)
                videojuego.genero = cursor.getString(2)
                videojuego.casaDesarrolladora = cursor.getString(3)
                videojuegoList.add(videojuego)
            }while (cursor.moveToNext())
        }
        return videojuegoList
    }


}