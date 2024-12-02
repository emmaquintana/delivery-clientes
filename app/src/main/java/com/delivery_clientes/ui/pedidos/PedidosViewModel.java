package com.delivery_clientes.ui.pedidos;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.delivery_clientes.data.db.entities.Clientes;
import com.delivery_clientes.data.db.entities.Pedidos;
import com.delivery_clientes.data.db.entities.Seguimiento;
import com.delivery_clientes.data.repository.ClientesRepository;
import com.delivery_clientes.data.repository.PedidosRepository;
import com.delivery_clientes.data.repository.SeguimientoRepository;

import java.util.List;
import java.util.concurrent.Executors;

public class PedidosViewModel extends AndroidViewModel {

    private final PedidosRepository pedidosRepository;
    private final ClientesRepository clientesRepository;
    private final SeguimientoRepository seguimientoRepository;

    public PedidosViewModel(@NonNull Application application) {
        super(application);
        pedidosRepository = new PedidosRepository(application);
        clientesRepository = new ClientesRepository(application);
        seguimientoRepository = new SeguimientoRepository(application);
    }

    public LiveData<List<Pedidos>> getPedidosLiveData() {
        MediatorLiveData<List<Pedidos>> pedidosLiveData = new MediatorLiveData<>();
        LiveData<Clientes> clientesLiveData = getCliente(getEmail());

        pedidosLiveData.addSource(clientesLiveData, cliente -> {
            if (cliente != null) {
                pedidosLiveData.addSource(pedidosRepository.obtenerPedidosPorIdClienteLive(cliente.getId()), pedidos -> {
                    pedidosLiveData.setValue(pedidos);
                });
            } else {
                Log.e("EN PedidosViewModel","cliente_id nulo");
            }
        });

        return pedidosLiveData;
    }

    private String getEmail() {
        SharedPreferences sharedPreferences = getApplication().getSharedPreferences("AppPreferences", Context.MODE_PRIVATE);
        return sharedPreferences.getString("email", null);  // Retorna el email almacenado, o null si no existe
    }

    private LiveData<Clientes> getCliente(String email){
        return clientesRepository.obtenerClientesPorEmailLive(email);
    }

    public LiveData<Seguimiento> getSeguimiento(long pedidoId){
        return seguimientoRepository.obtenerSeguimientoEstadoDePedidoPorIdPedido(pedidoId);
    }
}