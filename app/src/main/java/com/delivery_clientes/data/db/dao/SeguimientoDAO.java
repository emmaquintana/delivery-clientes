package com.delivery_clientes.data.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.delivery_clientes.data.db.entities.Seguimiento;

import java.util.List;

@Dao
public interface SeguimientoDAO {

    @Query("SELECT * FROM seguimiento")
    List<Seguimiento> obtenerSeguimientos();

    @Query("SELECT * FROM seguimiento WHERE pedido_id = :pedido_id")
    List<Seguimiento> obtenerSeguimientoDePedidoPorIdPedido(int pedido_id);

    @Query("SELECT * FROM seguimiento WHERE pedido_id = :pedido_id AND estado = :estado")
    List<Seguimiento> obtenerSeguimientoDePedidoPorIdAndEstado(int pedido_id, String estado);


    @Insert
    void insertarSeguimiento(Seguimiento seguimiento);

    @Update
    void actualizarSeguimiento(Seguimiento seguimiento);
}
