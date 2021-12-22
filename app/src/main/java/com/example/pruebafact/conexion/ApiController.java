package com.example.pruebafact.conexion;

import android.content.Context;
import android.util.Log;

import com.example.pruebafact.callback.CallbackRsp;
import com.example.pruebafact.callback.JsonListener;
import com.example.pruebafact.interfaces.Vistas;
import com.example.pruebafact.modelos.Datos;
import com.example.pruebafact.modelos.Juego;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ApiController implements Runnable {


    private Context context;
    private final String uri;
    private final String body;
    private final CallbackRsp rspCallback;
    public static JsonListener listener;
    private Vistas vistas;
    Gson gson = new Gson();

    public ApiController(Vistas vista, Context context, String uri, String body, CallbackRsp rspCallback) {
        this.context = context;
        this.uri = uri;
        this.body = body;
        this.rspCallback = rspCallback;
        this.vistas = vista;
    }


    @Override
    public void run() {
        Log.e("dfgdg", "run: ENtrada run");
        new Thread(() -> {
            JSONObject json;
            try {
                if (body != null) {
                    json = reciveJSON(body);
                    if (json != null) {
                        controller(json, uri, (json1, http) -> {
                            if (json1 != null && !http.isEmpty()) {
                                rspCallback.rspCallback(json1, http);
                            }
                        });
                    } else {
                        listener.rspListener("{\"response\":\"ERROR AL RECIBIR DATOS\"}", "400");
                    }
                } else {
                    listener.rspListener("{\"response\":\"MENSAJE INCOMPLETO\"}", "400");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void controller(JSONObject json, String tipoPeticion, JsonListener listener) {
        //this.listener = listener;
        this.setListener(listener);
        Log.e("JsonLLega", "controller: " + json.toString());
        switch (tipoPeticion) {
            case "/registrar":
                procesarJuego(json);
                break;
            case "/leer":
                vistas.verJuegos();
                break;
            case "/consultar":
                procesarConsulta(json);
                break;
        }


    }

    private void procesarConsulta(JSONObject json) {
        Datos datos = gson.fromJson(json.toString(), Datos.class);
        if (validacionConsulta(datos))
            vistas.consultarPorCedula(datos.getCedula());

    }

    private boolean validacionConsulta(Datos datos) {


        if (datos.getCedula().isEmpty()) {
            listener.rspListener("{\"response\":\"error en el json, faltan campos\"}", "400");
            return false;
        }

        return true;
    }

    private void setListener(JsonListener listener) {
        this.listener = listener;
    }

    private void procesarJuego(JSONObject json) {
        Juego juego = gson.fromJson(json.toString(), Juego.class);

        if (validacionJuego(juego))
            vistas.agregarJuego(juego);

    }


    private boolean validacionJuego(Juego juego) {
        if (juego.getTitulo() == null || juego.getNombre() == null ||
                juego.getAnho() == null || juego.getProtagonista() == null ||
                juego.getDirector() == null || juego.getProductor() == null ||
                juego.getTecnologia() == null || juego.getImagen() == null) {
            listener.rspListener("{\"response\":\"error en el json, faltan campos\"}", "400");
            return false;
        }

        return true;
    }

    public JSONObject reciveJSON(String json) {
        try {
            if (!json.isEmpty() && isJSONValid(json)) {
                String data = "DATA JSON --- " + "receiveJSON: " + json;
                Log.e("JSON Obtendio ---- ", data);
                if (json.contains("null")) {
                    return null;
                }
                Map<String, Object> jsonMap = new Gson().fromJson(
                        json, new TypeToken<HashMap<String, Object>>() {
                        }.getType()
                );
                return new JSONObject(jsonMap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isJSONValid(String jsonInString) {
        try {
            new Gson().fromJson(jsonInString, Object.class);
            return true;
        } catch (JsonSyntaxException e) {
            return false;
        }
    }

}
