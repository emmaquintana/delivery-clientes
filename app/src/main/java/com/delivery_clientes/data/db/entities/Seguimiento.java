package com.delivery_clientes.data.db.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDate;

@Entity(tableName = "seguimiento")
public class Seguimiento {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int pedido_id;
    private String estado;
    private String fecha_actualizacion;

    public Seguimiento() {
    }

    public Seguimiento(int pedido_id, String estado, String fecha_actualizacion) {
        this.pedido_id = pedido_id;
        this.estado = estado;
        this.fecha_actualizacion = fecha_actualizacion;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFecha_actualizacion() {
        return fecha_actualizacion;
    }

    public void setFecha_actualizacion(String fecha_actualizacion) {
        this.fecha_actualizacion = fecha_actualizacion;
    }
}
