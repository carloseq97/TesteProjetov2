package com.example.joovitormatos.testeprojeto1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.joovitormatos.testeprojeto1.modelo.Cliente;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;
import java.util.UUID;

public class TelaNovoCliente extends AppCompatActivity {

    public EditText edt_NewCli_NomeCliente;
    public Button btn_NewCli_Adicionar;

    public String nome_cliente;

    private static FirebaseDatabase firebaseDatabase_NewCli;
    private static DatabaseReference databaseReference_NewCli;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_novo_cliente);
        loadWidgets();
        eventWidgets();
    }

    @Override
    public void onBackPressed(){ //Botão BACK padrão do android
        startActivity(new Intent(getApplicationContext(), MainNavBar.class)); //O efeito ao ser pressionado do botão (no caso abre a activity)
        finishAffinity(); //Método para matar a activity e não deixa-lá indexada na pilhagem
        return;
    }

    private void loadWidgets() {
        edt_NewCli_NomeCliente = findViewById(R.id.edt_NewCli_NomeCliente);
        btn_NewCli_Adicionar = findViewById(R.id.btn_NewCli_Adicionar);
    }

    private void eventWidgets() {
        btn_NewCli_Adicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nome_cliente = String.valueOf(edt_NewCli_NomeCliente.getText().toString());
                if(edt_NewCli_NomeCliente.getText().toString().trim().isEmpty()){
                    Toast.makeText(TelaNovoCliente.this, "CAMPO NOME DO CLIENTE VAZIO", Toast.LENGTH_LONG).show();
                }else{
                    Cliente cliente = new Cliente();
                    cliente.setId_Cliente(UUID.randomUUID().toString());
                    cliente.setNome_Cliente(nome_cliente);
                    databaseReference_NewCli.child("Cliente").child(cliente.getId_Cliente()).setValue(cliente);
                    Toast.makeText(TelaNovoCliente.this, "Cliente adicionado com sucesso", Toast.LENGTH_SHORT).show();
                    clear();
                }
            }
        });
    }

    private void clear(){
        edt_NewCli_NomeCliente.setText("");
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadFirebase();
    }

    private void loadFirebase() {
        FirebaseApp.initializeApp(this.getApplicationContext());
        firebaseDatabase_NewCli = FirebaseDatabase.getInstance();
        //firebaseDatabase_NewCli.setPersistenceEnabled(true);
        databaseReference_NewCli = firebaseDatabase_NewCli.getReference();
    }
}
