package com.example.joovitormatos.testeprojeto1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.joovitormatos.testeprojeto1.modelo.Produto;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class TelaNovoProduto extends AppCompatActivity {

    public EditText edt_NewPro_NomeProduto;
    public CheckBox chb_NewPro_Caixa;
    public CheckBox chb_NewPro_Unidade;
    public Button btn_NewPro_AdicionarProduto;
    public String chbTipoProd;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

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
                checkBox();
                if (chbTipoProd == "Unidade" || chbTipoProd == "Caixa") {
                    loadFirebase();
                    Produto prod = new Produto();
                    prod.setId_Produto(UUID.randomUUID().toString());
                    prod.setNome_Produto(edt_NewPro_NomeProduto.toString());
                    prod.setTipo_Produto(chbTipoProd);
                    databaseReference.child("Produto").child(prod.getId_Produto()).setValue(prod);
                    clear();
                } else {
                    Toast.makeText(TelaNovoProduto.this, "Selecione Apenas um tipo de Produto", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void clear() {
        edt_NewPro_NomeProduto.setText("");
        chb_NewPro_Caixa.setChecked(false);
        chb_NewPro_Unidade.setChecked(false);

    }

    private void checkBox() {
        if (chb_NewPro_Caixa.isChecked()) chbTipoProd = "Unidade";
        if (chb_NewPro_Unidade.isChecked()) chbTipoProd = "Caixa";
        if (chb_NewPro_Unidade.isChecked() && chb_NewPro_Caixa.isChecked()) chbTipoProd = "3";
        else {
            Toast.makeText(TelaNovoProduto.this, "Selecione o tipo de Produto", Toast.LENGTH_LONG).show();
        }
    }

    private void loadFirebase() {
        FirebaseApp.initializeApp(TelaNovoProduto.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }


}
