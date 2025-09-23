package io.github.ryanlcampos.zupeat.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.ryanlcampos.zupeat.domain.model.Grupo;

public interface GrupoRepository extends JpaRepository<Grupo, Long> {
    
}
