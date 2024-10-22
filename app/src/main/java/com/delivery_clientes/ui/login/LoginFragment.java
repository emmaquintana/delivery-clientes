package com.delivery_clientes.ui.login;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.delivery_clientes.R;
import com.delivery_clientes.data.db.entities.Usuario;
import com.google.android.material.textfield.TextInputEditText;

public class LoginFragment extends Fragment {

    private LoginViewModel mViewModel;
    private TextInputEditText emailInput;
    private TextInputEditText passwordInput;
    private Button loginButton;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        mViewModel.register(new Usuario("usuario@email.com","usuario"));

        emailInput = view.findViewById(R.id.emailInput);
        passwordInput = view.findViewById(R.id.passwordInput);
        loginButton = view.findViewById(R.id.loginButton);

        loginButton.setOnClickListener(view1 -> {
            String email = emailInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();

            if(email.isEmpty() || password.isEmpty()){
                Toast.makeText(getContext(),"Por favor, ingrese sus credenciales", Toast.LENGTH_SHORT).show();
            } else {
                mViewModel.login(email,password);
            }
        });

        mViewModel.getLoginResult().observe(getViewLifecycleOwner(), loginResult -> {
            if(loginResult.isSuccess()){
                Toast.makeText(getContext(),"Login exitoso", Toast.LENGTH_SHORT).show();
                //Agregar navegacion aqui
            } else {
                Toast.makeText(getContext(), loginResult.getError(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}