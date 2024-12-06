package com.delivery_clientes.ui.profile.misDatos;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.delivery_clientes.R;
import com.delivery_clientes.data.db.entities.Clientes;
import com.delivery_clientes.data.db.entities.Usuario;
import com.google.android.material.textfield.TextInputEditText;

public class EditarDatosFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_editar_datos, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) requireActivity();
        activity.setSupportActionBar(toolbar);

        // Establece un título para el toolbar
        activity.getSupportActionBar().setTitle(R.string.editar_datos);

        // Habilita el botón de navegación
        if (activity.getSupportActionBar() != null) {
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Configura la acción del botón de navegación
        toolbar.setNavigationOnClickListener(v -> {
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.action_editarDatosFragment_to_misDatosFragment);
        });


            EditarDatosViewModel editarDatosViewModel = new ViewModelProvider(this).get(EditarDatosViewModel.class);

            // Obtiene los campos de entrada
            TextInputEditText apellidoInput = view.findViewById(R.id.apellidoInput);
            TextInputEditText nombreInput = view.findViewById(R.id.nombreInput);
            TextInputEditText emailInput = view.findViewById(R.id.emailInput);
            TextInputEditText telefonoInput = view.findViewById(R.id.telefonoInput);

            // Rellena los campos de entrada con la información actual del cliente
        // Observa los datos del cliente
        editarDatosViewModel.obtenerCliente().observe(getViewLifecycleOwner(), cli -> {
            if (cli != null) {
                // Rellena los campos con los valores del cliente
                apellidoInput.setText(cli.getApellido() != null ? cli.getApellido() : "");
                nombreInput.setText(cli.getNombre() != null ? cli.getNombre() : "");
                emailInput.setText(cli.getEmail() != null ? cli.getEmail() : "");
                telefonoInput.setText(cli.getTelefono() != null ? cli.getTelefono() : "");
            } else {
                Log.e("EditarDatosFragment", "Cliente no encontrado");
            }
        });

            // Obtiene el botón "Guardar cambios"
            Button btnGuardarCambios = view.findViewById(R.id.BtnGuardarCambios);

            // Cuando se presiona el botón "Guardar cambios", se actualiza la info del cliente
        btnGuardarCambios.setOnClickListener(v -> {
            String apellido = apellidoInput.getText().toString().trim();
            String nombre = nombreInput.getText().toString().trim();
            String email = emailInput.getText().toString().trim();
            String telefono = telefonoInput.getText().toString().trim();

            if (emailValido(email) && nombreValido(nombre) && apellidoValido(apellido) && telefonoValido(telefono)) {
                // Observa al cliente antes de realizar cambios
                editarDatosViewModel.obtenerCliente().observe(getViewLifecycleOwner(), cli -> {
                    if (cli != null) {
                        try {
                            String emailAnterior = cli.getEmail(); // Guarda el correo anterior

                            // Actualiza los datos del cliente
                            cli.setEmail(email);
                            cli.setApellido(apellido);
                            cli.setNombre(nombre);
                            cli.setTelefono(telefono);

                            // Actualiza al cliente en la base de datos
                            editarDatosViewModel.actualizarCliente(cli);

                            // Actualiza al usuario relacionado con el correo anterior
                            editarDatosViewModel.findUserByEmail(emailAnterior).observe(getViewLifecycleOwner(), usuario -> {
                                if (usuario != null) {
                                    usuario.setEmail(email);
                                    editarDatosViewModel.actualizarUsuario(usuario);
                                    Toast.makeText(getContext(), "Datos actualizados con éxito", Toast.LENGTH_SHORT).show();
                                } else {
                                    Log.e("EditarDatosFragment", "Usuario no encontrado para el email: " + emailAnterior);
                                }
                            });
                        } catch (Exception e) {
                            Log.e("EditarDatosFragment", "Error al actualizar el cliente: ", e);
                            Toast.makeText(getContext(), "Error al actualizar datos del cliente", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Log.e("EditarDatosFragment", "Cliente no encontrado");
                    }
                });
            } else {
                Toast.makeText(getContext(), "Ingrese datos válidos en los campos", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private boolean emailValido(String email) {
        return !email.isEmpty();
    }

    private boolean nombreValido(String nombre) {
        return !nombre.isEmpty();
    }

    private boolean apellidoValido(String apellido) {
        return !apellido.isEmpty();
    }

    private boolean telefonoValido(String telefono) {
        return !telefono.isEmpty();
    }
}