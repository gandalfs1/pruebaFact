package com.example.pruebafact.basedatos;

import static com.example.pruebafact.tools.Constantes.*;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.pruebafact.modelos.Datos;
import com.example.pruebafact.modelos.Juego;

import java.util.ArrayList;

public class DbDatos extends DbHelper {
    Context context;
    public DbDatos(@Nullable Context context) {
        super(context);
        this.context = context;
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
        cv.put(ID, datos.getIdJuego());

        return db.insert(TABLA_DATO, null, cv) != -1;
    }

    public Datos leerDatosPorCedula(String cedula) {
        ArrayList<Juego> lista = new ArrayList<>();
        String query =
                "SELECT * FROM " + TABLA_DATO + " where " + CEDULA + " = '" + cedula + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        DbJuego dbJuego = new DbJuego(context);
        Cursor cursor = null;
        Datos datos = new Datos();
        if (db != null) {
            cursor = db.rawQuery(query, null);
            while (cursor.moveToNext()) {
                datos.setNombre(cursor.getString(1));
                datos.setCedula(cursor.getString(2));
                datos.setTelefono(cursor.getString(3));
                datos.setCorreo(cursor.getString(4));
                datos.setNombreJuego(cursor.getString(5));
                datos.setOpcion(cursor.getString(6));
                Juego juego = dbJuego.leerJuegosPorId(cursor.getString(8));
                juego.setFechaPrestamo(cursor.getString(7));
                lista.add(juego);
                datos.setArrayJuegos(lista);
            }
        }
        return datos;
    }
}
