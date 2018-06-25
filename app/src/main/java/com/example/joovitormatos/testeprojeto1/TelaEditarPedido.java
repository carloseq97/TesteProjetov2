package com.example.joovitormatos.testeprojeto1;

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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class TelaEditarPedido extends AppCompatActivity {

    public Spinner spn_EditPed_Itens, spn_EditPed_Pedido, spn_EditPed_NewCliente, spn_EditPed_NewProduto;
    public EditText edt_EditPed_Quantidade;
    public FloatingActionButton fab_EditPed_Remove, fab_EditPed_Add;
    public Button btn_EditPed_AtualizarPedido;
    public String idPedido, dataPedido;
    public Date dataAtualPedido = new Date();

    private static FirebaseDatabase firebaseDatabase_EditPed;
    private static DatabaseReference databaseReference_EditPed;

    private List<Pedido> pedidoList = new ArrayList<>();
    private List<ItemPedido> itemPedidoList = new ArrayList<>();
    private List<Produto> produtoList = new ArrayList<>();
    private List<Cliente> clienteList = new ArrayList<>();
    private ArrayAdapter<Pedido> pedidoArrayAdapter;
    private ArrayAdapter<ItemPedido> itemPedidoArrayAdapter;
    private ArrayAdapter<Produto> produtoArrayAdapter;
    private ArrayAdapter<Cliente> clienteArrayAdapter;

    ItemPedido novoItem = new ItemPedido();
    ItemPedido item = new ItemPedido();
    Pedido pedido = new Pedido();

    ItemPedido itemAux = new ItemPedido();

    Pedido pedidoSelecionado;
    ItemPedido itemPedidoSelecionado;
    Produto produtoSelecionado;
    Cliente clienteSelecionado;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_editar_pedido);
        loadwidgets();
        eventWidgets();
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadFirebase();
        eventDatabase();
    }

    private void loadFirebase() {
        FirebaseApp.initializeApp((this.getApplicationContext()));
        firebaseDatabase_EditPed = FirebaseDatabase.getInstance();
        //firebaseDatabase_EditPed.setPersistenceEnabled(true);
        databaseReference_EditPed = firebaseDatabase_EditPed.getReference();
    }

    private void eventDatabase() {
        //Select PRODUTOS
        databaseReference_EditPed.child("Produto").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                produtoList.clear();
                for (DataSnapshot objSnapshot:dataSnapshot.getChildren()){
                    Produto produto = objSnapshot.getValue(Produto.class);
                    produtoList.add(produto);
                }
                produtoArrayAdapter = new ArrayAdapter<Produto>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, produtoList);
                spn_EditPed_NewProduto.setAdapter(produtoArrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Error database select produto", Toast.LENGTH_SHORT).show();
            }
        });
        //Select CLIENTES
        databaseReference_EditPed.child("Cliente").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                clienteList.clear();
                for (DataSnapshot objSnapshot:dataSnapshot.getChildren()){
                    Cliente cliente = objSnapshot.getValue(Cliente.class);
                    clienteList.add(cliente);
                }
                clienteArrayAdapter = new ArrayAdapter<Cliente>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, clienteList);
                spn_EditPed_NewCliente.setAdapter(clienteArrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Error database select cliente", Toast.LENGTH_SHORT).show();
            }
        });
        //Select PEDIDOS
        databaseReference_EditPed.child("Pedido").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                pedidoList.clear();
                for (DataSnapshot objSnapshot:dataSnapshot.getChildren()){
                    Pedido pedido = objSnapshot.getValue(Pedido.class);
                    pedidoList.add(pedido);
                }
                pedidoArrayAdapter= new ArrayAdapter<Pedido>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, pedidoList);
                spn_EditPed_Pedido.setAdapter(pedidoArrayAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Error database select pedido", Toast.LENGTH_SHORT).show();
            }
        });
        //Select ITENS PEDIDO em branco
        databaseReference_EditPed.child("ItemPedido").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                itemPedidoList.clear();
                for(DataSnapshot objSnapshot:dataSnapshot.getChildren()){
                    ItemPedido itemPedido = objSnapshot.getValue(ItemPedido.class);
                    itemPedidoList.add(itemPedido);
                }
                itemPedidoArrayAdapter = new ArrayAdapter<ItemPedido>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, itemPedidoList);
                spn_EditPed_Itens.setAdapter(itemPedidoArrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void buscaItem(){
        //Select ITENS PEDIDO
        Query query;
        query = databaseReference_EditPed.child("ItemPedido").orderByChild("id_Pedido").startAt(pedidoSelecionado.getId_Pedido()).endAt(pedidoSelecionado.getId_Pedido());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                itemPedidoList.clear();
                for (DataSnapshot objSnapshot:dataSnapshot.getChildren()){
                    ItemPedido itemPedido = objSnapshot.getValue(ItemPedido.class);
                    itemPedidoList.add(itemPedido);
                }
                itemPedidoArrayAdapter = new ArrayAdapter<ItemPedido>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, itemPedidoList);
                spn_EditPed_Itens.setAdapter(itemPedidoArrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Error database select item pedido", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadwidgets() {
        spn_EditPed_Itens = findViewById(R.id.spn_EditPed_Itens);
        spn_EditPed_Pedido = findViewById(R.id.spn_EditPed_Pedido);
        spn_EditPed_NewCliente = findViewById(R.id.spn_EditPed_NewCliente);
        spn_EditPed_NewProduto = findViewById(R.id.spn_EditPed_NewProduto);
        edt_EditPed_Quantidade = findViewById(R.id.edt_EditPed_Quantidade);
        fab_EditPed_Add = findViewById(R.id.fab_EditPed_Add);
        fab_EditPed_Remove = findViewById(R.id.fab_EditPed_Remove);
        btn_EditPed_AtualizarPedido = findViewById(R.id.btn_EditPed_AtualizarPedido);
    }

    private void eventWidgets() {
        //PREENCHIMENTO DOS SPINNER NA CLASSE
        //PEDIDO
        spn_EditPed_Pedido.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                pedidoSelecionado = (Pedido) parent.getItemAtPosition(position);
                pedido.setId_Pedido(pedidoSelecionado.getId_Pedido());
                novoItem.setId_Pedido(pedidoSelecionado.getId_Pedido());
                idPedido = pedidoSelecionado.getId_Pedido();
                buscaItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                pedido.setId_Pedido(null);
            }
        });
        //ITEM PEDIDO
        spn_EditPed_Itens.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                itemPedidoSelecionado= (ItemPedido) parent.getItemAtPosition(position);
                item.setId_Pedido(itemPedidoSelecionado.getId_Pedido());
                item.setId_Item_Pedido(itemPedidoSelecionado.getId_Item_Pedido());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                item.setId_Item_Pedido(null);
                item.setId_Pedido(null);
            }
        });
        //CLIENTE
        spn_EditPed_NewCliente.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                clienteSelecionado = (Cliente) parent.getItemAtPosition(position);
                novoItem.setNome_Cliente(clienteSelecionado.getNome_Cliente());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                novoItem.setNome_Cliente(null);
            }
        });//*/
        //PRODUTO
        spn_EditPed_NewProduto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        //REMOVER ITEM DO PEDIDO
        fab_EditPed_Remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!item.getId_Pedido().isEmpty() && !item.getId_Item_Pedido().isEmpty()){
                    ItemPedido delete_ItemPedido = new ItemPedido();
                    delete_ItemPedido.setId_Pedido(item.getId_Pedido());
                    delete_ItemPedido.setId_Item_Pedido(item.getId_Item_Pedido());
                    databaseReference_EditPed.child("ItemPedido").child(delete_ItemPedido.getId_Item_Pedido()).removeValue();
                    Toast.makeText(getApplicationContext(), "Item deletado do pedido", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "Erro ao deletar Item do pedido", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //ADICIONAR ITEM AO PEDIDO
        fab_EditPed_Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pedido.getId_Pedido()!=null && pedido.getId_Pedido().equals(novoItem.getId_Pedido())){
                    if(novoItem.getNome_Cliente().isEmpty()){
                        Toast.makeText(getApplicationContext(), "Selecione um cliente", Toast.LENGTH_SHORT).show();
                    }else if (novoItem.getNome_Produto().isEmpty()){
                        Toast.makeText(getApplicationContext(), "Selecione um produto", Toast.LENGTH_SHORT).show();
                    }else if (edt_EditPed_Quantidade.getText().toString().isEmpty()){
                        Toast.makeText(getApplicationContext(), "Digite a quantidade", Toast.LENGTH_SHORT).show();
                    }else {
                        novoItem.setQuant_Produto(Integer.valueOf(edt_EditPed_Quantidade.getText().toString().trim()));
                        novoItem.setId_Item_Pedido(UUID.randomUUID().toString());
                        databaseReference_EditPed.child("ItemPedido").child(novoItem.getId_Item_Pedido()).setValue(novoItem);
                        Toast.makeText(getApplicationContext(), "Item adicionado ao pedido", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Selecione um pedido", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //ATUALIZAR PEDIDO
        btn_EditPed_AtualizarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pedido.getId_Pedido() != null && pedido.getId_Pedido().equals(pedidoSelecionado.getId_Pedido())){
                    Pedido update_Pedido = new Pedido();
                    dataPedido = DateFormat.getInstance().format(dataAtualPedido);
                    update_Pedido.setData_PedidoModificado(dataPedido);
                    update_Pedido.setId_Pedido(pedidoSelecionado.getId_Pedido());
                    update_Pedido.setPedido_Finalizado(true);
                    databaseReference_EditPed.child("Pedido").child(update_Pedido.getId_Pedido()).setValue(update_Pedido);
                    Toast.makeText(getApplicationContext(), "Pedido atualizado com sucesso", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "Erro ao atualizar pedido", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
