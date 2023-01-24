package br.com.iflashfood.pedidos.dto;


import br.com.iflashfood.pedidos.model.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PedidoDto {

    private Long id;

    private LocalDateTime dataHora;

    private Status status;
    private List<ItensPedidoDto> itens = new ArrayList<>();

    private PedidoDto(){};

    public PedidoDto(List<ItensPedidoDto> itens) {
        this.dataHora = LocalDateTime.now();
        this.status = Status.REALIZADO;
        this.itens = itens;
    }
}
