package com.delivery_clientes.data.db.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "negocios")
public class Negocios {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String cuit;
    private String nombre;
    private String telefono;
    private String email;
    private int direccion_id;
    private int estado;

    public Negocios() {
    }

    public Negocios(String cuit, String nombre, String telefono, String email, int direccion_id, int estado) {
        this.cuit = cuit;
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
        this.direccion_id = direccion_id;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCuit() {
        return cuit;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getDireccion_id() {
        return direccion_id;
    }

    public void setDireccion_id(int direccion_id) {
        this.direccion_id = direccion_id;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
}
