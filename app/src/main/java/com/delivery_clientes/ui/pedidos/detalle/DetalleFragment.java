package com.delivery_clientes.ui.pedidos.detalle;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.delivery_clientes.R;

import java.util.ArrayList;

public class DetalleFragment extends Fragment {

    private RecyclerView recyclerViewDetalle;
    private DetalleAdapter detalleAdapter;
    private DetalleViewModel detalleViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pedido_detalle, container, false);

        recyclerViewDetalle = view.findViewById(R.id.recyclerViewPedidoDetalle);
        recyclerViewDetalle.setLayoutManager(new LinearLayoutManager(getContext()));

        detalleAdapter = new DetalleAdapter(new ArrayList<>());
        recyclerViewDetalle.setAdapter(detalleAdapter);

        return view;
    }
}
