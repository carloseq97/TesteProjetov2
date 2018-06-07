package com.example.joovitormatos.testeprojeto1.modelo;

public class Pedido {
    private String id_Pedido_final;
    private Integer id_Pedido;
    private String nome_Produto;
    private Integer quant_Produto;
    private String nome_Cliente;

    public Pedido() {
    }

    public String getId_Pedido_final() {
        return id_Pedido_final;
    }

    public void setId_Pedido_final(String id_Pedido_final) {
        this.id_Pedido_final = id_Pedido_final;
    }

    public Integer getId_Pedido() {
        return id_Pedido;
    }

    public void setId_Pedido(Integer id_Pedido) {
        this.id_Pedido = id_Pedido;
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

    public String getNome_Cliente() {
        return nome_Cliente;
    }

    public void setNome_Cliente(String nome_Cliente) {
        this.nome_Cliente = nome_Cliente;
    }
}
