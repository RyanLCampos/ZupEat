package io.github.ryanlcampos.zupeat.domain.repository;

import java.util.List;

import io.github.ryanlcampos.zupeat.domain.model.Permissao;

public interface PermissaoRepository {
	
	List<Permissao> listar();
	Permissao buscar(Long id);
	Permissao salvar(Permissao permissao);
	void remover(Permissao permissao);
	
}
