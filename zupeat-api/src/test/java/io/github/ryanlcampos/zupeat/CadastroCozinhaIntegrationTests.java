package io.github.ryanlcampos.zupeat;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import io.github.ryanlcampos.zupeat.domain.model.Cozinha;
import io.github.ryanlcampos.zupeat.domain.service.CadastroCozinhaService;
import jakarta.validation.ConstraintViolationException;

@SpringBootTest
class CadastroCozinhaIntegrationTests {

    @Autowired
    private CadastroCozinhaService cadastroCozinha;

    @Test
    public void deveAtribuirId_QuandoCadastrarCozinhaComDadosCorretos() {
        // Cenário
        Cozinha novaCozinha = new Cozinha();
        novaCozinha.setNome("Chinesa");

        // Ação
        novaCozinha = cadastroCozinha.salvar(novaCozinha);

        // Validação
        assertThat(novaCozinha).isNotNull();
        assertThat(novaCozinha.getId()).isNotNull();
    }

    @Test
    public void deveFalhar_QuandoCadastrarCozinhaSemNome(){
        // Cenário
        Cozinha novaCozinha = new Cozinha();

        novaCozinha.setNome(null);

        // Ação
        ConstraintViolationException erroEsperado =
                Assertions.assertThrows(ConstraintViolationException.class, () -> {
                    cadastroCozinha.salvar(novaCozinha);
                });

        // Validação]
        assertThat(erroEsperado).isNotNull();
    }

}
