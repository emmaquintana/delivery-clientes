package com.delivery_clientes.ui.home;

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
import com.delivery_clientes.ui.home.productos.ProductosAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerViewProductos;
    private ProductosAdapter productosAdapter;
    private HomeViewModel homeViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerViewProductos = view.findViewById(R.id.recyclerViewProductos);
        recyclerViewProductos.setLayoutManager(new LinearLayoutManager(getContext()));

        productosAdapter = new ProductosAdapter(new ArrayList<>());
        recyclerViewProductos.setAdapter(productosAdapter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Configuracion de VM
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        homeViewModel.getProductosLiveData().observe(getViewLifecycleOwner(), productosList -> {
            productosAdapter.updateData(productosList);
        });
    }
}