package com.delivery_clientes.ui.carrito;

import android.util.Log;
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
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CarritoAdapter extends RecyclerView.Adapter<CarritoAdapter.CarritoViewHolder> {

    private List<CarritoItem> carritoList;
    private Map<Integer, Productos> productosMap;
    private CarritoViewModel carritoViewModel;

    private double total;

    public CarritoAdapter(List<CarritoItem> carritoList, CarritoViewModel carritoViewModel){
        this.carritoList = carritoList;
        this.productosMap = new HashMap<>();
        this.total = 0;
        this.carritoViewModel = carritoViewModel;
        calcularTotal();
    }

    @NonNull
    @Override
    public CarritoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.carrito_item,parent,false);
        return new CarritoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarritoViewHolder holder, int position){
        CarritoItem carritoItem = carritoList.get(position);

        Productos producto = productosMap.get(carritoItem.getId_producto());

        if (producto != null) {
            holder.nombreProducto.setText(producto.getNombre());
            holder.descProducto.setText(producto.getDescripcion());
            holder.precio.setText(String.format("$%.2f", producto.getPrecio()));
            holder.cantidades.setText(String.format("x%d", carritoItem.getCantidad()));
            Picasso.get().load(producto.getImagen()).into(holder.imageView);

            holder.menos.setOnClickListener(view -> {
                carritoViewModel.restarCarritoItem(carritoItem.getId_producto());
            });

            holder.borrar.setOnClickListener(view -> {
                carritoViewModel.borrarCarritoItem(carritoItem.getId_producto());
            });
        } else {
            Log.d("CarritoAdapter", "Producto no encontrado en cache para el id: " + carritoItem.getId_producto());
        }
        //falta imagen y botones
    }

    @Override
    public int getItemCount(){ return carritoList.size(); }

    public void updateData(List<CarritoItem> nuevosCarritosItems){
        this.carritoList = nuevosCarritosItems;
        calcularTotal();
        notifyDataSetChanged();
    }

    public void updateProductos(Map<Integer, Productos> productosMap) {
        this.productosMap = productosMap;
        calcularTotal();
        notifyDataSetChanged();  // Actualiza el adaptador cuando los productos estén disponibles
    }

    public static class CarritoViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView nombreProducto;
        TextView descProducto;
        TextView precio;
        ImageButton menos;
        ImageButton borrar;
        TextView cantidades;

        public CarritoViewHolder(@NonNull View itemView){
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewItemCarrito);
            nombreProducto = itemView.findViewById(R.id.textViewItemCarrito);
            descProducto = itemView.findViewById(R.id.textViewDescItemCarrito);
            precio = itemView.findViewById(R.id.textViewPrecioItemCarrito);
            cantidades = itemView.findViewById(R.id.carritoCantidades);

            menos = itemView.findViewById(R.id.carritoRestarProducto);
            borrar = itemView.findViewById(R.id.carritoQuitarProducto);
        }
    }

    private void calcularTotal() {
        total = 0;
        for (CarritoItem item : carritoList) {
            Productos producto = productosMap.get(item.getId_producto());
            if (producto != null) {
                total += producto.getPrecio() * item.getCantidad(); // Suma el precio por cantidad
            }
        }
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }


}
