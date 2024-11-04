package com.delivery_clientes.data.db.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDate;

@Entity(tableName = "usuarios")
public class Usuario {

    @PrimaryKey(autoGenerate = true)
    private int idUsuarios;
    private String email;
    private String password;
    private int clientes_id;

    public Usuario(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public int getClientes_id() {
        return clientes_id;
    }

    public void setClientes_id(int clientes_id) {
        this.clientes_id = clientes_id;
    }

    public int getIdUsuarios() {
        return idUsuarios;
    }

    public void setIdUsuarios(int idUsuarios) {
        this.idUsuarios = idUsuarios;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
