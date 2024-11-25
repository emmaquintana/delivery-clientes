package com.delivery_clientes.ui.pedidos.detalle;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.delivery_clientes.R;
import com.delivery_clientes.data.db.entities.Pedidos;
import com.delivery_clientes.ui.pedidos.PedidosViewModel;

import java.util.ArrayList;

public class DetalleFragment extends Fragment {

    private RecyclerView recyclerViewDetalle;
    private DetalleAdapter detalleAdapter;
    private DetalleViewModel detalleViewModel;
    private Button seguimientoButton;
    private Pedidos pedido;
    private TextView idPedido;
    private TextView negocio;
    private TextView repartidor;
    private TextView fecha;
    private TextView estado;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pedido_detalle, container, false);

        recyclerViewDetalle = view.findViewById(R.id.recyclerViewPedidoDetalle);
        recyclerViewDetalle.setLayoutManager(new LinearLayoutManager(getContext()));

        detalleAdapter = new DetalleAdapter(new ArrayList<>(), new ArrayList<>());
        recyclerViewDetalle.setAdapter(detalleAdapter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        detalleViewModel = new ViewModelProvider(this).get(DetalleViewModel.class);

        detalleViewModel.getDetalleLiveData().observe(getViewLifecycleOwner(), pedidosList -> {
            detalleAdapter.updateData(pedidosList);
        });

        seguimientoButton = view.findViewById(R.id.btnDetSeguimiento);
//        idPedido = view.findViewById(R.id.txtVwPDpedidoValue);
//        negocio = view.findViewById(R.id.txtVwPDnegocioValue);
//        repartidor = view.findViewById(R.id.txtVwPDrepartidorValue);
//        fecha = view.findViewById(R.id.txtVwPDfechaValue);
//        estado = view.findViewById(R.id.txtVwPDestadoValue);
//
//        pedido.setText(detalleAdapter);

        seguimientoButton.setOnClickListener(view1 -> {
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.action_detalleFragment_to_seguimientoFragment);
        });

    }
}
