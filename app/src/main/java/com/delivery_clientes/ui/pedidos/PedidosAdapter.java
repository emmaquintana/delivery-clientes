package com.delivery_clientes.ui.pedidos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.delivery_clientes.R;
import com.delivery_clientes.data.db.entities.Pedidos;

import java.util.List;

public class PedidosAdapter extends RecyclerView.Adapter<PedidosAdapter.PedidosViewHolder> {//implements View.OnClickListener {

    private List<Pedidos> pedidosList;
    private View.OnClickListener listener;

    public PedidosAdapter(List<Pedidos> pedidosList) {this.pedidosList = pedidosList;}

    @NonNull
    @Override
    public PedidosViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pedidos_item,parent,false);
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

    public void updateData(List<Pedidos> nuevosPedidos){
        pedidosList = nuevosPedidos;
        notifyDataSetChanged();
    }

    public class PedidosViewHolder extends RecyclerView.ViewHolder {
        TextView idPedido;
        TextView fechaPedido;
        TextView estadoPedido;
        ConstraintLayout item;

        public PedidosViewHolder(@NonNull View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.pedidosItem);
            idPedido = itemView.findViewById(R.id.textViewPedido);
            fechaPedido = itemView.findViewById(R.id.textViewFechaPed);
            estadoPedido = itemView.findViewById(R.id.textViewEstadoPed);

            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    long id = pedidosList.get(position).getId();
                    int negocio = pedidosList.get(position).getNegocio_id();
                    int repartidor = pedidosList.get(position).getRepartidor_id();
                    String fecha = pedidosList.get(position).getFecha_pedido();
                    String estado = pedidosList.get(position).getEstado();
                    NavController navController = Navigation.findNavController(view);
                    Bundle bundle = new Bundle();
                    bundle.putLong("pedidoId", id);
                    bundle.putInt("negocio", negocio);
                    bundle.putInt("repartidor", repartidor);
                    bundle.putString("fecha", fecha);
                    bundle.putString("estado", estado);
                    navController.navigate(R.id.action_pedidosFragment_to_detalleFragment, bundle);
                }
            });
        }
    }

}
