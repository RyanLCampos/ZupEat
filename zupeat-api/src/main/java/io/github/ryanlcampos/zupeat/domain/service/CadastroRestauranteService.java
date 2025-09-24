package io.github.ryanlcampos.zupeat.domain.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.ryanlcampos.zupeat.domain.exceptions.EntidadeEmUsoException;
import io.github.ryanlcampos.zupeat.domain.exceptions.RestauranteNaoEncontradoException;
import io.github.ryanlcampos.zupeat.domain.model.Cidade;
import io.github.ryanlcampos.zupeat.domain.model.Cozinha;
import io.github.ryanlcampos.zupeat.domain.model.FormaPagamento;
import io.github.ryanlcampos.zupeat.domain.model.Restaurante;
import io.github.ryanlcampos.zupeat.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CadastroCozinhaService cadastroCozinha;

	@Autowired
	private CadastroCidadeService cadastroCidade;

	@Autowired
	private CadastroFormaPagamentoService cadastroFormaPagamento;

	@Transactional
	public Restaurante salvar(Restaurante restaurante) {
		
		Long cozinhaId = restaurante.getCozinha().getId();
		
		Cozinha cozinha = cadastroCozinha.obterPorId(cozinhaId);

		restaurante.setCozinha(cozinha);

		Long cidadeId = restaurante.getEndereco().getCidade().getId();

		Cidade cidade = cadastroCidade.obterPorId(cidadeId);

		restaurante.getEndereco().setCidade(cidade);
		
		return restauranteRepository.save(restaurante);
		
	}
	
	@Transactional
	public void remover(Long id) {
		try {
			obterPorId(id);
			
			restauranteRepository.deleteById(id);

			restauranteRepository.flush();
			
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format("Restaurante com codigo %d não pode ser removido, pois está em uso", id));
		}
		
	}

	@Transactional
	public void desassociarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
		Restaurante restaurante = obterPorId(restauranteId);

		FormaPagamento formaPagamento = cadastroFormaPagamento.obterPorId(formaPagamentoId);

		restaurante.removerFormaPagamento(formaPagamento);
	}

	@Transactional
	public Set<FormaPagamento> associarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
		Restaurante restaurante = obterPorId(restauranteId);

		FormaPagamento formaPagamento = cadastroFormaPagamento.obterPorId(formaPagamentoId);

		restaurante.adicionarFormaPagamento(formaPagamento);

		return restaurante.getFormasPagamento();
	}
	
	@Transactional
	public void ativar(Long restauranteId) {
		Restaurante restauranteAtual = obterPorId(restauranteId);

		restauranteAtual.ativar();
	}

	@Transactional
	public void inativar(Long restauranteId) {
		Restaurante restauranteAtual = obterPorId(restauranteId);

		restauranteAtual.inativar();
	}

	public Restaurante obterPorId(Long id) {
		return restauranteRepository.findById(id)
				.orElseThrow(() -> new RestauranteNaoEncontradoException(id));
	}

	
}
