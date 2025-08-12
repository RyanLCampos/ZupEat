package io.github.ryanlcampos.zupeat.domain.exceptions;

import java.io.Serial;

public class EstadoNaoEncontradoException extends EntidadeNaoEncontradaException {


	@Serial
	private static final long serialVersionUID = 1L;

	public EstadoNaoEncontradoException(String mensagem) {
		super(mensagem);
	}

	public EstadoNaoEncontradoException(Long id) {
		this(String.format("Estado de codigo %d não foi encontrado", id));
	}

}
