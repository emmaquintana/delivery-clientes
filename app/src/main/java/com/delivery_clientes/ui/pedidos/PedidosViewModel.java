package com.delivery_clientes.ui.pedidos;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.delivery_clientes.data.db.entities.Pedidos;
import com.delivery_clientes.data.repository.PedidosRepository;

import java.util.List;

public class PedidosViewModel extends AndroidViewModel {

    private final PedidosRepository pedidosRepository;
    private final LiveData<List<Pedidos>> pedidosLiveData;

    public PedidosViewModel(@NonNull Application application) {
        super(application);
        pedidosRepository = new PedidosRepository(application);
        pedidosLiveData = pedidosRepository.obtenerPedidosLive();

    }

    public LiveData<List<Pedidos>> getPedidosLiveData() {
        return pedidosLiveData;
    }
}