package com.example.joovitormatos.testeprojeto1.modelo;

public class Cliente {
    private String id_Cliente;
    private String nome_Cliente;

    public Cliente() {

    }

    public String getId_Cliente() {
        return id_Cliente;
    }

    public void setId_Cliente(String id_Cliente) {
        this.id_Cliente = id_Cliente;
    }

    public String getNome_Cliente() {
        return nome_Cliente;
    }

    public void setNome_Cliente(String nome_Cliente) {
        this.nome_Cliente = nome_Cliente;
    }

    @Override
    public String toString() {
        return id_Cliente + nome_Cliente;
    }
}
