package io.github.ryanlcampos.zupeat.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.ryanlcampos.zupeat.domain.model.Estado;

public interface EstadoRepository extends JpaRepository<Estado, Long>{
	
}
