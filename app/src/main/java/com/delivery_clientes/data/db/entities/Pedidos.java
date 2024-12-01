package com.delivery_clientes.data.db.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "pedidos")
public class Pedidos {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private long cliente_id;
    private int negocio_id;
    private int repartidor_id;
    private String fecha_pedido;
    private String estado;

    public Pedidos() {
    }

    public Pedidos(long cliente_id, int negocio_id, int repartidor_id, String fecha_pedido, String estado) {
        this.cliente_id = cliente_id;
        this.negocio_id = negocio_id;
        this.repartidor_id = repartidor_id;
        this.fecha_pedido = fecha_pedido;
        this.estado = estado;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCliente_id() {
        return cliente_id;
    }

    public void setCliente_id(long cliente_id) {
        this.cliente_id = cliente_id;
    }

    public int getNegocio_id() {
        return negocio_id;
    }

    public void setNegocio_id(int negocio_id) {
        this.negocio_id = negocio_id;
    }

    public int getRepartidor_id() {
        return repartidor_id;
    }

    public void setRepartidor_id(int repartidor_id) {
        this.repartidor_id = repartidor_id;
    }

    public String getFecha_pedido() {
        return fecha_pedido;
    }

    public void setFecha_pedido(String fecha_pedido) {
        this.fecha_pedido = fecha_pedido;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
