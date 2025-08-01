package io.github.ryanlcampos.zupeat.infrastructure.repository;

import io.github.ryanlcampos.zupeat.domain.model.Restaurante;
import io.github.ryanlcampos.zupeat.domain.repository.RestauranteRepository;
import io.github.ryanlcampos.zupeat.domain.repository.RestauranteRepositoryQueries;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static io.github.ryanlcampos.zupeat.infrastructure.repository.spec.RestauranteSpecs.comFreteGratis;
import static io.github.ryanlcampos.zupeat.infrastructure.repository.spec.RestauranteSpecs.comNomeSemelhante;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQueries {

    @PersistenceContext
    private EntityManager manager;

    @Autowired @Lazy
    private RestauranteRepository restauranteRepository;

    @Override
    public List<Restaurante> consultar(String nome,
                                       BigDecimal taxaFreteInicial,
                                       BigDecimal taxaFreteFinal) {

        CriteriaBuilder builder = manager.getCriteriaBuilder();

        CriteriaQuery<Restaurante> criteria = builder.createQuery(Restaurante.class);
        Root<Restaurante> root = criteria.from(Restaurante.class);

        var predicates = new ArrayList<Predicate>();

        if(StringUtils.hasText(nome)) {
            predicates.add(builder.like(root.get("nome"), "%" + nome + "%"));
        }

        if(taxaFreteInicial != null){
            predicates.add(builder.greaterThanOrEqualTo(root.get("taxaFrete"), taxaFreteInicial));
        }

        if(taxaFreteFinal != null){
            predicates.add(builder.lessThanOrEqualTo(root.get("taxaFrete"), taxaFreteFinal));
        }


        criteria.where(predicates.toArray(new Predicate[0]));

        TypedQuery<Restaurante> query = manager.createQuery(criteria);
        return query.getResultList();
    }

    @Override
    public List<Restaurante> findComFreteGratis(String nome) {
        return restauranteRepository.findAll(comFreteGratis().and(comNomeSemelhante(nome)));
    }

}
