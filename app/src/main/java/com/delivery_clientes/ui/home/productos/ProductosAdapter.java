package com.delivery_clientes.ui.home.productos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.delivery_clientes.R;
import com.delivery_clientes.data.db.entities.Productos;

import java.util.List;

public class ProductosAdapter extends RecyclerView.Adapter<ProductosAdapter.ProductosViewHolder> {

    private List<Productos> productosList;

    public ProductosAdapter(List<Productos> productosList){
        this.productosList = productosList;
    }

    @NonNull
    @Override
    public ProductosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.producto_item,parent,false);
        return new ProductosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductosViewHolder holder, int position){
        Productos producto = productosList.get(position);
        holder.nombreProducto.setText(producto.getNombre());
        holder.descProducto.setText(producto.getDescripcion());
        holder.precio.setText(String.format("$%.2f",producto.getPrecio()));
        //Añadir forma de recuperar imagen
    }

    @Override
    public int getItemCount(){
        return productosList.size();
    }

    public void updateData(List<Productos> nuevosProductos){
        productosList = nuevosProductos;
        notifyDataSetChanged();
    }

    public static class ProductosViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView nombreProducto;
        TextView descProducto;
        TextView precio;
        ImageButton imageButton;

        public ProductosViewHolder(@NonNull View itemView){
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewItemProductos);
            nombreProducto = itemView.findViewById(R.id.textViewItemProductos);
            descProducto = itemView.findViewById(R.id.textViewDescItemProductos);
            precio = itemView.findViewById(R.id.textViewPrecioItemProductos);
            imageButton = itemView.findViewById(R.id.imageButtonAddCartItemProductos);
        }
    }

}
