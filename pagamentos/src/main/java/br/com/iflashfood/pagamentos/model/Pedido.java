package br.com.iflashfood.pagamentos.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Pedido {

    private List<ItensDoPedido> itens;
}
