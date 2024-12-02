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
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.delivery_clientes.R;
import com.delivery_clientes.data.db.entities.Negocios;
import com.delivery_clientes.data.db.entities.Pedidos;
import com.delivery_clientes.ui.pedidos.PedidosViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DetalleFragment extends Fragment {

    private RecyclerView recyclerViewDetalle;
    private DetalleAdapter detalleAdapter;
    private DetalleViewModel detalleViewModel;
    private Button seguimientoButton;
    private TextView pedido;
    private TextView negocio;
    private TextView repartidor;
    private TextView fecha;
    private TextView estado;
    private TextView total;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pedido_detalle, container, false);

        recyclerViewDetalle = view.findViewById(R.id.recyclerViewPedidoDetalle);
        recyclerViewDetalle.setLayoutManager(new LinearLayoutManager(getContext()));

        detalleAdapter = new DetalleAdapter(new ArrayList<>());
        recyclerViewDetalle.setAdapter(detalleAdapter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        long id = bundle.getLong("pedidoId");
        int negocioId = bundle.getInt("negocio");
        int repartidorId = bundle.getInt("repartidor");
        String fechaPed = bundle.getString("fecha");
        String estadoPed = bundle.getString("estado");

        pedido = view.findViewById(R.id.txtVwPDpedidoValue);
        negocio = view.findViewById(R.id.txtVwPDnegocioValue);
        repartidor = view.findViewById(R.id.txtVwPDrepartidorValue);
        fecha = view.findViewById(R.id.txtVwPDfechaValue);
        estado = view.findViewById(R.id.txtVwPDestadoValue);
        total = view.findViewById(R.id.txtVwPDTotalValue);

        detalleViewModel = new ViewModelProvider(this).get(DetalleViewModel.class);
        detalleViewModel.getDetalleLiveData(id).observe(getViewLifecycleOwner(), pedidosList -> {
            detalleAdapter.updateData(pedidosList);
            double totalPedido = detalleAdapter.getTotal();
            total.setText(String.format("$%.2f", totalPedido));
        });

        pedido.setText("'" + id + "'");


        detalleViewModel.getNegocio(negocioId).observe(getViewLifecycleOwner(), negocios -> {
            if (negocios != null){
                negocio.setText(negocios.getNombre());
            } else {
                negocio.setText("Desconocido");
            }
        });

        detalleViewModel.getRepartidores(repartidorId).observe(getViewLifecycleOwner(), repartidores -> {
            if (repartidores != null){
                repartidor.setText(repartidores.getNombre() + " " + repartidores.getApellido());
            } else {
                repartidor.setText("Desconocido");
            }
        });

        detalleViewModel.getSeguimiento((int) id).observe(getViewLifecycleOwner(), seguimiento -> {
            if (seguimiento != null){
                estado.setText(seguimiento.getEstado());
            } else {
                estado.setText(estadoPed);
            }
        });

        fecha.setText(fechaPed);

        seguimientoButton = view.findViewById(R.id.btnDetSeguimiento);

        seguimientoButton.setOnClickListener(view1 -> {
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.action_detalleFragment_to_seguimientoFragment, bundle);
        });

    }
}
