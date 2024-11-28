package com.delivery_clientes.ui.pedidos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.delivery_clientes.R;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class PedidosFragment extends Fragment {

    private RecyclerView recyclerViewPedidos;
    private PedidosAdapter pedidosAdapter;
    private PedidosViewModel pedidosViewModel;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pedidos, container, false);

        recyclerViewPedidos = view.findViewById(R.id.recyclerViewPedidos);
        recyclerViewPedidos.setLayoutManager(new LinearLayoutManager(getContext()));

        pedidosAdapter = new PedidosAdapter(new ArrayList<>());
        recyclerViewPedidos.setAdapter(pedidosAdapter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pedidosViewModel = new ViewModelProvider(this).get(PedidosViewModel.class);

        pedidosViewModel.getPedidosLiveData().observe(getViewLifecycleOwner(), pedidosList -> {
            pedidosAdapter.updateData(pedidosList);
        });

    }
}