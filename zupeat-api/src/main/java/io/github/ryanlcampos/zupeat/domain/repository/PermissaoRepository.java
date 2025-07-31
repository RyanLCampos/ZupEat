package io.github.ryanlcampos.zupeat.domain.repository;

import io.github.ryanlcampos.zupeat.domain.model.Permissao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissaoRepository extends JpaRepository<Permissao, Long> {
	
}
