package br.com.iflashfood.pedidos.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItensPedidoDto {

    private Long id;
    private Integer quantidade;
    private String descricao;
}
