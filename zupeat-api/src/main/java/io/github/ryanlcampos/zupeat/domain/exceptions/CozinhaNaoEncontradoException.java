package io.github.ryanlcampos.zupeat.domain.exceptions;

import java.io.Serial;

public class CozinhaNaoEncontradoException extends EntidadeNaoEncontradaException {


	@Serial
	private static final long serialVersionUID = 1L;

	public CozinhaNaoEncontradoException(String mensagem) {
		super(mensagem);
	}

	public CozinhaNaoEncontradoException(Long id) {
		this(String.format("Cozinha de codigo %d não foi encontrado", id));
	}

}
