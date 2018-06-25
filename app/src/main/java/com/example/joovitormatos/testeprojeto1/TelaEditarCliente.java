package com.example.joovitormatos.testeprojeto1;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

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
    public Button btn_EditCli_Editar, btn_EditCli_Delete;
    public Spinner spn_EditCli_Cliente;
    public String new_nome_cliente;

    private static FirebaseDatabase firebaseDatabase_EditCli;
    public static DatabaseReference databaseReference_EditCli;

    private List<Cliente> clienteList = new ArrayList<Cliente>();
    private ArrayAdapter<Cliente> clienteArrayAdapter;

    Cliente clienteSelecionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_editar_cliente);
        loadWidgets();
        eventWidgets();
    }
    //continue


    @Override
    protected void onStart() {
        super.onStart();
        loadFirebase();
        eventDatabase();
    }

    @Override
    public void onBackPressed(){ //Botão BACK padrão do android
        startActivity(new Intent(getApplicationContext(), MainNavBar.class)); //O efeito ao ser pressionado do botão (no caso abre a activity)
        finishAffinity(); //Método para matar a activity e não deixa-lá indexada na pilhagem
        return;
    }

    private void loadFirebase() {
        FirebaseApp.initializeApp(this.getApplicationContext());
        firebaseDatabase_EditCli= FirebaseDatabase.getInstance();
        //firebaseDatabase_EditCli.setPersistenceEnabled(true);
        databaseReference_EditCli=firebaseDatabase_EditCli.getReference();
    }

    private void eventDatabase() {
        //SELECT CLIENTE
        databaseReference_EditCli.child("Cliente").addValueEventListener(new ValueEventListener() {
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



    private void loadWidgets() {
        edt_EditCli_NomeCliente = findViewById(R.id.edt_EditCli_NomeCliente);
        btn_EditCli_Editar = findViewById(R.id.btn_EditCli_Editar);
        btn_EditCli_Delete = findViewById(R.id.btn_EditCli_Deletar);
        spn_EditCli_Cliente = findViewById(R.id.spn_EditCli_Cliente);
    }

    private void eventWidgets() {
        //BOTÃO EDITAR
        btn_EditCli_Editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Atualizar dados de clientes
                new_nome_cliente = String.valueOf(edt_EditCli_NomeCliente.getText().toString());
                if (edt_EditCli_NomeCliente.getText().toString().trim().isEmpty()){
                    Toast.makeText(TelaEditarCliente.this, "Erro ao alterar cliente", Toast.LENGTH_SHORT).show();
                    Toast.makeText(TelaEditarCliente.this, "Campo nome do cliente vazio", Toast.LENGTH_SHORT).show();
                }else{
                    Cliente new_cliente_update = new Cliente();
                    new_cliente_update.setId_Cliente(clienteSelecionado.getId_Cliente().toString());
                    new_cliente_update.setNome_Cliente(new_nome_cliente.toString().trim());
                    databaseReference_EditCli.child("Cliente").child(new_cliente_update.getId_Cliente()).setValue(new_cliente_update);
                    Toast.makeText(TelaEditarCliente.this, "Cliente alterado com sucesso", Toast.LENGTH_SHORT).show();
                    clear();
                }
            }
        });

        //BOTÃO DELETAR
        btn_EditCli_Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Deletar dados de clientes
                Cliente clienteDelete = new Cliente();
                clienteDelete.setId_Cliente(clienteSelecionado.getId_Cliente());
                databaseReference_EditCli.child("Cliente").child(clienteDelete.getId_Cliente()).removeValue();
                clear();
            }
        });

        //PREENCHIMENTO DO SPINNER
        spn_EditCli_Cliente.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                clienteSelecionado = (Cliente)parent.getItemAtPosition(position);
                edt_EditCli_NomeCliente.setText(clienteSelecionado.getNome_Cliente());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void clear() {
        edt_EditCli_NomeCliente.setText("");
    }
}
