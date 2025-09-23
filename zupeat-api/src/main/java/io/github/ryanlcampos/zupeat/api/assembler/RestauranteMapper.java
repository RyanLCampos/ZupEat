package io.github.ryanlcampos.zupeat.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.github.ryanlcampos.zupeat.api.model.RestauranteModel;
import io.github.ryanlcampos.zupeat.api.model.input.RestauranteInput;
import io.github.ryanlcampos.zupeat.domain.model.Cidade;
import io.github.ryanlcampos.zupeat.domain.model.Cozinha;
import io.github.ryanlcampos.zupeat.domain.model.Restaurante;

@Component
public class RestauranteMapper {
    
	@Autowired
	private ModelMapper modelMapper;

	// DOMAIN -> DTO

    public RestauranteModel toModel(Restaurante restaurante) {
		return modelMapper.map(restaurante, RestauranteModel.class);
	}

	public List<RestauranteModel> toCollectionModel(List<Restaurante> restaurantes) {
		return restaurantes.stream()
		.map(restaurante -> toModel(restaurante))
		.collect(Collectors.toList());
	}

	// DTO -> DOMAIN

	public Restaurante toDomainObject(RestauranteInput restauranteInput) {
		return modelMapper.map(restauranteInput, Restaurante.class);
	}
	
	public void copyToDomainObject(RestauranteInput restauranteInput, Restaurante restaurante) {
		// Para evitar exception
		restaurante.setCozinha(new Cozinha());

		if(restaurante.getEndereco() != null) {
			restaurante.getEndereco().setCidade(new Cidade());
		}

		modelMapper.map(restauranteInput, restaurante);
	}
}
