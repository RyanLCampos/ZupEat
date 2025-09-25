package io.github.ryanlcampos.zupeat.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.github.ryanlcampos.zupeat.api.model.ProdutoModel;
import io.github.ryanlcampos.zupeat.api.model.input.ProdutoInput;
import io.github.ryanlcampos.zupeat.domain.model.Produto;

@Component
public class ProdutoMapper {

    @Autowired
    private ModelMapper mapper;

    // DOMAIN -> DTO
    public ProdutoModel toModel(Produto produto) {
        return mapper.map(produto, ProdutoModel.class);
    }

    public List<ProdutoModel> toCollectionModel(List<Produto> produtos) {
        return produtos.stream()
                .map(produto -> toModel(produto))
                .collect(Collectors.toList());
    }

    // DTO -> DOMAIN
    public Produto toDomainObject(ProdutoInput produtoInput) {
        return mapper.map(produtoInput, Produto.class);
    }

    public void copyToDomainObject(ProdutoInput produtoInput, Produto produto) {
        mapper.map(produtoInput, produto);
    }

}
