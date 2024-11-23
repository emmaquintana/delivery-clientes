package com.delivery_clientes.data.db.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "categorias")
public class Categorias {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String nombre;

    public Categorias() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public Categorias(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
