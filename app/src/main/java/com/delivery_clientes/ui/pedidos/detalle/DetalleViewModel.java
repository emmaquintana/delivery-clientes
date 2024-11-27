package com.delivery_clientes.ui.pedidos.detalle;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.delivery_clientes.data.db.entities.PedidoDetalle;
import com.delivery_clientes.data.repository.DetalleRepository;

import java.util.List;

public class DetalleViewModel extends AndroidViewModel {

    private final DetalleRepository detalleRepository;
    private final LiveData<List<PedidoDetalle>> detalleLiveData;

    public DetalleViewModel(@NonNull Application application){
        super(application);
        detalleRepository = new DetalleRepository(application);
        detalleLiveData = detalleRepository.obtenerDetalleLive();
    }

    public LiveData<List<PedidoDetalle>> getDetalleLiveData(){
        return detalleLiveData;
    }
}
