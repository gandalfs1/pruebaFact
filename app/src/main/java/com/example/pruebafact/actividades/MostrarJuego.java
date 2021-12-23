package com.example.pruebafact.actividades;


import static com.example.pruebafact.tools.Constantes.*;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.pruebafact.R;
import com.example.pruebafact.basedatos.DbDatos;
import com.example.pruebafact.basedatos.DbJuego;
import com.example.pruebafact.conexion.ApiController;
import com.example.pruebafact.modelos.Datos;
import com.example.pruebafact.modelos.Juego;
import com.example.pruebafact.modelos.Respuestas;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;
import java.util.Date;

public class MostrarJuego extends AppCompatActivity {
    ImageView imgJuego;
    String id;
    DbJuego dbJuego;
    DbDatos dbDatos;
    Juego juego;
    Datos datos;
    TextView tvTitulo,tvNombre,tvAnho,tvProductor,tvTecnologia,tvProtagonista;
    Button btnComprar,btnPrestar, btnRegistrar;
    LinearLayout mostrarDatos, lyOpciones, info;
    TextInputEditText etNombre,etCedula, etTelefono, etCorre;
    boolean compra= false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_mostrar_juego);
        inicializaciones();
        mostrarDatos();
        opciones();
        capturarDatos();
    }

    private void capturarDatos() {
        btnRegistrar.setOnClickListener(v ->{
            if (etNombre.getText().toString().trim().isEmpty() || etCedula.getText().toString().trim().isEmpty() || etTelefono.getText().toString().trim().isEmpty() || etCorre.getText().toString().trim().isEmpty()){
                Toast.makeText(this, "LLenar Datos", Toast.LENGTH_SHORT).show();
            }
            else{
                Respuestas respuestas = new Respuestas();
                respuestas.setMessage("Error al "+ (compra ? COMPRA : PRESTAMO));
                respuestas.setStatusCode(200);
                Date c = Calendar.getInstance().getTime();
                datos.setNombre(etNombre.getText().toString());
                datos.setCedula(etCedula.getText().toString().trim());
                datos.setTelefono(etTelefono.getText().toString().trim());
                datos.setCorreo(etCorre.getText().toString().trim());
                datos.setNombreJuego(juego.getTitulo());
                datos.setIdJuego(juego.getId());
                datos.setOpcion(compra ? COMPRA : PRESTAMO);
                datos.setFecha(String.valueOf(c));
                if (dbDatos.agregarDatos(datos))
                    ApiController.listener.rspListener(datos, "200");
                else
                    ApiController.listener.rspListener(respuestas, "400");

                startActivity(new Intent(this,VistasSimples.class));

            }
        });

    }

    private void opciones() {

        btnComprar.setText(COMPRA);
        btnComprar.setOnClickListener(v ->{
            lyOpciones.setVisibility(View.VISIBLE);
            info.setVisibility(View.GONE);
            btnRegistrar.setText(COMPRA);
            compra = true;
        });
        btnPrestar.setText(PRESTAMO);
        btnPrestar.setOnClickListener(v ->{
            lyOpciones.setVisibility(View.VISIBLE);
            info.setVisibility(View.GONE);
            btnRegistrar.setText(PRESTAMO);
        });
    }

    private void mostrarDatos() {
        Glide.with(this)
                .load(String.valueOf(juego.getImagen()))
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(imgJuego);

        tvTitulo.setText(juego.getTitulo());
        tvNombre.setText(juego.getNombre());
        tvAnho.setText(juego.getAnho());
        tvProductor.setText(juego.getProductor());
        tvTecnologia.setText(juego.getTecnologia());
        tvProtagonista.setText(juego.getPrecio());
    }
    void inicializaciones(){
        mostrarDatos = findViewById(R.id.mostrarDatos);
        lyOpciones = findViewById(R.id.lyOpciones);
        info = findViewById(R.id.info);
        mostrarDatos.setVisibility(View.VISIBLE);
        lyOpciones.setVisibility(View.GONE);
        info.setVisibility(View.VISIBLE);
        imgJuego = findViewById(R.id.imgJuego);
        tvTitulo = findViewById(R.id.tvTitulo);
        tvNombre = findViewById(R.id.tvNombre);
        tvAnho = findViewById(R.id.tvAnho);
        tvProductor = findViewById(R.id.tvProductor);
        tvTecnologia = findViewById(R.id.tvTecnologia);
        tvProtagonista = findViewById(R.id.tvProtagonista);
        btnComprar = findViewById(R.id.btnComprar);
        btnPrestar = findViewById(R.id.btnPrestar);
        btnRegistrar = findViewById(R.id.btnRegistrar);
        dbJuego = new DbJuego(this);
        juego = new Juego();
        id = getIntent().getStringExtra("position");
        juego = dbJuego.leerJuegosPorId(id);
        etNombre = findViewById(R.id.etNombre);
        etCedula = findViewById(R.id.etCedula);
        etTelefono = findViewById(R.id.etTelefono);
        etCorre = findViewById(R.id.etCorre);
        datos = new Datos();
        dbDatos = new DbDatos(this);
    }
}