package com.example.pruebafact.conexion;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

public class Conexion extends Thread{
    private static int puertoEstablecido;
    private  ConexionPostman webPostman;
    public  void getInstanceServer(final Context context, int puerto) {
        new Thread(() -> {
            final Activity activity = (Activity) context;
            if (puerto <= 0) {
                activity.runOnUiThread(() -> Toast.makeText(context, "no hay puerto", Toast.LENGTH_SHORT).show());
                pararServicio();
                return;
            }
            if (!iniciarServicio(puerto, context)) {
                Toast.makeText(context, "iniciando conexion", Toast.LENGTH_SHORT).show();
            }
        }).start();
    }

    private  boolean pararServicio() {
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
                webPostman = new ConexionPostman(port, context1);
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
