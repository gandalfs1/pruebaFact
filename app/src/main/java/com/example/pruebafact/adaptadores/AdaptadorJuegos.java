package com.example.pruebafact.adaptadores;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pruebafact.actividades.MostrarJuego;
import com.example.pruebafact.R;
import com.example.pruebafact.modelos.Juego;

import java.util.ArrayList;

public class AdaptadorJuegos extends RecyclerView.Adapter<AdaptadorJuegos.ViewMyHolder> {
    private Context context;
    ArrayList<Juego> listaJuegos;
    private Activity activity;

    public AdaptadorJuegos(Context context, ArrayList<Juego> listaJuegos, Activity activity) {
        this.context = context;
        this.listaJuegos = listaJuegos;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewMyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_lista, parent, false);
        return new ViewMyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewMyHolder holder, int position) {
        Juego juego = listaJuegos.get(position);
        holder.tvTituloLista.setText(String.valueOf(juego.getTitulo()));
        holder.tvNombreLista.setText(String.valueOf(juego.getPrecio()));
        holder.tvAnhoLista.setText(String.valueOf(juego.getAnho()));
        Glide.with(context)
                .load(String.valueOf(juego.getImagen()))
                .into(holder.imgJuegoLista);
        holder.mainLayoutJuego.setOnClickListener(v -> {
            Intent intent = new Intent(context, MostrarJuego.class);
            intent.putExtra("position",String.valueOf(juego.getId()));
            activity.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return listaJuegos.size();
    }


    public class ViewMyHolder extends RecyclerView.ViewHolder {
        TextView tvTituloLista;
        TextView tvNombreLista;
        TextView tvAnhoLista;
        ImageView imgJuegoLista;
        LinearLayout mainLayoutJuego;

        public ViewMyHolder(@NonNull View itemView) {
            super(itemView);
            tvTituloLista = itemView.findViewById(R.id.tvTituloLista);
            tvNombreLista = itemView.findViewById(R.id.tvNombreLista);
            tvAnhoLista = itemView.findViewById(R.id.tvAnhoLista);
            imgJuegoLista = itemView.findViewById(R.id.imgJuegoLista);
            mainLayoutJuego = itemView.findViewById(R.id.mainLayoutJuego);

        }
    }
}
