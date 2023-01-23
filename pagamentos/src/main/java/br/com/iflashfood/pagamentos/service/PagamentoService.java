package br.com.iflashfood.pagamentos.service;

import br.com.iflashfood.pagamentos.dto.PagamentoDto;
import br.com.iflashfood.pagamentos.http.PedidoClient;
import br.com.iflashfood.pagamentos.model.Pagamento;
import br.com.iflashfood.pagamentos.model.Status;
import br.com.iflashfood.pagamentos.repository.PagamentoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class PagamentoService {

    @Autowired
    private PagamentoRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PedidoClient pedidoClient;

    public Page<PagamentoDto> obterTodos(Pageable paginacao){
        return repository
                .findAll(paginacao)
                .map(p -> modelMapper.map(p, PagamentoDto.class));
        //Aqui, ao invés de fazer um new PagamentoDto passando as informações que serão retornadas,
        //estou chamando o modelMapper que já irá setar as informações e gerar os objetos PagamentoDto
    }

    public PagamentoDto obterPorId(Long id){
        Pagamento pagamento = repository.findById(id).orElseThrow(() -> new EntityNotFoundException());
        PagamentoDto dto =  modelMapper.map(pagamento, PagamentoDto.class);
        dto.setItens(pedidoClient.obterItensDoPedido(pagamento.getPedidoId()).getItens());
        return dto;
    }

    public PagamentoDto criarPagamento(PagamentoDto dto){
        Pagamento pagamento = modelMapper.map(dto, Pagamento.class);
        pagamento.setStatus(Status.CRIADO);
        repository.save(pagamento);

        return modelMapper.map(pagamento, PagamentoDto.class);
    }

    public PagamentoDto atualizarPagamento(Long id, PagamentoDto dto){
        Pagamento pagamento = modelMapper.map(dto, Pagamento.class);
        pagamento.setId(id);
        pagamento = repository.save(pagamento);

        return modelMapper.map(pagamento, PagamentoDto.class);
    }

    public void excluirPagamento(Long id){
        repository.deleteById(id);
    }

    public void confirmaPagamento(Long id){
        var pagamento = repository.findById(id);

        if(pagamento == null){
            throw new EntityNotFoundException();
        }

        pagamento.get().setStatus(Status.CONFIRMADO);
        repository.save(pagamento.get());
        pedidoClient.aprovaPagamento(pagamento.get().getPedidoId());
    }


    public void alteraStatusQuandoPagamentoPendente(Long id) {
        var pagamento = repository.findById(id);

        if(pagamento == null){
            throw new EntityNotFoundException();
        }

        pagamento.get().setStatus(Status.CONFIRMADO_SEM_INTEGRACAO);
        repository.save(pagamento.get());
    }
}
