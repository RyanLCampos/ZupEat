package io.github.ryanlcampos.zupeat.domain.exceptions;

import java.io.Serial;

public class CidadeNaoEncontradoException extends EntidadeNaoEncontradaException {


	@Serial
	private static final long serialVersionUID = 1L;

	public CidadeNaoEncontradoException(String mensagem) {
		super(mensagem);
	}

	public CidadeNaoEncontradoException(Long id) {
		this(String.format("Cidade de codigo %d não foi encontrado", id));
	}

}
