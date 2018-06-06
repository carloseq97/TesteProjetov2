package com.example.joovitormatos.testeprojeto1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class TelaNovoCliente extends AppCompatActivity {

    public EditText edt_NewCli_NomeCliente;
    public Button btn_NewCli_Adicionar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_novo_cliente);
        loadWidgets();
        eventWidgets();
    }

    private void loadWidgets() {
        edt_NewCli_NomeCliente = findViewById(R.id.edt_NewCli_NomeCliente);
        btn_NewCli_Adicionar = findViewById(R.id.btn_NewCli_Adicionar);
    }

    private void eventWidgets() {
        btn_NewCli_Adicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
