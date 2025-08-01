package io.github.ryanlcampos.zupeat.domain.service;

import java.util.Optional;

import io.github.ryanlcampos.zupeat.domain.exceptions.EntidadeEmUsoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import io.github.ryanlcampos.zupeat.domain.exceptions.EntidadeNaoEncontradaException;
import io.github.ryanlcampos.zupeat.domain.model.Estado;
import io.github.ryanlcampos.zupeat.domain.repository.EstadoRepository;

@Service
public class CadastroEstadoService {
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	public Estado salvar(Estado estado) {
		return estadoRepository.save(estado);
	}

	public void remover(Long id) {

		try {
			obterPorId(id);

			estadoRepository.deleteById(id);

		} catch (DataIntegrityViolationException e){
			throw new EntidadeEmUsoException(String.format("Estado com codigo %d não pode ser removido, pois está em uso", id));
		}
	}
	
	public Estado obterPorId(Long id) {
		Optional<Estado> possivelEstado = estadoRepository.findById(id);
		
		if(possivelEstado.isEmpty()) {
			throw new EntidadeNaoEncontradaException(String.format("Estado de codigo %d não foi encontrado", id));
		}
		
		return possivelEstado.get();
	}
	
}
