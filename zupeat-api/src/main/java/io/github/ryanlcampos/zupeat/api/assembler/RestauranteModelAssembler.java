package io.github.ryanlcampos.zupeat.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.github.ryanlcampos.zupeat.api.model.RestauranteModel;
import io.github.ryanlcampos.zupeat.domain.model.Restaurante;

@Component
public class RestauranteModelAssembler {
    
	@Autowired
	private ModelMapper modelMapper;

    public RestauranteModel toModel(Restaurante restaurante) {
		return modelMapper.map(restaurante, RestauranteModel.class);
	}

	public List<RestauranteModel> toCollectionModel(List<Restaurante> restaurantes) {
		return restaurantes.stream()
		.map(restaurante -> toModel(restaurante))
		.collect(Collectors.toList());
	}
}
