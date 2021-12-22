package com.example.pruebafact;

import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pruebafact.adaptadores.AdaptadorJuegos;
import com.example.pruebafact.basedatos.DbJuego;
import com.example.pruebafact.callback.JsonListener;
import com.example.pruebafact.conexion.ApiController;
import com.example.pruebafact.conexion.Conexion;
import com.example.pruebafact.interfaces.Vistas;
import com.example.pruebafact.modelos.Juego;
import com.example.pruebafact.modelos.Respuestas;

public class VistasSimples extends AppCompatActivity implements Vistas {
    DbJuego dbJuego;
    private JsonListener listeners;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        Conexion conexion = new Conexion();
        conexion.getInstanceServer(this, 2022, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void agregarJuego(Juego juego) {
        runOnUiThread(() -> {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setContentView(R.layout.activity_vistas_simples);
            dbJuego = new DbJuego(this);
            if (dbJuego.agregarJuego(juego)) {
                TextView tvGuardando = findViewById(R.id.tvGuardando);
                tvGuardando.setText("Agregando juego");
                respuestas("agregado exitosamente", 200);
            } else {
                respuestas("agregado fallido", 200);
            }
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
            Toast.makeText(this, "ver juegos", Toast.LENGTH_SHORT).show();
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setContentView(R.layout.vista_ver_juego);
            RecyclerView rvJuegos = findViewById(R.id.rvJuegos);
            dbJuego = new DbJuego(this);
            AdaptadorJuegos adaptadorJuegos = new AdaptadorJuegos(this, dbJuego.leerJuegos(), this);
            rvJuegos.setLayoutManager(new GridLayoutManager(this, 2));
            rvJuegos.setAdapter(adaptadorJuegos);
            respuestas("llego a ver",200);
        });
    }

    private void respuestas(String msg, int code) {
        Respuestas respuestas = new Respuestas();
        respuestas.setMessage(msg);
        respuestas.setStatusCode(code);
        ApiController.listener.rspListener(respuestas, "400");
    }

}