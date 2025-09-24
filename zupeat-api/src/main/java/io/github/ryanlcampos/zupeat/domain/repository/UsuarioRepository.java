package io.github.ryanlcampos.zupeat.domain.repository;

import java.util.Optional;

import io.github.ryanlcampos.zupeat.domain.model.Usuario;


public interface UsuarioRepository extends CustomJpaRepository<Usuario, Long>{

    Optional<Usuario> findByEmail(String email);

}
