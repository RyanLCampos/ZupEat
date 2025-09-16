package io.github.ryanlcampos.zupeat.core.jackson;

import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.module.SimpleModule;

import io.github.ryanlcampos.zupeat.api.model.mixin.CidadeMixin;
import io.github.ryanlcampos.zupeat.api.model.mixin.CozinhaMixin;
import io.github.ryanlcampos.zupeat.api.model.mixin.RestauranteMixin;
import io.github.ryanlcampos.zupeat.domain.model.Cidade;
import io.github.ryanlcampos.zupeat.domain.model.Cozinha;
import io.github.ryanlcampos.zupeat.domain.model.Restaurante;

@Configuration
public class JacksonMixinModule extends SimpleModule{
    
    public JacksonMixinModule() {
        setMixInAnnotation(Restaurante.class, RestauranteMixin.class);
        setMixInAnnotation(Cozinha.class, CozinhaMixin.class);
        setMixInAnnotation(Cidade.class, CidadeMixin.class);
    }
}
