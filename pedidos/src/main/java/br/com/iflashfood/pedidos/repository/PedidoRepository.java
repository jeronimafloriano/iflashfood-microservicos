package br.com.iflashfood.pedidos.repository;

import br.com.iflashfood.pedidos.model.Pedido;
import br.com.iflashfood.pedidos.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(" UPDATE Pedido p SET p.status = :status WHERE p = :pedido ")
    void atualizaStatus(Status status, Pedido pedido);

    @Query(" SELECT p FROM Pedido p LEFT JOIN FETCH p.itens where p.id = :id ")
    Pedido porIdComItens(Long id);


}
