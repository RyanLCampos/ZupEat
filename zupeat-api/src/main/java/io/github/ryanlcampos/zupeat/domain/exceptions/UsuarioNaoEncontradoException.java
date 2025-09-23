package io.github.ryanlcampos.zupeat.domain.exceptions;

import java.io.Serial;

public class UsuarioNaoEncontradoException extends EntidadeNaoEncontradaException{

    @Serial
	private static final long serialVersionUID = 1L;
    
    public UsuarioNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
    
    public UsuarioNaoEncontradoException(Long id) {
		this(String.format("Usuario de codigo %d não foi encontrado", id));
	}
    
}
