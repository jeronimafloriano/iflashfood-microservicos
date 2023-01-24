package br.com.iflashfood.pedidos.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pedidos")
@Getter
@Setter
@NoArgsConstructor
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dataHora;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "pedido")
    private List<ItensPedido> itens = new ArrayList<>();

    public Pedido(Long id, Status status, List<ItensPedido> itens) {
        this.id = id;
        this.dataHora = LocalDateTime.now();
        this.status = Status.REALIZADO;
        this.itens = itens;
    }
}
