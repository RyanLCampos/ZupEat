package io.github.ryanlcampos.zupeat.api.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.github.ryanlcampos.zupeat.api.assembler.RestauranteMapper;
import io.github.ryanlcampos.zupeat.api.model.RestauranteModel;
import io.github.ryanlcampos.zupeat.api.model.input.RestauranteInput;
import io.github.ryanlcampos.zupeat.domain.exceptions.CidadeNaoEncontradaException;
import io.github.ryanlcampos.zupeat.domain.exceptions.CozinhaNaoEncontradoException;
import io.github.ryanlcampos.zupeat.domain.exceptions.NegocioException;
import io.github.ryanlcampos.zupeat.domain.model.Restaurante;
import io.github.ryanlcampos.zupeat.domain.repository.RestauranteRepository;
import io.github.ryanlcampos.zupeat.domain.service.CadastroRestauranteService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {
	
	@Autowired
	private CadastroRestauranteService cadastroRestaurante;

	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private RestauranteMapper restauranteMapper;

	@GetMapping
	public List<RestauranteModel> listar(){
		return restauranteMapper.toCollectionModel(restauranteRepository.findAll());
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
	public RestauranteModel buscar(@PathVariable Long restauranteId){
		Restaurante restaurante = cadastroRestaurante.obterPorId(restauranteId);

		return restauranteMapper.toModel(restaurante);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public RestauranteModel adicionar(@RequestBody @Valid RestauranteInput restauranteInput){
		try {
			Restaurante restaurante = restauranteMapper.toDomainObject(restauranteInput);

			return restauranteMapper.toModel(cadastroRestaurante.salvar(restaurante));
		} catch (CozinhaNaoEncontradoException | CidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}
	
	@PutMapping("/{restauranteId}")
	public RestauranteModel atualizar(@PathVariable Long restauranteId, @RequestBody @Valid RestauranteInput restauranteInput){
		try {
			Restaurante restauranteAtual = cadastroRestaurante.obterPorId(restauranteId);

			restauranteMapper.copyToDomainObject(restauranteInput, restauranteAtual);
			
			return restauranteMapper.toModel(cadastroRestaurante.salvar(restauranteAtual));
		} catch (CozinhaNaoEncontradoException | CidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}
	
	@DeleteMapping("/{restauranteId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long restauranteId){
		cadastroRestaurante.remover(restauranteId);
	}

	@PutMapping("/{restauranteId}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ativar(@PathVariable Long restauranteId) {
		cadastroRestaurante.ativar(restauranteId);
	}

	@DeleteMapping("/{restauranteId}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void inativar(@PathVariable Long restauranteId) {
		cadastroRestaurante.inativar(restauranteId);
	}

	@PutMapping("/{restauranteId}/abertura")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void abrir(@PathVariable Long restauranteId){
		cadastroRestaurante.abrir(restauranteId);
	}

	@PutMapping("/{restauranteId}/fechamento")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void fechar(@PathVariable Long restauranteId){
		cadastroRestaurante.fechar(restauranteId);
	}

}
