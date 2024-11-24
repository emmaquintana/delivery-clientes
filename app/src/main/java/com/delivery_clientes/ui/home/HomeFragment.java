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
import com.delivery_clientes.data.db.entities.Categorias;
import com.delivery_clientes.ui.home.productos.FiltrosProductosAdapter;
import com.delivery_clientes.ui.home.productos.ProductosAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerViewProductos;
    private RecyclerView recyclerViewFiltros;
    private ProductosAdapter productosAdapter;
    private FiltrosProductosAdapter filtrosProductosAdapter;
    private HomeViewModel homeViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //Configuracion de RV de productos
        recyclerViewProductos = view.findViewById(R.id.recyclerViewProductos);
        recyclerViewProductos.setLayoutManager(new LinearLayoutManager(getContext()));
        productosAdapter = new ProductosAdapter(new ArrayList<>());
        recyclerViewProductos.setAdapter(productosAdapter);

        //Configuracion de RV de filtros/categorias
        recyclerViewFiltros = view.findViewById(R.id.recyclerViewFiltros);
        recyclerViewFiltros.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        //Se instancia un adaptador de categorias, se crea una lista vacia inicialmente, y se le envia un listener, se genera el listado filtrado mediante el id de categoria
        filtrosProductosAdapter = new FiltrosProductosAdapter(new ArrayList<>(),
                categoria -> {
                    homeViewModel.getProductosLiveDataPorCategoria(categoria.getId())
                            .observe(getViewLifecycleOwner(), productosList -> {
                                productosAdapter.updateData(productosList);
                            });
                });
        recyclerViewFiltros.setAdapter(filtrosProductosAdapter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Configuracion de VM
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        //Observacion de Lista de Productos
        homeViewModel.getProductosLiveData().observe(getViewLifecycleOwner(), productosList -> {
            productosAdapter.updateData(productosList);
        });

        //Observacion de Lista de Filtros/Categorias
        homeViewModel.getCategoriasLiveData().observe(getViewLifecycleOwner(), categoriasList -> {
            //Categoria virtual para recuperar todos los productos en caso de que hayan sido filtrados
            Categorias categoriaTodos = new Categorias();
            categoriaTodos.setId(-1);
            categoriaTodos.setNombre("Todos");
            categoriasList.add(0,categoriaTodos);

            filtrosProductosAdapter.updateData(categoriasList);
        });
    }
}