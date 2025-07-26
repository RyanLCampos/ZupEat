package io.github.ryanlcampos.zupeat.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.ryanlcampos.zupeat.domain.model.Restaurante;

public interface RestauranteRepository extends JpaRepository<Restaurante, Long>{
	
}
