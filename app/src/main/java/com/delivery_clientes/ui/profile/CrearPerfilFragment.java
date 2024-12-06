package com.delivery_clientes.ui.profile;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.delivery_clientes.R;
import com.google.android.material.textfield.TextInputEditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

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

        CrearPerfilViewModelFactory factory = new CrearPerfilViewModelFactory(getActivity().getApplication());
        CrearPerfilViewModel crearPerfilViewModel = new ViewModelProvider(this, factory).get(CrearPerfilViewModel.class);

        TextInputEditText fechaNacimientoInput = view.findViewById(R.id.fechaNacimientoInput);

        // Configura el DatePickerDialog
        fechaNacimientoInput.setOnClickListener(v -> {
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            // Muestra el DatePicker
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    getContext(),
                    (DatePicker view1, int selectedYear, int selectedMonth, int selectedDay) -> {
                        // Actualiza el campo con la fecha seleccionada
                        String formattedDate = String.format("%02d-%02d-%04d", selectedDay, selectedMonth + 1, selectedYear);
                        fechaNacimientoInput.setText(formattedDate);
                    },
                    year, month, day
            );

            // Opcional: Limitar la fecha seleccionable para evitar menores de 18 años
            calendar.set(Calendar.YEAR, year - 18);
            datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());

            datePickerDialog.show();
        });

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

                if (TextUtils.isEmpty(apellido) || TextUtils.isEmpty(nombre) || TextUtils.isEmpty(fechaNacimiento) || TextUtils.isEmpty(telefono)) {
                    Toast.makeText(getContext(), "Rellene todos los campos", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Usar SimpleDateFormat para analizar la fecha
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                Date fechaNacimientoDate = null;

                try {
                    // Intentamos parsear la fecha del input
                    fechaNacimientoDate = sdf.parse(fechaNacimiento);

                    // Si la fecha es válida, convertirla a un Calendar
                    Calendar fechaNacimientoCalendar = Calendar.getInstance();
                    fechaNacimientoCalendar.setTime(fechaNacimientoDate);

                    // Obtenemos la fecha actual
                    Calendar fechaActual = Calendar.getInstance();

                    // Calculamos la edad
                    int edad = fechaActual.get(Calendar.YEAR) - fechaNacimientoCalendar.get(Calendar.YEAR);

                    // Si el cumpleaños aún no ha ocurrido este año, restamos 1 de la edad
                    if (fechaActual.get(Calendar.MONTH) < fechaNacimientoCalendar.get(Calendar.MONTH) ||
                            (fechaActual.get(Calendar.MONTH) == fechaNacimientoCalendar.get(Calendar.MONTH) && fechaActual.get(Calendar.DAY_OF_MONTH) < fechaNacimientoCalendar.get(Calendar.DAY_OF_MONTH))) {
                        edad--;
                    }

                    // Verificamos si tiene 18 años o más
                    if (edad < 18) {
                        Toast.makeText(getContext(), "Debe tener al menos 18 años", Toast.LENGTH_SHORT).show();
                        return;
                    }

                } catch (ParseException e) {
                    // Si no se puede parsear la fecha
                    Toast.makeText(getContext(), "Formato de fecha inválido. Use dd-MM-yyyy", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Obtiene el email desde los argumentos del bundle
                Bundle bundle = getArguments();
                String email = bundle.getString("email");

                // Crear cliente en la base de datos
                crearPerfilViewModel.crearCliente(nombre, apellido, fechaNacimiento, telefono, email);

                // Observa el resultado de la operación
                crearPerfilViewModel.getClienteCreado().observe(getViewLifecycleOwner(), clienteCreado -> {
                    if (clienteCreado != null) {
                        NavController navController = Navigation.findNavController(view);
                        Toast.makeText(getContext(), "Perfil creado exitosamente", Toast.LENGTH_SHORT).show();

                        // Verifica si el cliente tiene dirección
                        crearPerfilViewModel.getDireccionCliente(clienteCreado.getDireccion_id()).observe(getViewLifecycleOwner(), direcciones -> {
                            if (direcciones.getDireccion() == null){
                                navController.navigate(R.id.action_crearPerfilFragment_to_containerActivity);
                                Toast.makeText(getContext(), "Ingrese una dirección", Toast.LENGTH_SHORT).show();
                            }
                        });
                        // Navega al home
                        navController.navigate(R.id.action_crearPerfilFragment_to_containerActivity);
                    } else {
                        Toast.makeText(getContext(), "Error al crear el perfil", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }


}