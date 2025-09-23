package io.github.ryanlcampos.zupeat.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.github.ryanlcampos.zupeat.api.model.CozinhaModel;
import io.github.ryanlcampos.zupeat.api.model.input.CozinhaInput;
import io.github.ryanlcampos.zupeat.domain.model.Cozinha;

@Component
public class CozinhaMapper {

    @Autowired
    private ModelMapper modelMapper;

    // DOMAIN -> DTO

    public CozinhaModel toModel(Cozinha cozinha) {
        return modelMapper.map(cozinha, CozinhaModel.class);
    }

    public List<CozinhaModel> toCollectionModel(List<Cozinha> cozinhas) {
        return cozinhas.stream()
                .map(cozinha -> toModel(cozinha))
                .collect(Collectors.toList());
    }

    // DTO -> DOMAIN

    public Cozinha toDomainObject(CozinhaInput cozinhaInput) {
        return modelMapper.map(cozinhaInput, Cozinha.class);
    }

    public void copyToDomainObject(CozinhaInput cozinhaInput, Cozinha cozinha){
        modelMapper.map(cozinhaInput, cozinha);
    }
}
