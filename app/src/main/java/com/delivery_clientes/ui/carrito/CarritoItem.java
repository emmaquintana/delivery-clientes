package com.delivery_clientes.ui.carrito;

import java.io.Serializable;

public class CarritoItem implements Serializable {

    private int id_producto;
    private int cantidad;

    public CarritoItem(int id_producto, int cantidad) {
        this.id_producto = id_producto;
        this.cantidad = cantidad;
    }

    public CarritoItem(int id_producto) {
        this.id_producto = id_producto;
        this.cantidad = 0;
    }

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
