package com.example.joovitormatos.testeprojeto1.modelo;

public class Produto {

    private String id_Produto;
    private String nome_Produto;
    private String tipo_Produto;

    public Produto() {
    }

    public String getId_Produto() {
        return id_Produto;
    }

    public void setId_Produto(String id_Produto) {
        this.id_Produto = id_Produto;
    }

    public String getNome_Produto() {
        return nome_Produto;
    }

    public void setNome_Produto(String nome_Produto) {
        this.nome_Produto = nome_Produto;
    }

    public String getTipo_Produto() {
        return tipo_Produto;
    }

    public void setTipo_Produto(String tipo_Produto) {
        this.tipo_Produto = tipo_Produto;
    }

    @Override
    public String toString() {
        return nome_Produto + " - " + tipo_Produto;
    }
}
