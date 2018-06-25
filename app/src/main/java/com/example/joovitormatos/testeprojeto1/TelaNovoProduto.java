package com.example.joovitormatos.testeprojeto1;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.example.joovitormatos.testeprojeto1.modelo.Produto;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

//public class TelaNovoProduto extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
public class TelaNovoProduto extends AppCompatActivity {
    public EditText edt_NewPro_NomeProduto;
    public CheckBox chb_NewPro_Caixa;
    public CheckBox chb_NewPro_Unidade;
    public Button btn_NewPro_AdicionarProduto;
    public String chbTipoProd, nome_produto;
    public FloatingActionButton fab_NewProd_Voltar;

    private static FirebaseDatabase firebaseDatabase_NewPro;
    private static DatabaseReference databaseReference_NewPro;

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
        //fab_NewProd_Voltar = findViewById(R.id.fab_NewPed_AddProdVoltar);
    }


    @Override
    public void onBackPressed(){ //Botão BACK padrão do android
        startActivity(new Intent(getApplicationContext(), MainNavBar.class)); //O efeito ao ser pressionado do botão (no caso abre a activity)
        finishAffinity(); //Método para matar a activity e não deixa-lá indexada na pilhagem
        return;
    }

    private void eventWidgets() {
        //Botao voltar
        /*fab_NewProd_Voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainNavBar.class)); //O efeito ao ser pressionado do botão (no caso abre a activity)
                finishAffinity(); //Método para matar a activity e não deixa-lá indexada na pilhagem
                //return;
            }
        });*/

        //ADICIONAR NOVO PRODUTO
        btn_NewPro_AdicionarProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBox();
                nome_produto = String.valueOf(edt_NewPro_NomeProduto.getText().toString());
                if (edt_NewPro_NomeProduto.getText().toString().trim().isEmpty()) {
                    Toast.makeText(TelaNovoProduto.this, "Nome de produto vazio", Toast.LENGTH_SHORT).show();
                } else {
                    if ((chbTipoProd.equals("Unidade") || chbTipoProd.equals("Caixa")) && (chb_NewPro_Caixa.isChecked() || chb_NewPro_Unidade.isChecked())) {
                        Produto prod = new Produto();
                        prod.setId_Produto(UUID.randomUUID().toString());
                        prod.setNome_Produto(nome_produto);
                        prod.setTipo_Produto(chbTipoProd);
                        databaseReference_NewPro.child("Produto").child(prod.getId_Produto()).setValue(prod);
                        Toast.makeText(TelaNovoProduto.this, "Produto adicionado com sucesso", Toast.LENGTH_SHORT).show();
                        clear();
                    } else {
                        Toast.makeText(TelaNovoProduto.this, "Selecione Apenas um tipo de Produto", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        //VERIFICAÇÃO DAS CHECKBOXs
        chb_NewPro_Caixa.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (chb_NewPro_Caixa.isChecked()){
                    chbTipoProd = "Caixa";
                    chb_NewPro_Unidade.setChecked(false);
                } else {
                    chb_NewPro_Caixa.setChecked(false);
                }
            }
        });
        chb_NewPro_Unidade.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (chb_NewPro_Unidade.isChecked()){
                    chbTipoProd = "Unidade";
                    chb_NewPro_Caixa.setChecked(false);
                } else{
                    chb_NewPro_Unidade.setChecked(false);
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
        if (chb_NewPro_Caixa.isChecked()){
            chbTipoProd = "Caixa";
            chb_NewPro_Unidade.setChecked(false);
        }
        else if (chb_NewPro_Unidade.isChecked()){
            chbTipoProd = "Unidade";
            chb_NewPro_Caixa.setChecked(false);
        }
        else {
            Toast.makeText(TelaNovoProduto.this, "Selecione o tipo de Produto", Toast.LENGTH_LONG).show();
        }
    }

    private void loadFirebase() {
        FirebaseApp.initializeApp(this.getApplicationContext());
        firebaseDatabase_NewPro = FirebaseDatabase.getInstance();
        //firebaseDatabase_NewPro.setPersistenceEnabled(true);
        databaseReference_NewPro = firebaseDatabase_NewPro.getReference();
    }

    protected void onStart() {
        super.onStart();
        loadFirebase();
    }
}
