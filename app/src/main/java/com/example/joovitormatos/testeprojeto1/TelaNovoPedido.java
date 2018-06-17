package com.example.joovitormatos.testeprojeto1;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;

import com.example.joovitormatos.testeprojeto1.modelo.Cliente;
import com.example.joovitormatos.testeprojeto1.modelo.Produto;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TelaNovoPedido extends AppCompatActivity {

    //public ScrollView srcview_NewPed_Busca;
    public Spinner spn_NewPed_Produto;
    public Spinner spn_NewPed_Cliente;
    public EditText edt_NewPed_Quantidade;
    public FloatingActionButton fab_NewPed_AddProd;
    public Button btn_NewPed_FinalizarPedido;
    public Integer quantidade;
    private static FirebaseDatabase firebaseDatabase_NewPed;
    public DatabaseReference databaseReference_NewPed;
    private List<Produto> produtoList = new ArrayList<>();
    private List<Cliente> clienteList = new ArrayList<>();
    private ArrayAdapter<Produto> produtoArrayAdapter;
    private ArrayAdapter<Cliente> clienteArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_novo_pedido);
        loadWidgets();
        loadFirebase();
        eventDatabase();
        eventWidgets();
    }

    private void eventDatabase() {
        //Select PRODUTOS
        databaseReference_NewPed.child("Produto").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                produtoList.clear();
                for (DataSnapshot objSnapshot:dataSnapshot.getChildren()){
                    Produto produto = objSnapshot.getValue(Produto.class);
                    produtoList.add(produto);
                }
                produtoArrayAdapter = new ArrayAdapter<Produto>(TelaNovoPedido.this, android.R.layout.simple_spinner_dropdown_item, produtoList);
                spn_NewPed_Produto.setAdapter(produtoArrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //Select CLIENTES
        databaseReference_NewPed.child("Cliente").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                clienteList.clear();
                for (DataSnapshot objSnapshot:dataSnapshot.getChildren()){
                    Cliente cliente = objSnapshot.getValue(Cliente.class);
                    clienteList.add(cliente);
                }
                clienteArrayAdapter = new ArrayAdapter<Cliente>(TelaNovoPedido.this, android.R.layout.simple_spinner_dropdown_item, clienteList);
                spn_NewPed_Cliente.setAdapter(clienteArrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void loadFirebase() {
        if (firebaseDatabase_NewPed == null) {
            FirebaseApp.initializeApp(TelaNovoPedido.this);
            firebaseDatabase_NewPed.setPersistenceEnabled(true);
            firebaseDatabase_NewPed = FirebaseDatabase.getInstance();
            databaseReference_NewPed = firebaseDatabase_NewPed.getReference();
        }
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
        fab_NewPed_AddProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantidade = Integer.valueOf(edt_NewPed_Quantidade.getText().toString());

            }
        });

        btn_NewPed_FinalizarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
