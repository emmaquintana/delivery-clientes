package com.delivery_clientes.ui.pedidos.seguimiento;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.delivery_clientes.R;
import com.delivery_clientes.utils.NotificationHelper;

import java.util.ArrayList;

public class SeguimientoFragment extends Fragment {

    private RecyclerView recyclerViewSeguimiento;
    private SeguimientoAdapter seguimientoAdapter;
    private SeguimientoViewModel seguimientoViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pedido_seguimiento, container, false);

        recyclerViewSeguimiento = view.findViewById(R.id.recyclerViewSeguimiento);
        recyclerViewSeguimiento.setLayoutManager(new LinearLayoutManager(getContext()));

        seguimientoAdapter = new SeguimientoAdapter(new ArrayList<>());
        recyclerViewSeguimiento.setAdapter(seguimientoAdapter);

        ImageButton goBack = view.findViewById(R.id.imgBtnSegBack);
        goBack.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(requireActivity(), R.id.fragmentContainerView);
            navController.navigateUp();
        });

        Button entregar = view.findViewById(R.id.btnSegEntregar);
        Bundle bundle = getArguments();
        long pedidoId = bundle.getLong("pedidoId");
        entregar.setOnClickListener(v -> {
            seguimientoViewModel.entregarPedido(pedidoId);
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        long pedidoId = bundle.getLong("pedidoId");

        seguimientoViewModel = new ViewModelProvider(this).get(SeguimientoViewModel.class);

        seguimientoViewModel.getSeguimientoLiveData(pedidoId).observe(getViewLifecycleOwner(), seguimientoList -> {
            seguimientoAdapter.updateData(getActivity(),seguimientoList);
        });
    }

}
