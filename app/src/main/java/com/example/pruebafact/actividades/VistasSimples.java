package com.example.pruebafact.actividades;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pruebafact.R;
import com.example.pruebafact.adaptadores.AdaptadorJuegos;
import com.example.pruebafact.basedatos.DbDatos;
import com.example.pruebafact.basedatos.DbJuego;
import com.example.pruebafact.conexion.ApiController;
import com.example.pruebafact.conexion.Conexion;
import com.example.pruebafact.interfaces.Vistas;
import com.example.pruebafact.modelos.Datos;
import com.example.pruebafact.modelos.Juego;
import com.example.pruebafact.modelos.Respuestas;

public class VistasSimples extends AppCompatActivity implements Vistas {
    DbJuego dbJuego;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initConexion();
    }

    void initConexion() {
        Conexion conexion = Conexion.getConexion(this, 2022, this);
    }

    @Override
    public void agregarJuego(Juego juego) {
        runOnUiThread(() -> {
            setContentView(R.layout.activity_vistas_simples);
            dbJuego = new DbJuego(this);
            dbJuego.agregarJuego(juego);
            TextView tvGuardando = findViewById(R.id.tvGuardando);
            tvGuardando.setText("Agregando juego");
            respuestas("agregado exitosamente", 200);
            endView();
        });
    }

    private void endView() {
        new Handler(getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {

                setContentView(R.layout.activity_main);
            }
        }, 5000);
    }

    @Override
    public void verJuegos() {
        runOnUiThread(() -> {

            setContentView(R.layout.vista_ver_juego);
            RecyclerView rvJuegos = findViewById(R.id.rvJuegos);
            LinearLayout lyDatosCliente = findViewById(R.id.lyDatosCliente);
            lyDatosCliente.setVisibility(View.GONE);
            dbJuego = new DbJuego(this);
            AdaptadorJuegos adaptadorJuegos = new AdaptadorJuegos(this, dbJuego.leerJuegos(), this);
            rvJuegos.setLayoutManager(new GridLayoutManager(this, 2));
            rvJuegos.setAdapter(adaptadorJuegos);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        setContentView(R.layout.activity_main);
    }

    @Override
    public void consultarPorCedula(String cedula) {
        runOnUiThread(() -> {
            DbDatos dbDatos = new DbDatos(this);
            Datos datos = dbDatos.leerDatosPorCedula(cedula);
            if (datos.getCedula() != null)
                ApiController.listener.rspListener(datos, "200");
            else
               respuestas("no se encontraron datos",400);


        });
    }

    @Override
    public void actualizar(Juego juego) {
        runOnUiThread(() -> {

            setContentView(R.layout.actualizando);
            dbJuego = new DbJuego(this);
            dbJuego.actualizarPrecio(juego);
            respuestas("actualizado con exito", 200);
            endView();
        });
    }

    private void respuestas(String msg, int code) {
        Respuestas respuestas = new Respuestas();
        respuestas.setMessage(msg);
        respuestas.setStatusCode(code);
        ApiController.listener.rspListener(respuestas, "400");
    }

}