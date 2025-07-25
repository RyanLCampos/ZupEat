package io.github.ryanlcampos.zupeat.domain.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import io.github.ryanlcampos.zupeat.domain.exceptions.EntidadeEmUsoException;
import io.github.ryanlcampos.zupeat.domain.exceptions.EntidadeNaoEncontradaException;
import io.github.ryanlcampos.zupeat.domain.model.Restaurante;
import io.github.ryanlcampos.zupeat.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	public Restaurante salvar(Restaurante restaurante) {
		return restauranteRepository.save(restaurante);
	}
	
	public void remover(Long id) {
		try {
			Optional<Restaurante> possivelRestaurante = restauranteRepository.findById(id);
			
			if(possivelRestaurante.isEmpty()) {
				throw new EmptyResultDataAccessException(1);
			}
			
			restauranteRepository.deleteById(id);
			
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format("Restaurante com codigo %d não pode ser removido, pois está em uso", id));
		} catch (EmptyResultDataAccessException er) {
			throw new EntidadeNaoEncontradaException(String.format("Restaurante com código %d não foi encontrado", id));
		}
		
	}
	
}
