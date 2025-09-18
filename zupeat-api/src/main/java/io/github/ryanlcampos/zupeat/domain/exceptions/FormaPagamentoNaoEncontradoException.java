package io.github.ryanlcampos.zupeat.domain.exceptions;

import java.io.Serial;

public class FormaPagamentoNaoEncontradoException extends EntidadeNaoEncontradaException {


    @Serial
	private static final long serialVersionUID = 1L;
    
    public FormaPagamentoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
    
    public FormaPagamentoNaoEncontradoException(Long id) {
        this(String.format("Forma de Pagamento de código %d não foi encontrado", id));
    }
}
