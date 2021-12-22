package com.example.pruebafact.basedatos;

import static com.example.pruebafact.tools.Constantes.CEDULA;
import static com.example.pruebafact.tools.Constantes.CORREO;
import static com.example.pruebafact.tools.Constantes.FECHA;
import static com.example.pruebafact.tools.Constantes.ID;
import static com.example.pruebafact.tools.Constantes.NOMBRE;
import static com.example.pruebafact.tools.Constantes.NOMBREJUEGO;
import static com.example.pruebafact.tools.Constantes.OPCION;
import static com.example.pruebafact.tools.Constantes.TABLA_DATO;
import static com.example.pruebafact.tools.Constantes.TABLA_JUEGO;
import static com.example.pruebafact.tools.Constantes.TELEFONO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.pruebafact.modelos.Datos;
import com.example.pruebafact.modelos.Juego;

import java.util.ArrayList;

public class DbDatos extends DbHelper {
    public DbDatos(@Nullable Context context) {
        super(context);
    }

    public boolean agregarDatos(Datos datos) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(NOMBRE, datos.getNombre());
        cv.put(CEDULA, datos.getCedula());
        cv.put(TELEFONO, datos.getTelefono());
        cv.put(CORREO, datos.getCorreo());
        cv.put(NOMBREJUEGO, datos.getNombreJuego());
        cv.put(OPCION, datos.getOpcion());
        cv.put(FECHA, datos.getFecha());

        if (db.insert(TABLA_DATO, null, cv) != 1) {
            return true;
        }
        return false;
    }

    public ArrayList<Datos> leerDatosPorCedula(String cedula) {
        ArrayList<Datos> lista = new ArrayList<>();
        String query =
                "SELECT * FROM " + TABLA_DATO + " where " + CEDULA + " = '" + cedula + "'";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
            while (cursor.moveToNext()) {
                Datos datos = new Datos();
                datos.setNombre(cursor.getString(1));
                datos.setCedula(cursor.getString(2));
                datos.setTelefono(cursor.getString(3));
                datos.setCorreo(cursor.getString(4));
                datos.setNombreJuego(cursor.getString(5));
                datos.setOpcion(cursor.getString(6));
                datos.setFecha(cursor.getString(7));
                lista.add(datos);
            }
        }
        return lista;
    }
    public Datos leerDatosPorCC(String cedula) {
        String query =
                "SELECT * FROM " + TABLA_JUEGO + " where " + CEDULA + "='" + cedula + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Datos datos = new Datos();
        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
            while (cursor.moveToNext()) {
                datos.setNombre(cursor.getString(1));
                datos.setCedula(cursor.getString(2));
                datos.setTelefono(cursor.getString(3));
                datos.setCorreo(cursor.getString(4));
                datos.setNombreJuego(cursor.getString(5));
                datos.setOpcion(cursor.getString(6));
                datos.setFecha(cursor.getString(7));

            }
        }
        return datos;
    }
}
