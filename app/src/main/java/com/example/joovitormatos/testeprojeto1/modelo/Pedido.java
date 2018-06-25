package com.example.joovitormatos.testeprojeto1.modelo;

public class Pedido {
    private String id_Pedido;
    private String data_Pedido;
    private String data_PedidoModificado;
    private Boolean pedido_Finalizado;

    public Pedido() {
    }

    public String getId_Pedido() {
        return id_Pedido;
    }

    public void setId_Pedido(String id_Pedido) {
        this.id_Pedido = id_Pedido;
    }

    public String getData_Pedido() {
        return data_Pedido;
    }

    public void setData_Pedido(String data_Pedido) {
        this.data_Pedido = data_Pedido;
    }

    public String getData_PedidoModificado() {
        return data_PedidoModificado;
    }

    public void setData_PedidoModificado(String data_PedidoModificado) {
        this.data_PedidoModificado = data_PedidoModificado;
    }

    public Boolean getPedido_Finalizado() {
        return pedido_Finalizado;
    }

    public void setPedido_Finalizado(Boolean pedido_Finalizado) {
        this.pedido_Finalizado = pedido_Finalizado;
    }

    @Override
    public String toString() {
        if (pedido_Finalizado!= null && pedido_Finalizado.equals(false)) {
            return data_Pedido + " - " + data_PedidoModificado + " - Aberto - ";
        }else{
            return data_Pedido + " - " + data_PedidoModificado + " - Finalizado - ";
        }
    }
}
