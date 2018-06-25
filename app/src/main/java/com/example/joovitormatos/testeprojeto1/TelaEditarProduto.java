package com.example.joovitormatos.testeprojeto1;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

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
    public Button btn_EditPro_Editar, btn_EditPro_Deletar;
    public Spinner spn_EditPro_Produto;

    public String tipo_produto, new_nome_produto;

    private static FirebaseDatabase firebaseDatabase_EditPro;
    private static DatabaseReference databaseReference_EditPro;

    private List<Produto> produtoList = new ArrayList<Produto>();
    private ArrayAdapter<Produto> produtoArrayAdapter;

    Produto produtoSelecionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_editar_produto);
        loadWidgets();
        eventWidgets();
    }

    @Override
    public void onBackPressed(){ //Botão BACK padrão do android
        startActivity(new Intent(getApplicationContext(), MainNavBar.class)); //O efeito ao ser pressionado do botão (no caso abre a activity)
        finishAffinity(); //Método para matar a activity e não deixa-lá indexada na pilhagem
        return;
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadFirebase();
        eventDatabase();
    }

    private void eventDatabase() {
        databaseReference_EditPro.child("Produto").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //firebaseDatabase_EditPro.goOnline();
                produtoList.clear();
                for (DataSnapshot objSnapshot:dataSnapshot.getChildren()){
                    Produto produto = objSnapshot.getValue(Produto.class);
                    produtoList.add(produto);
                }
                produtoArrayAdapter = new ArrayAdapter<Produto>(TelaEditarProduto.this, android.R.layout.simple_spinner_dropdown_item, produtoList);
                spn_EditPro_Produto.setAdapter(produtoArrayAdapter);
                //firebaseDatabase_EditPro.goOffline();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void loadFirebase() {
        FirebaseApp.initializeApp(this.getApplicationContext());
        firebaseDatabase_EditPro = FirebaseDatabase.getInstance();
        //firebaseDatabase_EditPro.setPersistenceEnabled(true);
        databaseReference_EditPro=firebaseDatabase_EditPro.getReference();
    }

    private void loadWidgets() {
        edt_EditPro_NomeProduto = findViewById(R.id.edt_EditPro_NomeProduto);
        chb_EditPro_Unidade = findViewById(R.id.chb_EditPro_Unidade);
        chb_EditPro_Caixa = findViewById(R.id.chb_EditPro_Caixa);
        btn_EditPro_Editar = findViewById(R.id.btn_EditPro_Editar);
        btn_EditPro_Deletar = findViewById(R.id.btn_EditPro_Deletar);
        spn_EditPro_Produto = findViewById(R.id.spn_EditPro_Produto);
    }

    private void eventWidgets() {
        //SELECT PRODUTOS
        spn_EditPro_Produto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //checkBox();
                //firebaseDatabase_EditPro.goOnline();

                produtoSelecionado = (Produto)parent.getItemAtPosition(position);
                edt_EditPro_NomeProduto.setText(produtoSelecionado.getNome_Produto());
                tipo_produto = produtoSelecionado.getTipo_Produto();

                if (produtoSelecionado.getTipo_Produto().equals("Caixa")){
                    chb_EditPro_Caixa.setChecked(true);
                    chb_EditPro_Unidade.setChecked(false);
                }else if (produtoSelecionado.getTipo_Produto().equals("Unidade")){
                    chb_EditPro_Unidade.setChecked(true);
                    chb_EditPro_Caixa.setChecked(false);
                } else {
                    Toast.makeText(TelaEditarProduto.this, "Erro produto sem tipo", Toast.LENGTH_SHORT).show();
                }
                //firebaseDatabase_EditPro.goOffline();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //VERIFICAÇÃO DAS CHECKBOXs
        chb_EditPro_Caixa.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (chb_EditPro_Caixa.isChecked()){
                    tipo_produto = "Caixa";
                    chb_EditPro_Unidade.setChecked(false);
                } else {
                    chb_EditPro_Caixa.setChecked(false);
                }
            }
        });
        chb_EditPro_Unidade.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (chb_EditPro_Unidade.isChecked()){
                    tipo_produto = "Unidade";
                    chb_EditPro_Caixa.setChecked(false);
                } else {
                    chb_EditPro_Unidade.setChecked(false);
                }
            }
        });

        //BOTÃO EDITAR
        btn_EditPro_Editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new_nome_produto = String.valueOf(edt_EditPro_NomeProduto.getText().toString());
                if(edt_EditPro_NomeProduto.getText().toString().trim().isEmpty()) {
                    Toast.makeText(TelaEditarProduto.this, "Campo nome do produto vazio", Toast.LENGTH_SHORT).show();
                }else {
                    if (tipo_produto.equals("Unidade") || tipo_produto.equals("Caixa")) {
                        //firebaseDatabase_EditPro.goOnline();
                        Produto new_prod_update = new Produto();
                        new_prod_update.setId_Produto(produtoSelecionado.getId_Produto().toString());
                        new_prod_update.setNome_Produto(new_nome_produto.toString().trim());
                        new_prod_update.setTipo_Produto(tipo_produto.toString().trim());
                        databaseReference_EditPro.child("Produto").child(new_prod_update.getId_Produto()).setValue(new_prod_update);
                        Toast.makeText(TelaEditarProduto.this, "Produto alterado com sucesso", Toast.LENGTH_SHORT).show();
                        clear();
                        //firebaseDatabase_EditPro.goOffline();
                    } else {
                        Toast.makeText(TelaEditarProduto.this, "Erro ao alterar produto", Toast.LENGTH_SHORT).show();
                        Toast.makeText(TelaEditarProduto.this, "Selecione apenas um tipo de produto", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        //BOTÃO DELETAR
        btn_EditPro_Deletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Produto produtoDelete = new Produto();
                produtoDelete.setId_Produto(produtoSelecionado.getId_Produto());
                databaseReference_EditPro.child("Produto").child(produtoDelete.getId_Produto()).removeValue();
                clear();
            }
        });
    }

    private void clear() {
        edt_EditPro_NomeProduto.setText("");
        chb_EditPro_Caixa.setChecked(false);
        chb_EditPro_Unidade.setChecked(false);
    }

    private void checkBox() {
        if (chb_EditPro_Caixa.isChecked()){
            tipo_produto = "Caixa";
            chb_EditPro_Unidade.setChecked(false);
        }
        else if (chb_EditPro_Unidade.isChecked()){
            tipo_produto = "Unidade";
            chb_EditPro_Caixa.setChecked(false);
        }
        else {
            Toast.makeText(TelaEditarProduto.this, "Selecione o tipo de Produto", Toast.LENGTH_LONG).show();
        }
    }
}
