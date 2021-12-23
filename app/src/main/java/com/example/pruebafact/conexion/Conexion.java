package com.example.pruebafact.conexion;

import android.content.Context;
import android.widget.Toast;

import com.example.pruebafact.interfaces.Vistas;

public class Conexion {
    private static int puertoEstablecido;
    private static ConexionPostman webPostman;
    Vistas vistas;

    private static Conexion conexion;

    public static Conexion getConexion(final Context context, int puerto, Vistas vista) {
        if (conexion == null){
            conexion = new Conexion();
            conexion.getInstanceServer(context, puerto, vista);
        }
       return conexion;
    }

    public void getInstanceServer(final Context context, int puerto, Vistas vista) {
        this.vistas = vista;
        new Thread(() -> {
            if (puerto <= 0) {
                pararServicio();
                return;
            }
            if (iniciarServicio(puerto, context)) {
                //Toast.makeText(context, "iniciando conexion", Toast.LENGTH_SHORT).show();
            }
        }).start();
    }

    private boolean pararServicio() {
        if (webPostman != null) {
            webPostman.stop();
            return true;
        }
        return false;
    }

    private boolean iniciarServicio(int port, Context context1) {
        System.out.println("Inicio server");
        try {
            if (port != puertoEstablecido) {
                puertoEstablecido = port;
                if (webPostman != null)
                    webPostman.stop();
                webPostman = null;
            }
            if (webPostman == null) {
                webPostman = new ConexionPostman(port, context1, vistas);
            }
            if (!webPostman.isAlive())
                webPostman.start();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
