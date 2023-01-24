package br.com.iflashfood.pedidos.service;

import br.com.iflashfood.pedidos.dto.ItensPedidoDto;
import br.com.iflashfood.pedidos.dto.PedidoDto;
import br.com.iflashfood.pedidos.dto.StatusDto;
import br.com.iflashfood.pedidos.model.Pedido;
import br.com.iflashfood.pedidos.model.Status;
import br.com.iflashfood.pedidos.repository.PedidoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    public List<PedidoDto> obterTodos(){
        return repository.findAll().stream()
                .map(p -> modelMapper.map(p, PedidoDto.class))
                .collect(Collectors.toList());
    }

    public PedidoDto obterPorId(Long id){
        Pedido pedido = repository.findById(id).orElseThrow(() -> new EntityNotFoundException());
        return modelMapper.map(pedido, PedidoDto.class);
    }

    public PedidoDto criarPedido(PedidoDto dto){
        Pedido pedido = modelMapper.map(dto, Pedido.class);
        pedido.getItens().forEach(p -> p.setPedido(pedido));
        pedido.setDataHora(LocalDateTime.now());
        pedido.setStatus(Status.REALIZADO);

        Pedido pedidoSalvo = repository.save(pedido);
        dto.setId(pedidoSalvo.getId());

        return modelMapper.map(pedidoSalvo, PedidoDto.class);
    }

    public PedidoDto atualizarStatus(Long id, StatusDto dto){
       Pedido pedido = repository.porIdComItens(id);

       if(pedido == null){
           throw new EntityNotFoundException();
       }

       pedido.setStatus(dto.getStatus());
       repository.atualizaStatus(dto.getStatus(), pedido);

       return modelMapper.map(pedido, PedidoDto.class);
    }

    public void aprovaPagamentoPedido(Long id){
        Pedido pedido = repository.porIdComItens(id);

        if(pedido == null){
            throw new EntityNotFoundException();
        }

        pedido.setStatus(Status.PAGO);
        repository.atualizaStatus(Status.PAGO, pedido);
    }
}
