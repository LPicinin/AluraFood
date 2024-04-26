package br.com.alurafood.pagamentos.Repository;

import br.com.alurafood.pagamentos.model.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {
}
