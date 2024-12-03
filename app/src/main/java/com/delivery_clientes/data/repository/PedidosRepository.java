package com.delivery_clientes.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.delivery_clientes.data.db.AppDatabase;
import com.delivery_clientes.data.db.dao.PedidosDAO;
import com.delivery_clientes.data.db.entities.Pedidos;

import java.util.List;

public class PedidosRepository {

    private final PedidosDAO pedidosDAO;

    public PedidosRepository(Application application){
        AppDatabase db = AppDatabase.getInstance(application);
        pedidosDAO = db.pedidosDAO();
    }

    public List<Pedidos> obtenerPedidos() { return pedidosDAO.obtenerPedidos(); }

    public LiveData<List<Pedidos>> obtenerPedidosLive(){
        return pedidosDAO.obtenerPedidosLive();
    }

    public LiveData<List<Pedidos>> obtenerPedidosPorIdClienteLive(int cliente_id){
        return pedidosDAO.obtenerPedidosPorClienteIdLive(cliente_id);
    }

    public Pedidos obtenerPedidoPorId(int pedidoId){return pedidosDAO.obtenerPedidoPorId(pedidoId);}

    public long insertarPedido(Pedidos ped) { return pedidosDAO.insertarPedido(ped);}

    public void actualizarPedido(Pedidos ped) {pedidosDAO.actualizarPedido(ped);}
}
