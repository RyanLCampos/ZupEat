package io.github.ryanlcampos.zupeat.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.ryanlcampos.zupeat.domain.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

}
