package com.delivery_clientes.data.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.delivery_clientes.data.db.entities.PedidoDetalle;

import java.util.List;

@Dao
public interface PedidosDetalleDAO {

    @Query("SELECT * FROM pedido_detalle")
    List<PedidoDetalle> obtenerTodosPedidoDetalle();

    //Este devuelve solamente un registro de pedido_detalle mediante el id que lo identifica
    @Query("SELECT * FROM pedido_detalle WHERE id = :pedido_detalle_id")
    PedidoDetalle obtenerDetallePedidoPorId(int pedido_detalle_id);

    //Este devuelve todos los productos incluidos en el pedido
    @Query("SELECT * FROM pedido_detalle WHERE pedido_id = :pedido_id")
    LiveData<List<PedidoDetalle>> obtenerDetallePedidoPorIdPedido(long pedido_id);

    @Query("SELECT * FROM pedido_detalle")
    LiveData<List<PedidoDetalle>> obtenerDetalleLive();

    @Insert
    void insertarPedidoDetalle(PedidoDetalle pedidoDetalle);
}
