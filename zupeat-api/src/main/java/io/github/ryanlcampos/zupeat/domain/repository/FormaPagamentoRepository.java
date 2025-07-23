package io.github.ryanlcampos.zupeat.domain.repository;

import java.util.List;

import io.github.ryanlcampos.zupeat.domain.model.FormaPagamento;

public interface FormaPagamentoRepository {
	
	List<FormaPagamento> listar();
	FormaPagamento buscar(Long id);
	FormaPagamento salvar(FormaPagamento formaPagamento);
	void remover(FormaPagamento formaPagamento);
	
}
