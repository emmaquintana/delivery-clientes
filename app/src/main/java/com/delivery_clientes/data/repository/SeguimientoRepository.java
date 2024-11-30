package com.delivery_clientes.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.delivery_clientes.data.db.AppDatabase;
import com.delivery_clientes.data.db.dao.SeguimientoDAO;
import com.delivery_clientes.data.db.entities.Seguimiento;

import java.util.List;

public class SeguimientoRepository {

    private final SeguimientoDAO seguimientoDAO;

    public SeguimientoRepository(Application application){
        AppDatabase db = AppDatabase.getInstance(application);
        seguimientoDAO = db.seguimientoDAO();
    }

    public List<Seguimiento> obtenerSeguimiento() {return seguimientoDAO.obtenerSeguimientos();}

    public LiveData<List<Seguimiento>> obtenerSeguimientoLive(int pedidoId){
        return seguimientoDAO.obtenerSeguimientoDePedidoPorIdPedido(pedidoId);
    }

    public void insertarSeguimiento(Seguimiento seg) {seguimientoDAO.insertarSeguimiento(seg);}

    public void actualizarSeguimiento(Seguimiento seg){seguimientoDAO.actualizarSeguimiento(seg);}

    public LiveData<Seguimiento> obtenerSeguimientoEstadoDePedidoPorIdPedido(int pedidoId){
        return seguimientoDAO.obtenerSeguimientoEstadoDePedidoPorIdPedido(pedidoId);
    }

    public LiveData<Seguimiento> obtenerEstadoLive(int id){
        return seguimientoDAO.obtenerSeguimientoEstadoDePedidoPorIdPedido(id);
    }
}
