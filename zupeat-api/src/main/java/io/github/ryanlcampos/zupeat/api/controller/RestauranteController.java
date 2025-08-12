package io.github.ryanlcampos.zupeat.api.controller;

import java.math.BigDecimal;
import java.util.List;

import io.github.ryanlcampos.zupeat.domain.exceptions.CozinhaNaoEncontradoException;
import io.github.ryanlcampos.zupeat.domain.exceptions.NegocioException;
import io.github.ryanlcampos.zupeat.domain.repository.RestauranteRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import io.github.ryanlcampos.zupeat.domain.model.Restaurante;
import io.github.ryanlcampos.zupeat.domain.service.CadastroRestauranteService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {
	
	@Autowired
	private CadastroRestauranteService cadastroRestaurante;
	@Autowired
	private RestauranteRepository restauranteRepository;

	@GetMapping
	public List<Restaurante> listar(){
		return restauranteRepository.findAll();
	}

	@GetMapping("/por-nome-e-frete")
	public List<Restaurante> buscarPorNomeFrete(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {
		return restauranteRepository.consultar(nome, taxaFreteInicial, taxaFreteFinal);
	}

	@GetMapping("/com-frete-gratis")
	public List<Restaurante> buscarPorFreteGratisNomeSemelhante(String nome) {
		return restauranteRepository.findComFreteGratis(nome);
	}

	@GetMapping("/{restauranteId}")
	public Restaurante buscar(@PathVariable Long restauranteId){
		return cadastroRestaurante.obterPorId(restauranteId);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Restaurante adicionar(@RequestBody @Valid Restaurante restaurante){
		try {
			return cadastroRestaurante.salvar(restaurante);
		} catch (CozinhaNaoEncontradoException e) {
			throw new NegocioException(e.getMessage());
		}
	}
	
	@PutMapping("/{restauranteId}")
	public Restaurante atualizar(@PathVariable Long restauranteId, @RequestBody @Valid Restaurante restaurante){
		try {

			Restaurante restauranteAtual = cadastroRestaurante.obterPorId(restauranteId);

			BeanUtils.copyProperties(restaurante, restauranteAtual,
					"id", "formasPagamento", "endereco", "dataCadastro", "produtos");

			return cadastroRestaurante.salvar(restauranteAtual);
		} catch (CozinhaNaoEncontradoException e) {
			throw new NegocioException(e.getMessage());
		}

	}
	
	@DeleteMapping("/{restauranteId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long restauranteId){
		cadastroRestaurante.remover(restauranteId);
	}
	
}
