package com.delivery_clientes.data.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.delivery_clientes.data.db.dao.UsuariosDAO;
import com.delivery_clientes.data.db.entities.Usuario;

@Database(entities = {Usuario.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UsuariosDAO usuariosDAO();
}
