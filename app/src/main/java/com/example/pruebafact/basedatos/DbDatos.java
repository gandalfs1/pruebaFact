package com.example.pruebafact.basedatos;

import static com.example.pruebafact.tools.Constantes.CEDULA;
import static com.example.pruebafact.tools.Constantes.CORREO;
import static com.example.pruebafact.tools.Constantes.FECHA;
import static com.example.pruebafact.tools.Constantes.ID;
import static com.example.pruebafact.tools.Constantes.NOMBRE;
import static com.example.pruebafact.tools.Constantes.NOMBREJUEGO;
import static com.example.pruebafact.tools.Constantes.OPCION;
import static com.example.pruebafact.tools.Constantes.TABLA_DATO;
import static com.example.pruebafact.tools.Constantes.TELEFONO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.pruebafact.modelos.Datos;
import com.example.pruebafact.modelos.Juego;

import java.util.ArrayList;
import java.util.Calendar;

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
                "SELECT DISTINCT td.*,tj.* " +
                        "FROM tabla_juego tj JOIN tabla_dato td\n" +
                        "ON td.cedula = '" + cedula + "' AND tj.id=td.id ";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        Datos datos = new Datos();
        if (db != null) {
            cursor = db.rawQuery(query, null);
            while (cursor.moveToNext()) {
                datos.setNombre(cursor.getString(1));
                datos.setCedula(cursor.getString(2));
                datos.setTelefono(cursor.getString(3));
                datos.setCorreo(cursor.getString(4));
                datos.setOpcion(cursor.getString(6));
                datos.setDiasParaEntregar(calcularDias(cursor.getString(7)));
                Juego juego = new Juego();
                juego.setId(cursor.getString(8));
                juego.setTitulo(cursor.getString(10));
                juego.setNombre(cursor.getString(11));
                juego.setAnho(cursor.getString(12));
                juego.setPrecio(cursor.getString(13));
                juego.setDirector(cursor.getString(14));
                juego.setProductor(cursor.getString(15));
                juego.setTecnologia(cursor.getString(16));
                juego.setImagen(cursor.getString(17));
                lista.add(juego);
            }
            if (!lista.isEmpty())
                datos.setJuegos(lista);
            cursor.close();
        }
        return datos;
    }


    private String calcularDias(String fecha) {

        String[] x = fecha.split("/");
        int mes = Integer.parseInt(x[0]);
        int dia = Integer.parseInt(x[1]);
        int anho = Integer.parseInt(x[2]);


        Calendar inicio = Calendar.getInstance();
        inicio.set(anho, mes, dia);
        inicio.set(Calendar.HOUR, 0);
        inicio.set(Calendar.HOUR_OF_DAY, 0);
        inicio.set(Calendar.MINUTE, 0);
        inicio.set(Calendar.MILLISECOND, 0);

        Calendar actual = Calendar.getInstance();
        actual.set(2022, 01, 06);
        actual.set(Calendar.HOUR, 0);
        actual.set(Calendar.HOUR_OF_DAY, 0);
        actual.set(Calendar.MINUTE, 0);
        actual.set(Calendar.MILLISECOND, 0);

        long finMs = actual.getTimeInMillis();
        long inicioMs = inicio.getTimeInMillis();

        int dias = (int) (Math.abs(finMs - inicioMs) / (1000 * 60 * 60 * 24));

        return String.valueOf(20 - dias);
    }

}
