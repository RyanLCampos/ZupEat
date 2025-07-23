package io.github.ryanlcampos.zupeat.domain.repository;

import java.util.List;

import io.github.ryanlcampos.zupeat.domain.model.Restaurante;

public interface RestauranteRepository {
	
	List<Restaurante> listar();
	Restaurante buscar(Long id);
	Restaurante salvar(Restaurante restaurante);
	void remover(Restaurante restaurante);
	
}
