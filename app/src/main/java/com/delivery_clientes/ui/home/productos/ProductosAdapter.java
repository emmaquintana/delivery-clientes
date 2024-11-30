package com.delivery_clientes.ui.home.productos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.delivery_clientes.R;
import com.delivery_clientes.data.db.entities.Productos;
import com.delivery_clientes.ui.carrito.CarritoItem;
import com.delivery_clientes.ui.carrito.CarritoViewModel;

import java.util.List;


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

        //Observacion dinamica para mostrar en home la cantidad de cada producto en el carrito
        LiveData<List<CarritoItem>> carritoItemsLive = carritoViewModel.getCarritoItems();
        carritoItemsLive.observeForever(carritoItems -> {
            if (carritoItems != null) {
                for (CarritoItem item : carritoItems) {
                    if (item.getId_producto() == producto.getId()) {
                        holder.cant.setText(String.valueOf(item.getCantidad())); // Actualiza la cantidad en la vista
                        break;
                    }
                }
            }
        });

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
        TextView cant;

        public ProductosViewHolder(@NonNull View itemView){
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewItemCarrito);
            nombreProducto = itemView.findViewById(R.id.textViewItemCarrito);
            descProducto = itemView.findViewById(R.id.textViewDescItemCarrito);
            precio = itemView.findViewById(R.id.textViewPrecioItemCarrito);
            imageButton = itemView.findViewById(R.id.carritoAgregarProducto);
            cant = itemView.findViewById(R.id.productoCantidadHomeTextView);
        }
    }

}
