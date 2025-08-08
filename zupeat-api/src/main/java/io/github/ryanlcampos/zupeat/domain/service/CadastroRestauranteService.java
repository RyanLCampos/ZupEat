package io.github.ryanlcampos.zupeat.domain.service;

import java.util.List;
import java.util.Optional;

import io.github.ryanlcampos.zupeat.domain.exceptions.RestauranteNaoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import io.github.ryanlcampos.zupeat.domain.exceptions.EntidadeEmUsoException;
import io.github.ryanlcampos.zupeat.domain.exceptions.EntidadeNaoEncontradaException;
import io.github.ryanlcampos.zupeat.domain.model.Cozinha;
import io.github.ryanlcampos.zupeat.domain.model.Restaurante;
import io.github.ryanlcampos.zupeat.domain.repository.CozinhaRepository;
import io.github.ryanlcampos.zupeat.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CadastroCozinhaService cadastroCozinha;

	public Restaurante salvar(Restaurante restaurante) {
		
		Long cozinhaId = restaurante.getCozinha().getId();
		
		Cozinha cozinha = cadastroCozinha.obterPorId(cozinhaId);

		restaurante.setCozinha(cozinha);
		
		return restauranteRepository.save(restaurante);
		
	}
	
	public void remover(Long id) {
		try {
			obterPorId(id);
			
			restauranteRepository.deleteById(id);
			
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format("Restaurante com codigo %d não pode ser removido, pois está em uso", id));
		}
		
	}
	
	public Restaurante obterPorId(Long id) {
		return restauranteRepository.findById(id)
				.orElseThrow(() -> new RestauranteNaoEncontradoException(id));
	}
	
}
