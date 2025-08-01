package io.github.ryanlcampos.zupeat.domain.repository;

import io.github.ryanlcampos.zupeat.domain.model.Restaurante;

import java.math.BigDecimal;
import java.util.List;

public interface RestauranteRepositoryQueries {
    List<Restaurante> consultar(String nome,
                                BigDecimal taxaFreteInicial,
                                BigDecimal taxaFreteFinal);

    List<Restaurante> findComFreteGratis(String nome);
}
