package com.delivery_clientes.ui.pedidos.seguimiento;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.delivery_clientes.data.db.entities.Pedidos;
import com.delivery_clientes.data.db.entities.Seguimiento;
import com.delivery_clientes.data.repository.PedidosRepository;
import com.delivery_clientes.data.repository.SeguimientoRepository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executors;

public class SeguimientoViewModel extends AndroidViewModel {

    private final SeguimientoRepository seguimientoRepository;
    private final PedidosRepository pedidosRepository;

    public SeguimientoViewModel(@NonNull Application application){
        super(application);
        seguimientoRepository = new SeguimientoRepository(application);
        pedidosRepository = new PedidosRepository(application);
    }

    public LiveData<List<Seguimiento>> getSeguimientoLiveData(long id){
        return seguimientoRepository.obtenerSeguimientoLive(id);
    }

    // Dev Mode
    public void entregarPedido(long pedidoId){
        Seguimiento seguimiento = new Seguimiento((int) pedidoId, "Entregado", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date()));
        Executors.newSingleThreadExecutor().execute(() -> {
            seguimientoRepository.insertarSeguimiento(seguimiento);
            actualizarEstadoPedido(pedidoId);
        });
    }

    public void actualizarEstadoPedido(long pedidoId){
        Pedidos pedido = pedidosRepository.obtenerPedidoPorId((int) pedidoId);
        pedido.setEstado("Entregado");
        pedidosRepository.actualizarPedido(pedido);
    }
}
