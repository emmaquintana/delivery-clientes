package com.delivery_clientes.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.delivery_clientes.data.db.AppDatabase;
import com.delivery_clientes.data.db.dao.PedidosDetalleDAO;
import com.delivery_clientes.data.db.entities.PedidoDetalle;

import java.util.List;

public class DetalleRepository {

    private final PedidosDetalleDAO pedidosDetalleDAO;

    public DetalleRepository(Application application){
        AppDatabase db = AppDatabase.getInstance(application);
        pedidosDetalleDAO = db.pedidosDetalleDAO();
    }

    public List<PedidoDetalle> obtenerPedidoDetalle() {return pedidosDetalleDAO.obtenerTodosPedidoDetalle();}

    public void insertarDetallePedido(PedidoDetalle ped) {pedidosDetalleDAO.insertarPedidoDetalle(ped);}

    public LiveData<List<PedidoDetalle>> obtenerDetalleLive(int id){
        return pedidosDetalleDAO.obtenerDetallePedidoPorIdPedido(id);
    }

}
