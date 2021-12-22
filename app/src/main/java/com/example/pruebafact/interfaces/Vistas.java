package com.example.pruebafact.interfaces;

import com.example.pruebafact.callback.JsonListener;
import com.example.pruebafact.modelos.Juego;

public interface Vistas {
    void agregarJuego(Juego juego);
    void verJuegos();
    void consultarPorCedula(String cedula);
}
