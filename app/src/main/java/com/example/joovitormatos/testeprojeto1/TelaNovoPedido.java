package com.example.joovitormatos.testeprojeto1;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;

public class TelaNovoPedido extends AppCompatActivity {

    //public ScrollView srcview_NewPed_Busca;
    public Spinner spn_NewPed_Produto;
    public Spinner spn_NewPed_Cliente;
    public EditText edt_NewPed_Quantidade;
    public FloatingActionButton fab_NewPed_AddProd;
    public Button btn_NewPed_FinalizarPedido;
    public Integer quantidade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_novo_pedido);
        loadWidgets();
        eventWidgets();
    }

    private void loadWidgets() {
        //srcview_NewPed_Busca = findViewById(R.id.srcview_NewPed_Busca);
        spn_NewPed_Produto = findViewById(R.id.spn_NewPed_Produto);
        spn_NewPed_Cliente = findViewById(R.id.spn_NewPed_Cliente);
        edt_NewPed_Quantidade = findViewById(R.id.edt_NewPed_Quantidade);
        fab_NewPed_AddProd = findViewById(R.id.fab_NewPed_AddProd);
        btn_NewPed_FinalizarPedido = findViewById(R.id.btn_NewPed_FinalizarPedido);
    }

    private void eventWidgets() {
        btn_NewPed_FinalizarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
