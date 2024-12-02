package com.delivery_clientes.ui.profile;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class CrearPerfilViewModelFactory implements ViewModelProvider.Factory {
    private final Application application;

    public CrearPerfilViewModelFactory(Application application) {
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(CrearPerfilViewModel.class)) {
            return (T) new CrearPerfilViewModel(application);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}