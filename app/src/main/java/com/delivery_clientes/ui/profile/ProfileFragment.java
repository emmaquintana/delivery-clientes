package com.delivery_clientes.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class ProfileFragment extends Fragment {

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
                    break;
                case "Mis pedidos":
                    break;
                case "Carrito":
                    break;
                case "Preguntas frecuentes":
                    navController.navigate(R.id.action_profileFragment_to_faqFragment);
                    break;
                case "Cerrar sesión":
                    break;
            }
            Toast.makeText(requireContext(), "Seleccionaste: " + selectedItem, Toast.LENGTH_SHORT).show();
        });
    }
}