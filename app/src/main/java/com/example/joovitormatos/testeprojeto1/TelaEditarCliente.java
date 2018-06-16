package com.example.joovitormatos.testeprojeto1;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.joovitormatos.testeprojeto1.modelo.Cliente;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TelaEditarCliente extends AppCompatActivity {

    public EditText edt_EditCli_NomeCliente;
    public Button btn_EditCli_Editar;
    public Spinner spn_EditCli_Cliente;

    public FirebaseDatabase firebaseDatabase;
    public DatabaseReference databaseReference;

    private List<Cliente> clienteList = new ArrayList<Cliente>();
    private ArrayAdapter<Cliente> clienteArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_editar_cliente);
        loadWidgets();
        eventWidgets();
        loadFirebase();
        eventDatabase();
    }
    //continue

    private void eventDatabase() {
        databaseReference.child("Cliente").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                clienteList.clear();
                for (DataSnapshot objSnapshot:dataSnapshot.getChildren()){
                    Cliente cliente = objSnapshot.getValue(Cliente.class);
                    clienteList.add(cliente);
                }
                clienteArrayAdapter = new ArrayAdapter<Cliente>(TelaEditarCliente.this, android.R.layout.simple_spinner_dropdown_item, clienteList);
                spn_EditCli_Cliente.setAdapter(clienteArrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void loadFirebase() {
        FirebaseApp.initializeApp(TelaEditarCliente.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        //firebaseDatabase.setPersistenceEnabled(true);
        databaseReference = firebaseDatabase.getReference();
    }

    private void loadWidgets() {
        edt_EditCli_NomeCliente = findViewById(R.id.edt_EditCli_NomeCliente);
        btn_EditCli_Editar = findViewById(R.id.btn_EditCli_Editar);
        spn_EditCli_Cliente = findViewById(R.id.spn_EditCli_Cliente);
    }

    private void eventWidgets() {
        btn_EditCli_Editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }
}
