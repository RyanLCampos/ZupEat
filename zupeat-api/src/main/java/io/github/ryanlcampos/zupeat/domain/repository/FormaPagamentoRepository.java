package io.github.ryanlcampos.zupeat.domain.repository;

import io.github.ryanlcampos.zupeat.domain.model.FormaPagamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FormaPagamentoRepository extends JpaRepository<FormaPagamento, Long> {
	
}
