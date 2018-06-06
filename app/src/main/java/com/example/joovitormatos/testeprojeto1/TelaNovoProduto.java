package com.example.joovitormatos.testeprojeto1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class TelaNovoProduto extends AppCompatActivity {

    public EditText edt_NewPro_NomeProduto;
    public CheckBox chb_NewPro_Caixa;
    public CheckBox chb_NewPro_Unidade;
    public Button btn_NewPro_AdicionarProduto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_novo_produto);
        loadWidgets();
        eventWidgets();
    }

    private void loadWidgets() {
        edt_NewPro_NomeProduto = findViewById(R.id.edt_NewPro_NomeProduto);
        chb_NewPro_Caixa = findViewById(R.id.chb_NewPro_Caixa);
        chb_NewPro_Unidade = findViewById(R.id.chb_NewPro_Unidade);
        btn_NewPro_AdicionarProduto = findViewById(R.id.btn_NewPro_AdicionarProduto);
    }

    private void eventWidgets() {
        btn_NewPro_AdicionarProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }
}
