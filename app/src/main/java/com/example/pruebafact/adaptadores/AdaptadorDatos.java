package com.example.pruebafact.adaptadores;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pruebafact.R;
import com.example.pruebafact.modelos.Datos;

import java.util.ArrayList;

public class AdaptadorDatos extends RecyclerView.Adapter<AdaptadorDatos.ViewMyHolder> {

    private Context context;
    ArrayList<Datos> listaJuegos;
    private Activity activity;

    public AdaptadorDatos(Context context, ArrayList<Datos> listaJuegos, Activity activity) {
        this.context = context;
        this.listaJuegos = listaJuegos;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewMyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_lista_datos, parent, false);
        return new AdaptadorDatos.ViewMyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewMyHolder holder, int position) {
        Datos datos = listaJuegos.get(position);
        holder.tvJuegoDato.setText(datos.getNombreJuego());
        holder.tvFechaDato.setText(datos.getFecha());
        holder.tvOpcionDato.setText(datos.getOpcion());
        //holder.tvJuegoDato.setText(datos.getNombreJuego());
    }

    @Override
    public int getItemCount() {
        return listaJuegos.size();
    }

    public class ViewMyHolder extends RecyclerView.ViewHolder {
        TextView tvJuegoDato;
        TextView tvFechaDato;
        TextView tvOpcionDato;
        TextView tvOtroDato;
        ImageView imgJuegoLista;

        public ViewMyHolder(@NonNull View itemView) {
            super(itemView);
            tvJuegoDato = itemView.findViewById(R.id.tvJuegoDato);
            tvFechaDato = itemView.findViewById(R.id.tvFechaDato);
            tvOpcionDato = itemView.findViewById(R.id.tvOpcionDato);
            tvOtroDato = itemView.findViewById(R.id.tvOtroDato);
        }
    }
}
