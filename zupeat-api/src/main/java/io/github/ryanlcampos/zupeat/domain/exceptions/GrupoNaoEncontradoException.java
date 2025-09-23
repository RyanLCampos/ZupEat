package io.github.ryanlcampos.zupeat.domain.exceptions;

public class GrupoNaoEncontradoException extends EntidadeNaoEncontradaException {
    
    private static final long serialVersionUID = 1L;

    public GrupoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public GrupoNaoEncontradoException(Long grupoId) {
        this(String.format("Não existe um cadastro de grupo com código %d", grupoId));
    }
}
