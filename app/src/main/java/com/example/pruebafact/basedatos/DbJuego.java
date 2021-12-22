package com.example.pruebafact.basedatos;


import static com.example.pruebafact.tools.Constantes.*;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.pruebafact.modelos.Juego;

import java.util.ArrayList;

public class DbJuego extends DbHelper {
    public DbJuego(@Nullable Context context) {
        super(context);
    }

    public boolean agregarJuego(Juego juego) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(ID, juego.getId());
        cv.put(TITULO, juego.getTitulo());
        cv.put(NOMBRE, juego.getNombre());
        cv.put(ANHO, juego.getAnho());
        cv.put(PRECIO, juego.getPrecio());
        cv.put(DIRECTOR, juego.getDirector());
        cv.put(PRODUCTOR, juego.getProductor());
        cv.put(TECNOLOGIA, juego.getTecnologia());
        cv.put(IMAGEN, juego.getImagen());

        if (db.insert(TABLA_JUEGO, null, cv) != 1) {
            return true;
        }
        return false;
    }

    public ArrayList<Juego> leerJuegos() {
        ArrayList<Juego> lista = new ArrayList<>();
        String query =
                "SELECT * FROM " + TABLA_JUEGO;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
            while (cursor.moveToNext()) {
                Juego juego = new Juego();
                juego.setId(cursor.getString(0));
                juego.setTitulo(cursor.getString(1));
                juego.setNombre(cursor.getString(2));
                juego.setAnho(cursor.getString(3));
                juego.setPrecio(cursor.getString(4));
                juego.setDirector(cursor.getString(5));
                juego.setProductor(cursor.getString(6));
                juego.setTecnologia(cursor.getString(7));
                juego.setImagen(cursor.getString(8));
                lista.add(juego);
            }
        }
        return lista;
    }

    public Juego leerJuegosPorId(String id) {
        String query =
                "SELECT * FROM " + TABLA_JUEGO + " where " + ID + "='" + id + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Juego juego = new Juego();
        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
            while (cursor.moveToNext()) {

                juego.setId(cursor.getString(0));
                juego.setTitulo(cursor.getString(1));
                juego.setNombre(cursor.getString(2));
                juego.setAnho(cursor.getString(3));
                juego.setPrecio(cursor.getString(4));
                juego.setDirector(cursor.getString(5));
                juego.setProductor(cursor.getString(6));
                juego.setTecnologia(cursor.getString(7));
                juego.setImagen(cursor.getString(8));

            }
        }
        return juego;
    }
}
