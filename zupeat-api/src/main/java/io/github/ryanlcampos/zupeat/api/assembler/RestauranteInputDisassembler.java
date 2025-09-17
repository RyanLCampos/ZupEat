package io.github.ryanlcampos.zupeat.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.github.ryanlcampos.zupeat.api.model.input.RestauranteInput;
import io.github.ryanlcampos.zupeat.domain.model.Cozinha;
import io.github.ryanlcampos.zupeat.domain.model.Restaurante;

@Component
public class RestauranteInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;
    
    public Restaurante toDomainObject(RestauranteInput restauranteInput) {
		return modelMapper.map(restauranteInput, Restaurante.class);
	}
	
	public void copyToDomainObject(RestauranteInput restauranteInput, Restaurante restaurante) {
		// Para evitar exception
		restaurante.setCozinha(new Cozinha());

		modelMapper.map(restauranteInput, restaurante);
	}

}
