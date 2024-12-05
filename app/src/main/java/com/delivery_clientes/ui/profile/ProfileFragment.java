package com.delivery_clientes.ui.profile;

import android.content.Intent;
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
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.delivery_clientes.LoginActivity;
import com.delivery_clientes.R;
import com.delivery_clientes.data.db.AppDatabase;
import com.delivery_clientes.data.db.entities.Clientes;
import com.delivery_clientes.ui.login.LoginViewModel;
import com.delivery_clientes.ui.profile.misDatos.MisDatosViewModel;
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

        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        TextView user = view.findViewById(R.id.txt_username);
        LiveData<Clientes> cliente = profileViewModel.getCliente();
        cliente.observe(getViewLifecycleOwner(), clientes -> {
            user.setText(clientes.getNombre() + " " + clientes.getApellido());
        });

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

        LoginViewModel loginViewModel = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);


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
                    // Cerrar sesión
                    loginViewModel.logout();

                    // Navegar a la actividad de Login
                    Intent intent = new Intent(requireContext(), LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Elimina el stack de actividades
                    startActivity(intent);

                    // Finaliza la actividad actual
                    requireActivity().finish();
                    break;
            }
        });
    }
}