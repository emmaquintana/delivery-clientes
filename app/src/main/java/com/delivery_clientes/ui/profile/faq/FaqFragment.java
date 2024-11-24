package com.delivery_clientes.ui.profile.faq;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.delivery_clientes.R;


public class FaqFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Configurar el toolbar
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) requireActivity();
        activity.setSupportActionBar(toolbar);

        // Establecer el título
        if (activity.getSupportActionBar() != null) {
            activity.getSupportActionBar().setTitle("Preguntas Frecuentes");
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Configurar el botón de navegación
        toolbar.setNavigationOnClickListener(v -> {
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.action_faqFragment_to_profileFragment);
        });

        // Configurar los botones y sus respuestas
        setupFaqButtons(view);
    }

    private void setupFaqButtons(View view) {
        // FAQ 1
        Button btnTrigger1 = view.findViewById(R.id.BtnTrigger1);
        TextView textView1 = view.findViewById(R.id.textView1);
        btnTrigger1.setOnClickListener(v -> {
            // Toggle visibility
            textView1.setVisibility(textView1.getVisibility() == View.VISIBLE ?
                    View.GONE : View.VISIBLE);
        });

        // FAQ 2
        Button btnTrigger2 = view.findViewById(R.id.BtnTrigger2);
        TextView textView2 = view.findViewById(R.id.textView2);
        btnTrigger2.setOnClickListener(v -> {
            textView2.setVisibility(textView2.getVisibility() == View.VISIBLE ?
                    View.GONE : View.VISIBLE);
        });

        // FAQ 3
        Button btnTrigger3 = view.findViewById(R.id.BtnTrigger3);
        TextView textView3 = view.findViewById(R.id.textView3);
        btnTrigger3.setOnClickListener(v -> {
            textView3.setVisibility(textView3.getVisibility() == View.VISIBLE ?
                    View.GONE : View.VISIBLE);
        });
    }
}