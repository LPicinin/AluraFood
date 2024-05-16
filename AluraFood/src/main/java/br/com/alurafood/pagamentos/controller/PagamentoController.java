package br.com.alurafood.pagamentos.controller;

import br.com.alurafood.pagamentos.dto.PagamentoDTO;
import br.com.alurafood.pagamentos.service.PagamentoService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController
{
    @Autowired
    private PagamentoService service;

    @GetMapping
    public Page<PagamentoDTO> list(@PageableDefault(size = 10) Pageable paginacao)
    {
        return service.obterTodos(paginacao);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagamentoDTO> show(@PathVariable @NotNull Long id)
    {
        PagamentoDTO pagamentoDto = service.obterPorId(id);
        return ResponseEntity.ok(pagamentoDto);
    }

    @PostMapping
    public ResponseEntity<PagamentoDTO> create(@RequestBody @Valid PagamentoDTO dto, UriComponentsBuilder uriBuilder)
    {
        PagamentoDTO pagamento = service.criarPagamento(dto);
        URI endereco = uriBuilder.path("/pagamentos/{id}").buildAndExpand(pagamento.getId()).toUri();
        return ResponseEntity.created(endereco).body(pagamento);
    }
    @PutMapping("/{id}")
    public ResponseEntity<PagamentoDTO> update(@PathVariable @NotNull Long id, @RequestBody @Valid PagamentoDTO dto)
    {
        PagamentoDTO pagamento = service.atualizaPagamento(id, dto);
        return ResponseEntity.ok(pagamento);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PagamentoDTO> delete(@PathVariable @NotNull Long id)
    {
        service.excluirPagamento(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/confirmar")
    @CircuitBreaker(name="atualizaPedido", fallbackMethod = "pagamentoAutorizadoComIntegracaoPendente")
    public void confirmarPagamento(@PathVariable @NotNull Long id)
    {
        service.confirmarPagamento(id);
    }

    public void pagamentoAutorizadoComIntegracaoPendente(Long id, Exception e)
    {
        service.alteraStatus(id);
    }

}
