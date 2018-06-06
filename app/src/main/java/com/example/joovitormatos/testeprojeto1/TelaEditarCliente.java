package com.example.joovitormatos.testeprojeto1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class TelaEditarCliente extends AppCompatActivity {

    public EditText edt_EditCli_NomeCliente;
    public Button btn_EditCli_Editar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_editar_cliente);
        loadWidgets();
        eventWidgets();
    }

    private void loadWidgets() {
        edt_EditCli_NomeCliente = findViewById(R.id.edt_EditCli_NomeCliente);
        btn_EditCli_Editar = findViewById(R.id.btn_EditCli_Editar);
    }

    private void eventWidgets() {
        btn_EditCli_Editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
