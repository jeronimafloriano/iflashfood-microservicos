package br.com.iflashfood.pedidos.controller;

import br.com.iflashfood.pedidos.dto.ItensPedidoDto;
import br.com.iflashfood.pedidos.dto.PedidoDto;
import br.com.iflashfood.pedidos.dto.StatusDto;
import br.com.iflashfood.pedidos.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService service;

    @GetMapping
    public List<PedidoDto> listarTodos(){
        return service.obterTodos();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PedidoDto listarPorId(@PathVariable @NotNull Long id){
        return service.obterPorId(id);
    }

    @GetMapping("/porta")
    public String retornaPorta(@Value("${local.server.port}") String porta){
        return String.format("Requisição respondida pela instância executando na porta %s", porta);
    }

    @PostMapping
    public ResponseEntity<PedidoDto> realizaPedido(@RequestBody @Valid PedidoDto dto, UriComponentsBuilder uriBuilder){
        PedidoDto pedido = service.criarPedido(dto);
        var uri = uriBuilder.path("/pedidos/{id}").buildAndExpand(pedido.getId()).toUri();
        return ResponseEntity.created(uri).body(pedido);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<PedidoDto> atualizaStatus(@PathVariable @NotNull Long id, @RequestBody StatusDto status){
        PedidoDto atualizado = service.atualizarStatus(id, status);
        return ResponseEntity.ok(atualizado);
    }

    @PutMapping("/{id}/pago")
    public ResponseEntity<Void> aprovaPagamento(@PathVariable @NotNull Long id){
        service.aprovaPagamentoPedido(id);
        return ResponseEntity.ok().build();
    }

}
