package io.github.ryanlcampos.zupeat.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.ryanlcampos.zupeat.domain.exceptions.ProdutoNaoEncontradoException;
import io.github.ryanlcampos.zupeat.domain.model.Produto;
import io.github.ryanlcampos.zupeat.domain.repository.ProdutoRepository;

@Service
public class CadastroProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Transactional
    public Produto salvar(Produto produto) {
        return produtoRepository.save(produto);
    }

    @Transactional
    public void remover(Long produtoId) {
        produtoRepository.deleteById(produtoId);
    }

    public Produto obterPorId(Long produtoId, Long restauranteId) {
        return produtoRepository.findById(restauranteId, produtoId)
                .orElseThrow(() -> new ProdutoNaoEncontradoException(produtoId, restauranteId));
    }
}
