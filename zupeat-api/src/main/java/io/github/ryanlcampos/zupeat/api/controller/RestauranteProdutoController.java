package io.github.ryanlcampos.zupeat.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.github.ryanlcampos.zupeat.api.assembler.ProdutoMapper;
import io.github.ryanlcampos.zupeat.api.model.ProdutoModel;
import io.github.ryanlcampos.zupeat.api.model.input.ProdutoInput;
import io.github.ryanlcampos.zupeat.domain.model.Produto;
import io.github.ryanlcampos.zupeat.domain.model.Restaurante;
import io.github.ryanlcampos.zupeat.domain.repository.ProdutoRepository;
import io.github.ryanlcampos.zupeat.domain.service.CadastroProdutoService;
import io.github.ryanlcampos.zupeat.domain.service.CadastroRestauranteService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos")
public class RestauranteProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CadastroProdutoService cadastroProduto;

    @Autowired
    private CadastroRestauranteService cadastroRestaurante;

    @Autowired
    private ProdutoMapper mapper;

    @GetMapping
    public List<ProdutoModel> listar(@PathVariable Long restauranteId) {

        Restaurante restaurante = cadastroRestaurante.obterPorId(restauranteId);

        return mapper.toCollectionModel(produtoRepository.findByRestaurante(restaurante));
    }

    @GetMapping("/{produtoId}")
    public ProdutoModel buscar(@PathVariable Long restauranteId,
            @PathVariable Long produtoId) {

        Produto produto = cadastroProduto.obterPorId(produtoId, restauranteId);

        return mapper.toModel(produto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoModel adicionar(@PathVariable Long restauranteId,
            @RequestBody @Valid ProdutoInput produtoInput) {

        Restaurante restaurante = cadastroRestaurante.obterPorId(restauranteId);

        Produto produto = mapper.toDomainObject(produtoInput);
        produto.setRestaurante(restaurante);

        produto = cadastroProduto.salvar(produto);

        return mapper.toModel(produto);
    }

    @PutMapping("/{produtoId}")
    public ProdutoModel atualizar(@PathVariable Long restauranteId, @PathVariable Long produtoId,
            @RequestBody @Valid ProdutoInput produtoInput) {
        Produto produtoAtual = cadastroProduto.obterPorId(produtoId, restauranteId);

        mapper.copyToDomainObject(produtoInput, produtoAtual);

        produtoAtual = cadastroProduto.salvar(produtoAtual);

        return mapper.toModel(produtoAtual);
    }

}
