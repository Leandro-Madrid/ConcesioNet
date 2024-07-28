package com.madridramos.concesionet.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.LinearLayout;

import com.madridramos.concesionet.EditarAutoActivity;
import com.madridramos.concesionet.R;
import com.madridramos.concesionet.models.Auto;
import com.madridramos.concesionet.VerAutosActivity;
import com.madridramos.concesionet.AutoIndividualActivity;

import java.util.List;

public class AutoAdapter extends BaseAdapter {

    private Context context;
    private List<Auto> listaAutos;
    private boolean mostrarDetalles;

    public AutoAdapter(Context context, List<Auto> listaAutos, boolean mostrarDetalles) {
        this.context = context;
        this.listaAutos = listaAutos;
        this.mostrarDetalles = mostrarDetalles;
    }

    @Override
    public int getCount() {
        return listaAutos.size();
    }

    @Override
    public Object getItem(int position) {
        return listaAutos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_auto, parent, false);
            holder = new ViewHolder();
            holder.marcaTextView = convertView.findViewById(R.id.marcaTextView);
            holder.modeloTextView = convertView.findViewById(R.id.modeloTextView);
            holder.anioTextView = convertView.findViewById(R.id.anioTextView);
            holder.precioTextView = convertView.findViewById(R.id.precioTextView);
            holder.verDetalleButton = convertView.findViewById(R.id.verDetalleButton);
            holder.editarLayout = convertView.findViewById(R.id.editarBorrarLayout);
            holder.editarButton = convertView.findViewById(R.id.editarButton);
            holder.borrarButton = convertView.findViewById(R.id.borrarButton);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // Obtener el auto actual
        final Auto auto = listaAutos.get(position);

        // Mostrar los datos en las vistas
        holder.marcaTextView.setText(auto.getMarca());
        holder.modeloTextView.setText(auto.getModelo());
        holder.anioTextView.setText(String.valueOf(auto.getAnio()));
        holder.precioTextView.setText(String.valueOf(auto.getPrecio()));

        // Configurar visibilidad de botones según la actividad
        if (mostrarDetalles) {
            holder.verDetalleButton.setVisibility(View.VISIBLE);
            holder.editarLayout.setVisibility(View.GONE);
        } else {
            holder.verDetalleButton.setVisibility(View.GONE);
            holder.editarLayout.setVisibility(View.VISIBLE);
        }

        // Configurar clic en el botón de detalles
        holder.verDetalleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abrir AutoIndividualActivity para ver detalles del auto
                Intent intent = new Intent(context, AutoIndividualActivity.class);
                intent.putExtra("id_auto", auto.getIdAuto()); // Suponiendo que getId() devuelve el ID del auto
                context.startActivity(intent);
            }
        });

        // Configurar clic en el botón de editar
        holder.editarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, EditarAutoActivity.class);
                intent.putExtra("id_auto", auto.getIdAuto());
                context.startActivity(intent);
            }
        });

        // Configurar clic en el botón de borrar
        holder.borrarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listaAutos.remove(position);
                notifyDataSetChanged();
            }
        });

        return convertView;
    }

    static class ViewHolder {
        TextView marcaTextView;
        TextView modeloTextView;
        TextView anioTextView;
        TextView precioTextView;
        Button verDetalleButton;
        LinearLayout editarLayout;
        Button editarButton;
        Button borrarButton;
    }
}

