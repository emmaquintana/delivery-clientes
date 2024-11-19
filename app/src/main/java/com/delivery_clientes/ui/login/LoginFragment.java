package com.delivery_clientes.ui.login;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.delivery_clientes.R;
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
//        mViewModel.register(new Usuario("usuario@email.com","usuario"));

        //Controla si ya se inicio sesion
        if (mViewModel.isLoggedIn()) {
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.action_loginFragment_to_containerActivity);
            return;
        }

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

        //Observa el resultado del login
        mViewModel.getLoginResult().observe(getViewLifecycleOwner(), loginResult -> {

            //Login exitoso
            if(loginResult.isSuccess()){
                Toast.makeText(getContext(),loginResult.getError(), Toast.LENGTH_SHORT).show();
                //Navegacion a home
                NavController navController = Navigation.findNavController(view);
                if (navController.getCurrentDestination().getId() != R.id.homeFragment) {
                    navController.navigate(R.id.action_loginFragment_to_containerActivity);
                }

            //Login fallido
            } else {
                Toast.makeText(getContext(), loginResult.getError(), Toast.LENGTH_SHORT).show();
            }
        });

        //Navegacion a SignUp
        TextView registerLink = view.findViewById(R.id.registerLink);
        registerLink.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.action_loginFragment_to_signUpFragment);
        });

    }

}