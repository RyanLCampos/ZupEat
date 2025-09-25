package io.github.ryanlcampos.zupeat.domain.exceptions;

import java.io.Serial;

public class ProdutoNaoEncontradoException extends EntidadeNaoEncontradaException{

    @Serial
	private static final long serialVersionUID = 1L;
    
    public ProdutoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public ProdutoNaoEncontradoException(Long produtoId){
        this(String.format("Produto de codigo %d não foi encontrado", produtoId));
    }
    
}
