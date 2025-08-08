package io.github.ryanlcampos.zupeat.domain.exceptions;

import java.io.Serial;

public class RestauranteNaoEncontradoException extends EntidadeNaoEncontradaException {


	@Serial
	private static final long serialVersionUID = 1L;

	public RestauranteNaoEncontradoException(String mensagem) {
		super(mensagem);
	}

	public RestauranteNaoEncontradoException(Long id) {
		this(String.format("Restaurante de codigo %d não foi encontrado", id));
	}

}
