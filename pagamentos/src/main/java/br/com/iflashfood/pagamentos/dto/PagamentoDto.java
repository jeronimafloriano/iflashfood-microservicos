package br.com.iflashfood.pagamentos.dto;

import br.com.iflashfood.pagamentos.model.ItensDoPedido;
import br.com.iflashfood.pagamentos.model.Status;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class PagamentoDto {

    private Long id;
    private BigDecimal valor;
    private String nome;
    private String numero;
    private String expiracao;
    private String codigo;
    private Status status;
    private Long pedidoId;
    private Long formaDePagamentoId;
    private List<ItensDoPedido> itens;
}
