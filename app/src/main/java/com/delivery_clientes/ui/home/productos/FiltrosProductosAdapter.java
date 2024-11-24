package com.delivery_clientes.ui.home.productos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.delivery_clientes.R;
import com.delivery_clientes.data.db.entities.Categorias;
import com.delivery_clientes.ui.home.HomeViewModel;

import java.util.List;

public class FiltrosProductosAdapter extends RecyclerView.Adapter<FiltrosProductosAdapter.FiltrosProductosViewHolder> {

    private List<Categorias> categoriasList;
    private OnCategoriaClickListener listener;

    public interface OnCategoriaClickListener{
        void onCategoriaClick(Categorias categoria);
    }

    public FiltrosProductosAdapter(List<Categorias> categoriasList, OnCategoriaClickListener listener){
        this.categoriasList = categoriasList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public FiltrosProductosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.categoria_item,parent,false);
        return new FiltrosProductosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FiltrosProductosViewHolder holder, int position){
        Categorias categoria = categoriasList.get(position);
        holder.categoria.setText(categoria.getNombre());
        holder.categoria.setOnClickListener(view -> {
            if (listener != null) {
                    listener.onCategoriaClick(categoria);
            }
        });
    }

    @Override
    public int getItemCount(){ return categoriasList.size(); }

    public void updateData(List<Categorias> nuevasCategorias){
        categoriasList = nuevasCategorias;
        notifyDataSetChanged();
    }

    public static class FiltrosProductosViewHolder extends RecyclerView.ViewHolder {
        TextView categoria;

        public FiltrosProductosViewHolder (@NonNull View itemView) {
            super(itemView);
            categoria = itemView.findViewById(R.id.categoria_item_textView);
        }
    }
}
