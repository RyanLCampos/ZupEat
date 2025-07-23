package io.github.ryanlcampos.zupeat.domain.repository;

import java.util.List;

import io.github.ryanlcampos.zupeat.domain.model.Estado;

public interface EstadoRepository {
	
	List<Estado> listar();
	Estado buscar(Long id);
	Estado salvar(Estado estado);
	void remover(Estado estado);
	
}
