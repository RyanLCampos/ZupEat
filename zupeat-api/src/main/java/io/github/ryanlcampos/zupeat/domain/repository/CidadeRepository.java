package io.github.ryanlcampos.zupeat.domain.repository;

import io.github.ryanlcampos.zupeat.domain.model.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CidadeRepository extends JpaRepository<Cidade, Long> {
}
