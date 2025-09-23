package io.github.ryanlcampos.zupeat.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.github.ryanlcampos.zupeat.api.model.CidadeModel;
import io.github.ryanlcampos.zupeat.api.model.input.CidadeInput;
import io.github.ryanlcampos.zupeat.domain.model.Cidade;
import io.github.ryanlcampos.zupeat.domain.model.Estado;

@Component
public class CidadeMapper {

    @Autowired
    private ModelMapper modelMapper;

    // DOMAIN -> DTO

    public CidadeModel toModel(Cidade cidade) {
        return modelMapper.map(cidade, CidadeModel.class);
    }

    public List<CidadeModel> toCollectionModel(List<Cidade> cidades) {
        return cidades.stream()
                .map(cidade -> toModel(cidade))
                .collect(Collectors.toList());
    }

    // DTO -> DOMAIN

    public Cidade toDomainObject(CidadeInput cidadeInput) {
        return modelMapper.map(cidadeInput, Cidade.class);
    }

    public void copyToDomainObject(CidadeInput cidadeInput, Cidade cidade) {
		// Para evitar exception
		cidade.setEstado(new Estado());

		modelMapper.map(cidadeInput, cidade);
	}
}
