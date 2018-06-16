package com.example.joovitormatos.testeprojeto1;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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

public class TelaEditarProduto extends AppCompatActivity {

    public EditText edt_EditPro_NomeProduto;
    public CheckBox chb_EditPro_Unidade;
    public CheckBox chb_EditPro_Caixa;
    public Button btn_EditPro_Editar;
    public Spinner spn_EditPro_Produto;

    public FirebaseDatabase firebaseDatabase;
    public DatabaseReference databaseReference;

    private List<Produto> produtoList = new ArrayList<Produto>();
    private ArrayAdapter<Produto> produtoArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_editar_produto);
        loadWidgets();
        eventWidgets();
        loadFirebase();
        eventDatabase();
    }



    private void eventDatabase() {
        databaseReference.child("Produto").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                produtoList.clear();
                for (DataSnapshot objSnapshot:dataSnapshot.getChildren()){
                    Produto produto = objSnapshot.getValue(Produto.class);
                    produtoList.add(produto);
                }
                produtoArrayAdapter = new ArrayAdapter<Produto>(TelaEditarProduto.this, android.R.layout.simple_spinner_dropdown_item, produtoList);
                spn_EditPro_Produto.setAdapter(produtoArrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void loadFirebase() {
        FirebaseApp.initializeApp(TelaEditarProduto.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        //firebaseDatabase.setPersistenceEnabled(true);
        databaseReference = firebaseDatabase.getReference();
    }

    private void loadWidgets() {
        edt_EditPro_NomeProduto = findViewById(R.id.edt_EditPro_NomeProduto);
        chb_EditPro_Unidade = findViewById(R.id.chb_EditPro_Unidade);
        chb_EditPro_Caixa = findViewById(R.id.chb_EditPro_Caixa);
        btn_EditPro_Editar = findViewById(R.id.btn_EditPro_Editar);
        spn_EditPro_Produto = findViewById(R.id.spn_EditPro_Produto);
    }

    private void eventWidgets() {
        btn_EditPro_Editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
