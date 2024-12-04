package com.delivery_clientes;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.delivery_clientes.utils.NotificationHelper;
import com.jakewharton.threetenabp.AndroidThreeTen;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ContainerActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidThreeTen.init(this);
        setContentView(R.layout.activity_container);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Obtiene el NavHostFragment y su NavController
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragmentContainerView);
        if (navHostFragment != null) {
            NavController navController = navHostFragment.getNavController();

            // Configura la navegación con BottomNavigationView
            NavigationUI.setupWithNavController(bottomNavigationView, navController);

            // Agrega un listener para controlar la navegación y limpiar la pila
            bottomNavigationView.setOnItemSelectedListener(item -> {
                if (item.getItemId() == R.id.homeFragment) {
//                    // Navega a HomeFragment y limpia la pila
//                    navController.popBackStack(R.id.homeFragment, false);  // Limpia la pila antes de navegar
//                    navController.navigate(R.id.homeFragment);

                    navController.navigate(R.id.homeFragment, null, new NavOptions.Builder()
                            .setPopUpTo(R.id.homeFragment, true) // Elimina todos los fragmentos previos
                            .build());
                    return true;
                } else if (item.getItemId() == R.id.pedidosFragment) {
//                    // Navega a PedidosFragment y limpia la pila
//                    navController.popBackStack(R.id.pedidosFragment, false);
//                    navController.navigate(R.id.pedidosFragment);

                    navController.navigate(R.id.pedidosFragment, null, new NavOptions.Builder()
                            .setPopUpTo(R.id.homeFragment, true) // Elimina todos los fragmentos previos
                            .build());
                    return true;
                } else if (item.getItemId() == R.id.profileFragment) {
//                    // Navega a PerfilFragment y limpia la pila
//                    navController.popBackStack(R.id.profileFragment, false);
//                    navController.navigate(R.id.profileFragment);

                    navController.navigate(R.id.profileFragment, null, new NavOptions.Builder()
                            .setPopUpTo(R.id.homeFragment, true) // Elimina todos los fragmentos previos
                            .build());
                    return true;
                }
                return false;
            });

        }

        // Crea el canal de notificación
        NotificationHelper.createNotificationChannel(this);
    }

    @Override
    public void onBackPressed() {
        // Obtiene el NavHostFragment
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragmentContainerView);
        if (navHostFragment != null) {
            NavController navController = navHostFragment.getNavController();
            NavDestination currentDestination = navController.getCurrentDestination();

            // Verifica si el destino actual es HomeFragment
            if (currentDestination != null && currentDestination.getId() == R.id.homeFragment) {
                moveTaskToBack(true);
            } else {
                navController.navigate(R.id.homeFragment, null, new NavOptions.Builder()
                        .setPopUpTo(R.id.homeFragment, true)  // Limpia la pila de fragmentos previos
                        .build());
            }
        } else {
            super.onBackPressed();
        }
    }

}