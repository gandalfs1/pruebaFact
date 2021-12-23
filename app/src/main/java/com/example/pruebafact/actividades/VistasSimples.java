package com.example.pruebafact.actividades;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import com.example.pruebafact.modelos.Juego;
import com.example.pruebafact.modelos.Respuestas;

public class VistasSimples extends AppCompatActivity implements Vistas {
    DbJuego dbJuego;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
       initConexion();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
       initConexion();
    }
    void initConexion(){
        Conexion conexion = Conexion.getConexion(this, 2022, this);
    }
    @Override
    public void agregarJuego(Juego juego) {
        runOnUiThread(() -> {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
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
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
                setContentView(R.layout.activity_main);
            }
        }, 5000);
    }

    @Override
    public void verJuegos() {
        runOnUiThread(() -> {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
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
    public void consultarPorCedula(String cedula) {
        runOnUiThread(() -> {
            DbDatos dbDatos = new DbDatos(this);
            ApiController.listener.rspListener(dbDatos.leerDatosPorCedula(cedula), "400");
        });
    }

    @Override
    public void actualizar(Juego juego) {
        runOnUiThread(() -> {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setContentView(R.layout.actualizando);
            dbJuego = new DbJuego(this);
            dbJuego.actualizarPrecio(juego);
            respuestas("actualizado con exito", 400);
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