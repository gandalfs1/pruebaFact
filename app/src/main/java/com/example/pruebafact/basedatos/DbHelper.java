package com.example.pruebafact.basedatos;

import static com.example.pruebafact.tools.Constantes.*;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {


    public DbHelper(@Nullable Context context) {
        super(context, NAMEDB, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLA_JUEGO + " ( " +
                ID + " Text , " +
                TITULO + " TEXT , " +
                NOMBRE + " TEXT , " +
                ANHO + " TEXT , " +
                PRECIO + " TEXT , " +
                DIRECTOR + " TEXT , " +
                PRODUCTOR + " TEXT , " +
                TECNOLOGIA + " TEXT ," +
                IMAGEN + " TEXT);");

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLA_DATO + " ( " +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
                NOMBRE + " TEXT , " +
                CEDULA + " TEXT , " +
                TELEFONO + " TEXT , " +
                CORREO + " TEXT , " +
                NOMBREJUEGO + " TEXT , " +
                OPCION + " TEXT , " +
                FECHA + " TEXT);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLA_JUEGO);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLA_DATO);
    }
}
