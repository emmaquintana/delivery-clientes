package com.delivery_clientes.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.delivery_clientes.R;
import com.delivery_clientes.data.db.AppDatabase;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) requireActivity();
        activity.setSupportActionBar(toolbar);

        // Establece un título para el toolbar
        activity.getSupportActionBar().setTitle(R.string.title_perfil);

        // Habilita el botón de navegación
        if (activity.getSupportActionBar() != null) {
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Configura la acción del botón de navegación
        toolbar.setNavigationOnClickListener(v -> {
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.action_profileFragment_to_homeFragment);
        });

        // Referencia al ListView desde la vista del fragmento
        ListView listView = view.findViewById(R.id.lst_opciones);

        // Datos para el ListView
        String[] items = {
                "Mis datos",
                "Mis pedidos",
                "Carrito",
                "Preguntas frecuentes",
                "Cerrar sesión"
        };

        // Configura el adaptador
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_list_item_1,
                items
        );

        listView.setAdapter(adapter);

        // Maneja clics en las opciones del ListView
        listView.setOnItemClickListener((parent, itemView, position, id) -> {
            String selectedItem = items[position];
            NavController navController = Navigation.findNavController(view);
            switch (selectedItem) {
                case "Mis datos":
                    navController.navigate(R.id.action_profileFragment_to_misDatosFragment);
                    break;
                case "Mis pedidos":
                    navController.navigate(R.id.action_profileFragment_to_pedidosFragment);
                    break;
                case "Carrito":
                    navController.navigate(R.id.action_profileFragment_to_carritoFragment);
                    break;
                case "Preguntas frecuentes":
                    navController.navigate(R.id.action_profileFragment_to_faqFragment);
                    break;
                case "Cerrar sesión":
                    break;
            }
        });

        AppDatabase db = AppDatabase.getInstance(requireContext());
        profileViewModel = new ProfileViewModel(db);

        // Obtener instancia de la base de datos
        AppDatabase database = AppDatabase.getInstance(requireContext());

        // Crear el ViewModel directamente
        profileViewModel = new ProfileViewModel(database);

        // Cargar datos del cliente (por ejemplo, con un ID 1)
        profileViewModel.cargarClientePorId(1);

        // Observar el LiveData del cliente
        profileViewModel.getCliente().observe(getViewLifecycleOwner(), cliente -> {
            if (cliente != null) {
                // Actualiza la UI con los datos del cliente
                TextView username = view.findViewById(R.id.txt_username);
                username.setText(profileViewModel.getCliente().getValue().getNombre());

                // Obtiene la dirección del cliente y en base a ello establece su provincia y ciudad
                // ...
            }
        });
    }
}