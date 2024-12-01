package com.delivery_clientes.ui.profile;

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
import android.widget.Toast;

import com.delivery_clientes.R;
import com.google.android.material.textfield.TextInputEditText;

public class CrearPerfilFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crear_perfil, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) requireActivity();
        activity.setSupportActionBar(toolbar);

        // Establece un título para el toolbar
        activity.getSupportActionBar().setTitle(R.string.title_crear_perfil);

        Button btnCrearPerfil = view.findViewById(R.id.BtnCrearPerfil);

        btnCrearPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextInputEditText apellidoInput = getView().findViewById(R.id.apellidoInput);
                TextInputEditText nombreInput = getView().findViewById(R.id.nombreInput);
                TextInputEditText fechaNacimientoInput = getView().findViewById(R.id.fechaNacimientoInput);
                TextInputEditText telefonoInput = getView().findViewById(R.id.telefonoInput);

                String apellido = apellidoInput.getText().toString().trim();
                String nombre = nombreInput.getText().toString().trim();
                String fechaNacimiento = fechaNacimientoInput.getText().toString().trim();
                String telefono = telefonoInput.getText().toString().trim();

                if (apellido.isEmpty() || nombre.isEmpty() || fechaNacimiento.isEmpty() || telefono.isEmpty()) {
                    Toast.makeText(getContext(), "Rellene todos los campos", Toast.LENGTH_SHORT).show();
                }
                else {
                    // No sé qué hacer
                }
            }
        });
    }
}