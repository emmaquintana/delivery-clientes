package com.delivery_clientes.ui.pedidos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.delivery_clientes.R;
import com.delivery_clientes.data.db.entities.Pedidos;

import java.util.List;

public class PedidosAdapter extends RecyclerView.Adapter<PedidosAdapter.PedidosViewHolder> implements View.OnClickListener {

    private List<Pedidos> pedidosList;
    private View.OnClickListener listener;

    public PedidosAdapter(List<Pedidos> pedidosList) {this.pedidosList = pedidosList;}

    @NonNull
    @Override
    public PedidosViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pedidos_item,parent,false);

        view.setOnClickListener(this);

        return new PedidosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PedidosViewHolder holder, int position){
        Pedidos pedido = pedidosList.get(position);
        holder.idPedido.setText("'" + pedido.getId() + "'");
        holder.estadoPedido.setText(pedido.getEstado());
        holder.fechaPedido.setText(pedido.getFecha_pedido());

    }

    @Override
    public int getItemCount() {return pedidosList.size();}

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View view){
        if (listener != null) {listener.onClick(view);}
    }

    public void updateData(List<Pedidos> nuevosPedidos){
        pedidosList = nuevosPedidos;
        notifyDataSetChanged();
    }

    public static class PedidosViewHolder extends RecyclerView.ViewHolder{
        TextView idPedido;
        TextView fechaPedido;
        TextView estadoPedido;

        public PedidosViewHolder(@NonNull View itemView){
            super(itemView);
            idPedido = itemView.findViewById(R.id.textViewPedido);
            fechaPedido = itemView.findViewById(R.id.textViewFechaPed);
            estadoPedido = itemView.findViewById(R.id.textViewEstadoPed);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }
}
