package com.example.pruebafact.conexion;

import android.content.Context;

import com.example.pruebafact.interfaces.Vistas;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import fi.iki.elonen.NanoHTTPD;

public class ConexionPostman extends NanoHTTPD {
    private static final String TAG = "asdasd";
    private final Context context;
    String response = "";
    int status = 0;
    Vistas vistas;

    public ConexionPostman(int port, Context context, Vistas vista) {
        super(port);
        this.context = context;
        this.vistas =  vista;
    }

    public ConexionPostman(String hostname, int port, Context context) {
        super(hostname, port);
        this.context = context;
    }

    @Override
    public Response serve(IHTTPSession session) {
        try {
            Thread.sleep(10);
            final CountDownLatch count = new CountDownLatch(1);
            String uri = session.getUri();
            Map<String, String> jsonBody = new HashMap<>();
            Method method = session.getMethod();
            String body = "-";
            if (Method.POST.equals(method)) {
                session.parseBody(jsonBody);
                body = jsonBody.get("postData");
            }

            new ApiController(vistas,context, uri, body, (json, restHttp) -> {
                Gson gson = new Gson();
                response = gson.toJson(json);
                status = Integer.parseInt(restHttp);
                count.countDown();

            }).run();
            count.await();
        } catch (IOException | ResponseException | InterruptedException ex) {
            if (ex instanceof InterruptedException) {
                Thread.currentThread().interrupt();
            }
            ex.printStackTrace();
        }
        Response.Status status1 = lookup(status);
        return newFixedLengthResponse(status1, "application/json", response);

    }
    public Response.Status lookup(int requestStatus) {
        for (Response.Status estado : Response.Status.values()) {
            if (estado.getRequestStatus() == requestStatus) {
                return estado;
            }
        }
        return null;
    }
}
