package com.example.joovitormatos.testeprojeto1.modelo;

public class ItemPedido {
    private String id_Item_Pedido;
    private String id_Pedido;
    private String nome_Cliente;
    private String nome_Produto;
    private String tipo_Produto;
    private Integer quant_Produto;

    public ItemPedido(){

    }

    public String getTipo_Produto() {
        return tipo_Produto;
    }

    public void setTipo_Produto(String tipo_Produto) {
        this.tipo_Produto = tipo_Produto;
    }

    public String getId_Item_Pedido() {
        return id_Item_Pedido;
    }

    public void setId_Item_Pedido(String id_Item_Pedido) {
        this.id_Item_Pedido = id_Item_Pedido;
    }

    public String getId_Pedido() {
        return id_Pedido;
    }

    public void setId_Pedido(String id_Pedido) {
        this.id_Pedido = id_Pedido;
    }

    public String getNome_Cliente() {
        return nome_Cliente;
    }

    public void setNome_Cliente(String nome_Cliente) {
        this.nome_Cliente = nome_Cliente;
    }

    public String getNome_Produto() {
        return nome_Produto;
    }

    public void setNome_Produto(String nome_Produto) {
        this.nome_Produto = nome_Produto;
    }

    public Integer getQuant_Produto() {
        return quant_Produto;
    }

    public void setQuant_Produto(Integer quant_Produto) {
        this.quant_Produto = quant_Produto;
    }

    @Override
    public String toString() {
        return nome_Cliente + " - " +
                nome_Produto + " - " +
                tipo_Produto + " - " +
                quant_Produto;
    }
}
