package com.delivery_clientes.ui.pedidos.detalle;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.delivery_clientes.data.repository.DetalleRepository;

public class DetalleViewModel extends AndroidViewModel {

    private final DetalleRepository detalleRepository;

    public DetalleViewModel(@NonNull Application application){
        super(application);
        detalleRepository = new DetalleRepository(application);
    }
}
