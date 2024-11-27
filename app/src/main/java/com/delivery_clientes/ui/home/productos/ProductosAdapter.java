package com.delivery_clientes.ui.home.productos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.delivery_clientes.R;
import com.delivery_clientes.data.db.entities.Productos;
import com.delivery_clientes.ui.carrito.CarritoItem;
import com.delivery_clientes.ui.carrito.CarritoViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductosAdapter extends RecyclerView.Adapter<ProductosAdapter.ProductosViewHolder> {

    private List<Productos> productosList;
    private CarritoViewModel carritoViewModel;

    public ProductosAdapter(List<Productos> productosList, CarritoViewModel carritoViewModel){
        this.productosList = productosList;
        this.carritoViewModel = carritoViewModel;
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

        CarritoItem carritoItem = new CarritoItem(producto.getId());

        //Configuracion del boton añadir producto
        holder.imageButton.setOnClickListener(view -> {
            carritoViewModel.addCarritoItem(producto.getId());
        });

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
            imageView = itemView.findViewById(R.id.imageViewItemCarrito);
            nombreProducto = itemView.findViewById(R.id.textViewItemCarrito);
            descProducto = itemView.findViewById(R.id.textViewDescItemCarrito);
            precio = itemView.findViewById(R.id.textViewPrecioItemCarrito);
            imageButton = itemView.findViewById(R.id.carritoQuitarProducto);
        }
    }

}
