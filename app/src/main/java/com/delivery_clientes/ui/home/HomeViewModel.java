package com.delivery_clientes.ui.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.delivery_clientes.data.db.dao.ProductosDAO;
import com.delivery_clientes.data.db.entities.Productos;
import com.delivery_clientes.data.repository.ProductosRepository;

import java.util.List;

public class HomeViewModel extends AndroidViewModel {

    private final ProductosRepository productosRepository;
    private final LiveData<List<Productos>> productosLiveData;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        productosRepository = new ProductosRepository(application);
        productosLiveData = productosRepository.obtenerProductosLive();
    }

    public LiveData<List<Productos>> getProductosLiveData() {
        return productosLiveData;
    }


}