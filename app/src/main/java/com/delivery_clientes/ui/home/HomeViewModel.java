package com.delivery_clientes.ui.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.delivery_clientes.data.db.entities.Categorias;
import com.delivery_clientes.data.db.entities.Productos;
import com.delivery_clientes.data.repository.CategoriasRepository;
import com.delivery_clientes.data.repository.ProductosRepository;

import java.util.List;

public class HomeViewModel extends AndroidViewModel {

    private final ProductosRepository productosRepository;
    private final LiveData<List<Productos>> productosLiveData;
    private LiveData<List<Productos>> productosFiltradosLiveData;

    private final CategoriasRepository categoriasRepository;
    private final LiveData<List<Categorias>> categoriasLiveData;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        productosRepository = new ProductosRepository(application);
        productosLiveData = productosRepository.obtenerProductosLive();

        categoriasRepository = new CategoriasRepository(application);
        categoriasLiveData = categoriasRepository.obtenerCategoriasLive();
    }

    public LiveData<List<Productos>> getProductosLiveData() {
        return productosLiveData;
    }

    public LiveData<List<Categorias>> getCategoriasLiveData() { return categoriasLiveData; }

    public LiveData<List<Productos>> getProductosLiveDataPorCategoria(int categoria_id){
        if (categoria_id == -1){
            return productosLiveData;
        }
        productosFiltradosLiveData = productosRepository.obtenerProductosLivePorCategoria(categoria_id);
        return productosFiltradosLiveData;
    }

    //
    public LiveData<List<Productos>> getProductosFiltradosLiveData() {
        return productosFiltradosLiveData;
    }


}