package com.example.joovitormatos.testeprojeto1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class TelaEditarProduto extends AppCompatActivity {

    public EditText edt_EditPro_NomeProduto;
    public CheckBox chb_EditPro_Unidade;
    public CheckBox chb_EditPro_Caixa;
    public Button btn_EditPro_Editar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_editar_produto);
        loadWidgets();
        eventWidgets();
    }

    private void loadWidgets() {
        edt_EditPro_NomeProduto = findViewById(R.id.edt_EditPro_NomeProduto);
        chb_EditPro_Unidade = findViewById(R.id.chb_EditPro_Unidade);
        chb_EditPro_Caixa = findViewById(R.id.chb_EditPro_Caixa);
        btn_EditPro_Editar = findViewById(R.id.btn_EditPro_Editar);
    }

    private void eventWidgets() {
        btn_EditPro_Editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
