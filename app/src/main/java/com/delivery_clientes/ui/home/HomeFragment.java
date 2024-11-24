package com.delivery_clientes.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

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
import com.delivery_clientes.data.db.entities.Productos;
import com.delivery_clientes.ui.home.productos.FiltrosProductosAdapter;
import com.delivery_clientes.ui.home.productos.ProductosAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerViewProductos;
    private RecyclerView recyclerViewFiltros;
    private ProductosAdapter productosAdapter;
    private FiltrosProductosAdapter filtrosProductosAdapter;
    private HomeViewModel homeViewModel;
    private SearchView searchView;

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

        //Se instancia el adaptador de categorias. Se ejecuta una funcion lambda
        //Fun Lambda -> Obtiene los productos filtrados por categoria, se observan los cambios en el listado de productos y se actualiza su adaptador
        filtrosProductosAdapter = new FiltrosProductosAdapter(new ArrayList<>(),
                categoria -> {
                    homeViewModel.getProductosLiveDataPorCategoria(categoria.getId())
                            .observe(getViewLifecycleOwner(), productosList -> {
                                productosAdapter.updateData(productosList);
                            });
                });
        recyclerViewFiltros.setAdapter(filtrosProductosAdapter);

        //Configuracion de barra de busqueda
        searchView = view.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                filtrarProductosPorNombre(s);
                return true;
            }
        });

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

    private void filtrarProductosPorNombre(String s){
        if(homeViewModel.getProductosLiveData() != null){
            homeViewModel.getProductosLiveData().observe(getViewLifecycleOwner(), productosList -> {
                List<Productos> listaFiltrada = new ArrayList<>();
                for (Productos producto : productosList){
                    if(producto.getNombre().toLowerCase().contains(s.toLowerCase())){
                        listaFiltrada.add(producto);
                    }
                }
                productosAdapter.updateData(listaFiltrada);
            });
        }
    }
}