package io.github.ryanlcampos.zupeat.domain.repository;

import java.util.List;

import io.github.ryanlcampos.zupeat.domain.model.Cidade;

public interface CidadeRepository {
	
	List<Cidade> listar();
	Cidade buscar(Long id);
	Cidade salvar(Cidade cidade);
	void remover(Cidade cidade);
}
