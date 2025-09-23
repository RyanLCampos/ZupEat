package io.github.ryanlcampos.zupeat.api.model.input;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SenhaInput {
    
    @NotBlank
    private String senha;

    @NotBlank
    private String novaSenha;
}
