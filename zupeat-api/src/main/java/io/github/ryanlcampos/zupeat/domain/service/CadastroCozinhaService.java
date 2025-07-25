package io.github.ryanlcampos.zupeat.domain.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import io.github.ryanlcampos.zupeat.domain.exceptions.EntidadeEmUsoException;
import io.github.ryanlcampos.zupeat.domain.exceptions.EntidadeNaoEncontradaException;
import io.github.ryanlcampos.zupeat.domain.model.Cozinha;
import io.github.ryanlcampos.zupeat.domain.repository.CozinhaRepository;

@Service
public class CadastroCozinhaService {
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	public Cozinha salvar(Cozinha cozinha) {
		return cozinhaRepository.save(cozinha);
	}
	
	public void remover(Long id) {
		try {
			
			Optional<Cozinha> possivelCozinha = cozinhaRepository.findById(id);
			
			if(possivelCozinha.isEmpty()) {
				throw new EmptyResultDataAccessException(1);
			}
			
			cozinhaRepository.deleteById(id);
			
		}catch(DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format("Cozinha de codigo %d não pode ser removida, pois está em uso", id));
		}catch(EmptyResultDataAccessException er) {
			throw new EntidadeNaoEncontradaException(String.format("Cozinha de codigo %d não foi encontrada", id));
		}
	}
	
}
