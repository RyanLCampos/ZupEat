package io.github.ryanlcampos.zupeat.api.model.mixin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.github.ryanlcampos.zupeat.domain.model.Estado;

public class CidadeMixin {
    
    @JsonIgnoreProperties(value = "nome", allowGetters = true)
	private Estado estado;
}
