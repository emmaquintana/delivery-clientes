package com.delivery_clientes.ui.profile.misDatos;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.delivery_clientes.R;

public class MisDatosFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mis_datos, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) requireActivity();
        activity.setSupportActionBar(toolbar);

        // Establece un título para el toolbar
        activity.getSupportActionBar().setTitle(R.string.title_mis_datos);

        // Habilita el botón de navegación
        if (activity.getSupportActionBar() != null) {
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Configura la acción del botón de navegación
        toolbar.setNavigationOnClickListener(v -> {
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.action_misDatosFragment_to_profileFragment);
        });

        Button btnEditarDatos = view.findViewById(R.id.BtnEditarDatos);
        btnEditarDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.action_misDatosFragment_to_editarDatosFragment);
            }
        });
    }
}