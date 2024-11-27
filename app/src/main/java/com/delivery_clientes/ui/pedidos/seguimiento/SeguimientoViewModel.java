package com.delivery_clientes.ui.pedidos.seguimiento;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.delivery_clientes.data.db.entities.Seguimiento;
import com.delivery_clientes.data.repository.SeguimientoRepository;

import java.util.List;

public class SeguimientoViewModel extends AndroidViewModel {

    private final SeguimientoRepository seguimientoRepository;
    private final LiveData<List<Seguimiento>> seguimientoLiveData;

    public SeguimientoViewModel(@NonNull Application application){
        super(application);
        seguimientoRepository = new SeguimientoRepository(application);
        seguimientoLiveData = seguimientoRepository.obtenerSeguimientoLive();
    }

    public LiveData<List<Seguimiento>> getSeguimientoLiveData(){
        return seguimientoLiveData;
    }
}
