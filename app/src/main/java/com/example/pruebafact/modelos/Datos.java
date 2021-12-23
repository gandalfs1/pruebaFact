package com.example.pruebafact.modelos;

import java.util.ArrayList;

public class Datos {
    private String nombre;
    private String cedula;
    private String telefono;
    private String correo;
    private String opcion;
    private String fecha;
    private String nombreJuego;
    private String idJuego;
    private Juego listaJuego;
    private ArrayList<Juego> arrayJuegos;



    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getFecha() {
        return fecha;
    }

    public String getOpcion() {
        return opcion;
    }

    public void setOpcion(String opcion) {
        this.opcion = opcion;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getNombreJuego() {
        return nombreJuego;
    }

    public void setNombreJuego(String nombreJuego) {
        this.nombreJuego = nombreJuego;
    }

    public String getIdJuego() {
        return idJuego;
    }

    public void setIdJuego(String idJuego) {
        this.idJuego = idJuego;
    }

    public Juego getListaJuego() {
        return listaJuego;
    }

    public void setListaJuego(Juego listaJuego) {
        this.listaJuego = listaJuego;
    }

    public ArrayList<Juego> getArrayJuegos() {
        return arrayJuegos;
    }

    public void setArrayJuegos(ArrayList<Juego> arrayJuegos) {
        this.arrayJuegos = arrayJuegos;
    }
}
