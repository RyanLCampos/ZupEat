package io.github.ryanlcampos.zupeat.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.ryanlcampos.zupeat.domain.model.Cozinha;

public interface CozinhaRepository extends JpaRepository<Cozinha, Long>{
	
	Cozinha findByNome(String nome);
	
}
