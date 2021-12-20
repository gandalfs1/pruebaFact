package com.example.pruebafact;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.pruebafact.conexion.Conexion;
import com.example.pruebafact.conexion.ConexionPostman;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Conexion conexion = new Conexion();

        conexion.getInstanceServer(MainActivity.this,2022);

    }
}