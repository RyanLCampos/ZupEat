package io.github.ryanlcampos.zupeat.domain.exceptions;

import java.io.Serial;

public class PermissaoNaoEncontradaException extends EntidadeNaoEncontradaException{

    @Serial
	private static final long serialVersionUID = 1L;
    
    public PermissaoNaoEncontradaException(String mensagem) {
        super(mensagem);
    }

    public PermissaoNaoEncontradaException(Long permissaoId){
        this(String.format("Permissão de codigo %d não foi encontrada", permissaoId));
    }
    
}
