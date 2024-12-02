package com.delivery_clientes.data.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.delivery_clientes.data.db.entities.Pedidos;

import java.time.LocalDate;
import java.util.List;

@Dao
public interface PedidosDAO {

    @Query("SELECT * FROM pedidos")
    List<Pedidos> obtenerPedidos();

    @Query("SELECT * FROM pedidos WHERE id = :pedido_id ORDER BY id DESC")
    Pedidos obtenerPedidoPorId(int pedido_id);

    @Query("SELECT * FROM pedidos WHERE cliente_id = :cliente_id ORDER BY id DESC")
    List<Pedidos> obtenerPedidosPorClienteId(int cliente_id);

    @Query("SELECT * FROM pedidos WHERE cliente_id = :cliente_id AND negocio_id = :negocio_id")
    List<Pedidos> obtenerPedidosPorClienteIdAndNegocioId(int cliente_id, int negocio_id);

    //Estado esta actualmente definido como string, posiblemente cambiado en un futuro
    @Query("SELECT * FROM pedidos WHERE cliente_id = :cliente_id AND estado = :estado")
    List<Pedidos> obtenerPedidosPorClienteIdAndEstado(int cliente_id, String estado);

    @Query("SELECT * FROM pedidos WHERE cliente_id = :cliente_id AND fecha_pedido = :fecha_pedido")
    List<Pedidos> obtenerPedidosPorClienteIdAndFecha(int cliente_id, String fecha_pedido);

    @Query("SELECT * FROM pedidos ORDER BY id DESC")
    LiveData<List<Pedidos>> obtenerPedidosLive();

    @Query("SELECT * FROM pedidos WHERE cliente_id = :cliente_id ORDER BY id DESC")
    LiveData<List<Pedidos>> obtenerPedidosPorClienteIdLive(int cliente_id);

    @Insert
    long insertarPedido(Pedidos pedido);

    @Update
    void actualizarPedido(Pedidos pedido);
}
