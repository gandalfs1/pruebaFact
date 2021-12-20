package com.example.pruebafact.conexion;

import android.content.Context;

import java.util.Map;
import java.util.concurrent.CountDownLatch;

import fi.iki.elonen.NanoHTTPD;

public class ConexionPostman extends NanoHTTPD {
private  final Context context;

    public ConexionPostman(int port, Context context) {
        super(port);
        this.context = context;
    }

    public ConexionPostman(String hostname, int port, Context context) {
        super(hostname, port);
        this.context = context;
    }

    @Override
    public Response serve(IHTTPSession session) {
        return super.serve(session);
    }
}
