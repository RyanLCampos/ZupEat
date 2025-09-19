package io.github.ryanlcampos.zupeat.domain.exceptions;

import java.io.Serial;

public class CidadeNaoEncontradaException extends EntidadeNaoEncontradaException {


	@Serial
	private static final long serialVersionUID = 1L;

	public CidadeNaoEncontradaException(String mensagem) {
		super(mensagem);
	}

	public CidadeNaoEncontradaException(Long id) {
		this(String.format("Cidade de codigo %d não foi encontrado", id));
	}

}
