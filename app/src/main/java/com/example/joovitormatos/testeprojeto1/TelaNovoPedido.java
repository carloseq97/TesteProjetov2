package com.example.joovitormatos.testeprojeto1;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.joovitormatos.testeprojeto1.modelo.Cliente;
import com.example.joovitormatos.testeprojeto1.modelo.ItemPedido;
import com.example.joovitormatos.testeprojeto1.modelo.Pedido;
import com.example.joovitormatos.testeprojeto1.modelo.Produto;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class TelaNovoPedido extends AppCompatActivity {

    //public ScrollView srcview_NewPed_Busca;
    public Spinner spn_NewPed_Produto;
    public Spinner spn_NewPed_Cliente;
    public EditText edt_NewPed_Quantidade;
    public FloatingActionButton fab_NewPed_AddProd;
    public Button btn_NewPed_FinalizarPedido;
    //public Integer quantidade;
    public String idPedido, dataPedido;
    public Date dataAtualPedido = new Date();


    private static FirebaseDatabase firebaseDatabase_NewPed;
    private static DatabaseReference databaseReference_NewPed;

    private List<Produto> produtoList = new ArrayList<>();
    private List<Cliente> clienteList = new ArrayList<>();
    private ArrayAdapter<Produto> produtoArrayAdapter;
    private ArrayAdapter<Cliente> clienteArrayAdapter;

    ItemPedido novoItem = new ItemPedido();
    Pedido pedido = new Pedido();

    Produto produtoSelecionado;
    Cliente clienteSelecionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_novo_pedido);
        loadWidgets();
        eventWidgets();
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadFirebase();
        eventDatabase();
    }

    private void eventDatabase() {
        //Select PRODUTOS
        databaseReference_NewPed.child("Produto").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                produtoList.clear();
                for (DataSnapshot objSnapshot:dataSnapshot.getChildren()){
                    Produto produto = objSnapshot.getValue(Produto.class);
                    produtoList.add(produto);
                }
                produtoArrayAdapter = new ArrayAdapter<Produto>(TelaNovoPedido.this, android.R.layout.simple_spinner_dropdown_item, produtoList);
                spn_NewPed_Produto.setAdapter(produtoArrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(TelaNovoPedido.this, "Error database select produto", Toast.LENGTH_SHORT).show();
            }
        });
        //Select CLIENTES
        databaseReference_NewPed.child("Cliente").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                clienteList.clear();
                for (DataSnapshot objSnapshot:dataSnapshot.getChildren()){
                    Cliente cliente = objSnapshot.getValue(Cliente.class);
                    clienteList.add(cliente);
                }
                clienteArrayAdapter = new ArrayAdapter<Cliente>(TelaNovoPedido.this, android.R.layout.simple_spinner_dropdown_item, clienteList);
                spn_NewPed_Cliente.setAdapter(clienteArrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(TelaNovoPedido.this, "Error database select cliente", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadFirebase() {
        FirebaseApp.initializeApp(this.getApplicationContext());
        firebaseDatabase_NewPed= FirebaseDatabase.getInstance();
        //firebaseDatabase_NewPed.setPersistenceEnabled(true);
        databaseReference_NewPed=firebaseDatabase_NewPed.getReference();
    }

    private void loadWidgets() {
        //srcview_NewPed_Busca = findViewById(R.id.srcview_NewPed_Busca);
        spn_NewPed_Produto = findViewById(R.id.spn_NewPed_Produto);
        spn_NewPed_Cliente = findViewById(R.id.spn_NewPed_Cliente);
        edt_NewPed_Quantidade = findViewById(R.id.edt_NewPed_Quantidade);
        fab_NewPed_AddProd = findViewById(R.id.fab_NewPed_AddProd);
        btn_NewPed_FinalizarPedido = findViewById(R.id.btn_EditPed_AtualizarPedido);
    }

    @Override
    public void onBackPressed(){ //Botão BACK padrão do android
        startActivity(new Intent(getApplicationContext(), MainNavBar.class)); //O efeito ao ser pressionado do botão (no caso abre a activity)
        finishAffinity(); //Método para matar a activity e não deixa-lá indexada na pilhagem
        return;
    }

    private void eventWidgets() {
        //PREENCHIMENTO DOS SPINNER NA CLASSE
        spn_NewPed_Cliente.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                clienteSelecionado = (Cliente) parent.getItemAtPosition(position);
                novoItem.setNome_Cliente(clienteSelecionado.getNome_Cliente());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                novoItem.setNome_Cliente(null);
            }
        });
        spn_NewPed_Produto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                produtoSelecionado = (Produto)parent.getItemAtPosition(position);
                novoItem.setNome_Produto(produtoSelecionado.getNome_Produto());
                novoItem.setTipo_Produto(produtoSelecionado.getTipo_Produto());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                novoItem.setNome_Produto(null);
            }
        });

        //ADICIONAR NOVO ITEM AO PEDIDO
        fab_NewPed_AddProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pedido.getId_Pedido() == null){
                    idPedido=UUID.randomUUID().toString();
                    novoItem.setId_Pedido(idPedido);
                    pedido.setId_Pedido(idPedido);
                    dataPedido = DateFormat.getInstance().format(dataAtualPedido);
                    pedido.setData_Pedido(dataPedido);
                    pedido.setPedido_Finalizado(false);
                    databaseReference_NewPed.child("Pedido").child(pedido.getId_Pedido()).setValue(pedido);
                }
                if (novoItem.getNome_Cliente().isEmpty()){
                    Toast.makeText(TelaNovoPedido.this, "Selecione um cliente", Toast.LENGTH_SHORT).show();
                }else if(novoItem.getNome_Produto().isEmpty() && novoItem.getTipo_Produto().isEmpty()){
                    Toast.makeText(TelaNovoPedido.this, "Selecione um produto", Toast.LENGTH_SHORT).show();
                }else if(edt_NewPed_Quantidade.getText().toString().isEmpty()){
                    Toast.makeText(TelaNovoPedido.this, "Digite a quantidade do produto", Toast.LENGTH_SHORT).show();
                }else {
                    novoItem.setId_Pedido(idPedido);
                    novoItem.setId_Item_Pedido(UUID.randomUUID().toString());
                    novoItem.setQuant_Produto(Integer.valueOf(edt_NewPed_Quantidade.getText().toString()));
                    databaseReference_NewPed.child("ItemPedido").child(novoItem.getId_Item_Pedido()).setValue(novoItem);
                    Toast.makeText(TelaNovoPedido.this, "Item Adicionado ao pedido", Toast.LENGTH_SHORT).show();
                    clear();
                }
            }
        });

        btn_NewPed_FinalizarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //boolean aux = pedido.getPedido_Finalizado();
                if (pedido.getPedido_Finalizado()!=null && pedido.getPedido_Finalizado().equals(false)){
                    Pedido update_pedido = new Pedido();
                    update_pedido.setId_Pedido(pedido.getId_Pedido());
                    update_pedido.setData_Pedido(pedido.getData_Pedido());
                    update_pedido.setPedido_Finalizado(true);
                    databaseReference_NewPed.child("Pedido").child(update_pedido.getId_Pedido()).setValue(update_pedido);
                    Toast.makeText(TelaNovoPedido.this, "Pedido finalizado com sucesso", Toast.LENGTH_SHORT).show();
                    nextPedido();
                }else{
                    Toast.makeText(TelaNovoPedido.this, "Erro ao finalizar pedido", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void nextPedido(){
        pedido.setId_Pedido(null);
        novoItem.setId_Pedido(null);
        edt_NewPed_Quantidade.setText("");
    }

    public void clear(){
        edt_NewPed_Quantidade.setText("");
    }
}
