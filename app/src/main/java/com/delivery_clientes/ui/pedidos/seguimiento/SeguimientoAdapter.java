package com.delivery_clientes.ui.pedidos.seguimiento;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.delivery_clientes.R;
import com.delivery_clientes.data.db.entities.Seguimiento;
import com.delivery_clientes.ui.pedidos.PedidosAdapter;
import com.delivery_clientes.utils.NotificationHelper;

import java.util.List;

public class SeguimientoAdapter extends RecyclerView.Adapter<SeguimientoAdapter.SeguimientoViewHolder> {

    private List<Seguimiento> seguimientoList;

    public SeguimientoAdapter(List<Seguimiento> seguimientoList) {this.seguimientoList = seguimientoList;}

    @NonNull
    @Override
    public SeguimientoViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.seguimiento_item,parent,false);

        return new SeguimientoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SeguimientoViewHolder holder, int position){
        Seguimiento seguimiento = seguimientoList.get(position);
        holder.estado.setText(seguimiento.getEstado());
        holder.fecha.setText(seguimiento.getFecha_actualizacion());
    }

    @Override
    public int getItemCount() {return seguimientoList.size();}

    public void updateData(Context context, List<Seguimiento> nuevosSeguimiento){
        seguimientoList = nuevosSeguimiento;
        notifyDataSetChanged();
        String estado = seguimientoList.get(seguimientoList.size()-1).getEstado();
        if(!estado.equals("Pendiente")){
            NotificationHelper.sendLocalNotification(context, "Estado de su pedido: " + estado);
        }
    }

    public static class SeguimientoViewHolder extends RecyclerView.ViewHolder{
        TextView estado;
        TextView fecha;

        public SeguimientoViewHolder(@NonNull View itemView){
            super(itemView);
            estado = itemView.findViewById(R.id.textViewSegEstado);
            fecha = itemView.findViewById(R.id.textViewSegFecha);
        }
    }
}
