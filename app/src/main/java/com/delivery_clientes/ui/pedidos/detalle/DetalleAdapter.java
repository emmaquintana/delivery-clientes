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

import java.util.List;

public class DetalleAdapter extends RecyclerView.Adapter<DetalleAdapter.DetalleViewHolder> {

    private List<PedidoDetalle> detalleList;
    private List<Productos> productosList;

    public DetalleAdapter(List<PedidoDetalle> detalleList, List<Productos> productosList){
        this.detalleList = detalleList;
        this.productosList = productosList;
    }

    @NonNull
    @Override
    public DetalleViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detalle_item,parent,false);

        return new DetalleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetalleViewHolder holder, int position){
        PedidoDetalle detalle = detalleList.get(position);

        holder.producto.setText(detalle.getProducto_id());

    }

    @Override
    public int getItemCount() {return detalleList.size();}

    public void updateData(List<PedidoDetalle> nuevosDetalle){
        detalleList = nuevosDetalle;
        notifyDataSetChanged();
    }

    public void encontrarProducto(List<Productos> productos){
        productosList = productos;
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
}
