package com.delivery_clientes.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.delivery_clientes.data.db.AppDatabase;
import com.delivery_clientes.data.db.dao.NegociosDAO;
import com.delivery_clientes.data.db.entities.Negocios;

import java.util.List;

public class NegociosRepository {

    private final NegociosDAO negociosDAO;

    public NegociosRepository(Application application){
        AppDatabase db = AppDatabase.getInstance(application);
        negociosDAO = db.negociosDAO();
    }

    public LiveData<Negocios> getNegocioLive(int id){
        return negociosDAO.obtenerNegocioPorId(id);
    }
}
