package com.delivery_clientes.data.db.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "pedido_detalle")
public class PedidoDetalle {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int pedido_id;
    private int producto_id;
    private int cantidad;
    private double precio_unitario;

    public PedidoDetalle() {
    }

    public PedidoDetalle(int pedido_id, int producto_id, int cantidad, double precio_unitario) {
        this.pedido_id = pedido_id;
        this.producto_id = producto_id;
        this.cantidad = cantidad;
        this.precio_unitario = precio_unitario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPedido_id() {
        return pedido_id;
    }

    public void setPedido_id(int pedido_id) {
        this.pedido_id = pedido_id;
    }

    public int getProducto_id() {
        return producto_id;
    }

    public void setProducto_id(int producto_id) {
        this.producto_id = producto_id;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio_unitario() {
        return precio_unitario;
    }

    public void setPrecio_unitario(double precio_unitario) {
        this.precio_unitario = precio_unitario;
    }
}
