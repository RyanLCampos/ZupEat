package io.github.ryanlcampos.zupeat.api.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.github.ryanlcampos.zupeat.api.assembler.FormaPagamentoMapper;
import io.github.ryanlcampos.zupeat.api.model.FormaPagamentoModel;
import io.github.ryanlcampos.zupeat.domain.model.FormaPagamento;
import io.github.ryanlcampos.zupeat.domain.model.Restaurante;
import io.github.ryanlcampos.zupeat.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/formas-pagamento")
public class RestauranteFormaPagamentoController {

    @Autowired
    private CadastroRestauranteService cadastroRestaurante;

    @Autowired
    private FormaPagamentoMapper formaPagamentoMapper;

    @GetMapping
    public List<FormaPagamentoModel> listar(@PathVariable Long restauranteId) {
        Restaurante restaurante = cadastroRestaurante.obterPorId(restauranteId);

        Set<FormaPagamento> formasPagamento = restaurante.getFormasPagamento();

        return formaPagamentoMapper.toCollectionModel(formasPagamento);
    }

    @DeleteMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociar(@PathVariable Long restauranteId,
            @PathVariable Long formaPagamentoId) {
        
        cadastroRestaurante.desassociarFormaPagamento(restauranteId, formaPagamentoId);
    }

    @PutMapping("/{formaPagamentoId}")
    public List<FormaPagamentoModel> associar(@PathVariable Long restauranteId,
            @PathVariable Long formaPagamentoId) {
        
        Set<FormaPagamento> formasPagamento = cadastroRestaurante.associarFormaPagamento(restauranteId, formaPagamentoId);

        return formaPagamentoMapper.toCollectionModel(formasPagamento);
    }

}
