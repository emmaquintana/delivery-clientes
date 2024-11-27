package com.delivery_clientes.ui.carrito;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.delivery_clientes.R;
import com.delivery_clientes.data.db.entities.Productos;
import com.delivery_clientes.data.repository.ProductosRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CarritoFragment extends Fragment {

    private RecyclerView carritoRecyclerView;
    private CarritoAdapter carritoAdapter;
    private CarritoViewModel carritoViewModel;
    private ProductosRepository productosRepository;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_carrito, container, false);

        //Configuracion del RV
        carritoRecyclerView = view.findViewById(R.id.carritoRecyclerView);
        carritoRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //Inicializa el VM
        carritoViewModel = new ViewModelProvider(requireActivity()).get(CarritoViewModel.class);

        //Inicializa el repo/service
        productosRepository = new ProductosRepository(requireActivity().getApplication());
        carritoViewModel.setProductosRepository(productosRepository);

        //Inicializa el adaptador y se le pasa el repo
        carritoAdapter = new CarritoAdapter(new ArrayList<>());
        carritoRecyclerView.setAdapter(carritoAdapter);

        // Observar los cambios en los productos del carrito
        carritoViewModel.getCarritoItems().observe(getViewLifecycleOwner(), carritoItems -> {
            carritoAdapter.updateData(carritoItems);
            carritoViewModel.observarProductos(carritoItems);  // Actualiza los productos observados
        });

        // Observar el mapa de productos
        carritoViewModel.getProductosCache().observe(getViewLifecycleOwner(), productosMap -> {
            carritoAdapter.updateProductos(productosMap);  // Actualiza el adaptador con los productos cargados
        });

        ImageButton goBack = view.findViewById(R.id.goBackIcon);
        goBack.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(requireActivity(), R.id.fragmentContainerView);
            navController.navigateUp();
        });

        return view;
    }
}