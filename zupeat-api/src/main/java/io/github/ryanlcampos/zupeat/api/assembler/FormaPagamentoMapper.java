package io.github.ryanlcampos.zupeat.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.github.ryanlcampos.zupeat.api.model.FormaPagamentoModel;
import io.github.ryanlcampos.zupeat.api.model.input.FormaPagamentoInput;
import io.github.ryanlcampos.zupeat.domain.model.FormaPagamento;

@Component
public class FormaPagamentoMapper {

    @Autowired
    private ModelMapper modelMapper;

    // DOMAIN -> DTO

    public FormaPagamentoModel toModel(FormaPagamento formaPagamento) {
        return modelMapper.map(formaPagamento, FormaPagamentoModel.class);
    }

    public List<FormaPagamentoModel> toCollectionModel(List<FormaPagamento> formasPagamentos) {
        return formasPagamentos.stream()
                .map(formaPagamento -> toModel(formaPagamento))
                .collect(Collectors.toList());
    }

    // DTO -> DOMAIN

    public FormaPagamento toDomainObject(FormaPagamentoInput formaPagamentoInput) {
        return modelMapper.map(formaPagamentoInput, FormaPagamento.class);
    }

    public void copyToDomainObject(FormaPagamentoInput formaPagamentoInput, FormaPagamento formaPagamento) {
        modelMapper.map(formaPagamentoInput, formaPagamento);
    }

}
