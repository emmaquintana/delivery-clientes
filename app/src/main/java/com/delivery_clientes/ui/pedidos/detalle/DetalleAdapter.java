package com.delivery_clientes.ui.pedidos.detalle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.delivery_clientes.R;
import com.delivery_clientes.data.db.entities.PedidoDetalle;
import com.delivery_clientes.data.db.entities.Productos;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class DetalleAdapter extends RecyclerView.Adapter<DetalleAdapter.DetalleViewHolder> {

    private List<HashMap<String, Object>> detalleList;
    private double total;

    public DetalleAdapter(List<HashMap<String, Object>> detalleList){
        this.total = 0;
        this.detalleList = detalleList;
    }

    @NonNull
    @Override
    public DetalleViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detalle_item,parent,false);

        return new DetalleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetalleViewHolder holder, int position){
        HashMap<String, Object> pedidoHash = detalleList.get(position);
        holder.cantidad.setText(pedidoHash.get("cant") + "x");
        holder.producto.setText(" " + pedidoHash.get("producto"));
        holder.precio.setText("$" + pedidoHash.get("precio"));
    }

    @Override
    public int getItemCount() {return detalleList.size();}

    public void updateData(List<HashMap<String, Object>> nuevosDetalle){
        detalleList = nuevosDetalle;
        calcularTotal();
        notifyDataSetChanged();
    }

    public static class DetalleViewHolder extends RecyclerView.ViewHolder{
        TextView cantidad;
        TextView producto;
        TextView precio;

        public DetalleViewHolder(@NonNull View itemView){
            super(itemView);
            cantidad = itemView.findViewById(R.id.txtVwDetItemCant);
            producto = itemView.findViewById(R.id.txtVwDetItemNombre);
            precio = itemView.findViewById(R.id.txtVwDetItemPrecio);
        }
    }

    public void calcularTotal(){
        total = 0;
        for (HashMap<String, Object> prod : detalleList){
            int cant = (int) prod.get("cant");
            double precio = (double) prod.get("precio");
            total = total + (precio * cant);
        }
    }

    public double getTotal(){return total;}
}
