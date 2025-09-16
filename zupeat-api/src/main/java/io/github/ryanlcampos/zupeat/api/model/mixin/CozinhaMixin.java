package io.github.ryanlcampos.zupeat.api.model.mixin;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.github.ryanlcampos.zupeat.domain.model.Restaurante;

public class CozinhaMixin {

    @JsonIgnore
	private List<Restaurante> restaurantes = new ArrayList<>();
}
