package com.delivery_clientes.ui.register;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.delivery_clientes.R;
import com.delivery_clientes.data.db.entities.Usuario;
import com.google.android.material.textfield.TextInputEditText;

public class SignUpFragment extends Fragment {

    private SignUpViewModel signUpViewModel;
    private TextInputEditText emailInput;
    private TextInputEditText passwordInput;
    private TextInputEditText confirmPasswordInput;
    private Button signUpButton;

    public SignUpFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        signUpViewModel = new ViewModelProvider(this).get(SignUpViewModel.class);

        emailInput = view.findViewById(R.id.mailInput);
        passwordInput = view.findViewById(R.id.passwordInput);
        confirmPasswordInput = view.findViewById(R.id.confirmPasswordInput);
        signUpButton = view.findViewById(R.id.registerButton);

        signUpButton.setOnClickListener(v -> {
            String email = emailInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();
            String confirmPassword = confirmPasswordInput.getText().toString().trim();

            if(email.isEmpty() || password.isEmpty()){
                Toast.makeText(getContext(), "Por favor, ingrese todos los datos", Toast.LENGTH_SHORT).show();
            } else if (!password.equals(confirmPassword)){
                Toast.makeText(getContext(),"Las contraseñas no coinciden",Toast.LENGTH_SHORT).show();
            } else {
                signUpViewModel.register(email, password);
            }
        });

        signUpViewModel.getSignUpResult().observe(getViewLifecycleOwner(), signUpResult -> {
            if (signUpResult.isSuccess()){
                Toast.makeText(getContext(), signUpResult.getMessage(), Toast.LENGTH_SHORT).show();
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_signUpFragment_to_loginFragment);
            } else {
                Toast.makeText(getContext(), signUpResult.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        TextView loginLink = view.findViewById(R.id.loginLink);

        loginLink.setOnClickListener(v -> {
                NavController navController = Navigation.findNavController(view);
                navController.popBackStack();
        });

    }
}