package io.github.ryanlcampos.zupeat.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import io.github.ryanlcampos.zupeat.domain.exceptions.EntidadeEmUsoException;
import io.github.ryanlcampos.zupeat.domain.exceptions.EntidadeNaoEncontradaException;
import io.github.ryanlcampos.zupeat.domain.model.Cozinha;
import io.github.ryanlcampos.zupeat.domain.repository.CozinhaRepository;

@Service
public class CadastroCozinhaService {

	public static final String MSG_COZINHA_NAO_ENCONTRADA = "Cozinha de codigo %d não foi encontrada";
	public static final String MSG_COZINHA_EM_USO = "Cozinha de codigo %d não pode ser removida, pois está em uso";
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	public Cozinha salvar(Cozinha cozinha) {
		return cozinhaRepository.save(cozinha);
	}
	
	public void remover(Long id) {
		try {

			if(!cozinhaRepository.existsById(id)){
				throw new EntidadeNaoEncontradaException(
						String.format(MSG_COZINHA_NAO_ENCONTRADA, id));
			}

			cozinhaRepository.deleteById(id);
			
		}catch(DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format(MSG_COZINHA_EM_USO, id));
		}
	}

	public Cozinha buscarPorId(Long cozinhaId) {
		return cozinhaRepository.findById(cozinhaId)
				.orElseThrow(() -> new EntidadeNaoEncontradaException(
						String.format(MSG_COZINHA_NAO_ENCONTRADA, cozinhaId)));
	}
}
