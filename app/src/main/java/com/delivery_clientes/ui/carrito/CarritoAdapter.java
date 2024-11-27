package com.delivery_clientes.ui.carrito;

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
import com.delivery_clientes.data.repository.ProductosRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CarritoAdapter extends RecyclerView.Adapter<CarritoAdapter.CarritoViewHolder> {

    private List<CarritoItem> carritoList;
    private Map<Integer, Productos> productosMap;


    public CarritoAdapter(List<CarritoItem> carritoList){
        this.carritoList = carritoList;
        this.productosMap = new HashMap<>();
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
        }
        //falta imagen y botones
    }

    @Override
    public int getItemCount(){ return carritoList.size(); }

    public void updateData(List<CarritoItem> nuevosCarritosItems){
        this.carritoList = nuevosCarritosItems;
        notifyDataSetChanged();
    }

    public void updateProductos(Map<Integer, Productos> productosMap) {
        this.productosMap = productosMap;
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

//    private LiveData<Productos> obtenerProductoDesdeCarrito(CarritoItem carritoItem){
//        return productosRepository.obtenerProductosLivePorId(carritoItem.getId_producto());
//    }

}
