package io.github.ryanlcampos.zupeat.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.ryanlcampos.zupeat.domain.exceptions.EntidadeEmUsoException;
import io.github.ryanlcampos.zupeat.domain.exceptions.EstadoNaoEncontradoException;
import io.github.ryanlcampos.zupeat.domain.model.Estado;
import io.github.ryanlcampos.zupeat.domain.repository.EstadoRepository;

@Service
public class CadastroEstadoService {
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Transactional
	public Estado salvar(Estado estado) {
		return estadoRepository.save(estado);
	}

	@Transactional
	public void remover(Long id) {

		try {
			obterPorId(id);

			estadoRepository.deleteById(id);

			estadoRepository.flush();

		} catch (DataIntegrityViolationException e){
			throw new EntidadeEmUsoException(String.format("Estado com codigo %d não pode ser removido, pois está em uso", id));
		}
	}
	
	public Estado obterPorId(Long id) {
		return estadoRepository.findById(id)
				.orElseThrow(() -> new EstadoNaoEncontradoException(id));
	}
	
}
