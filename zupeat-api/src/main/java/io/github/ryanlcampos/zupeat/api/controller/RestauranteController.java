package io.github.ryanlcampos.zupeat.api.controller;

import java.util.List;
import java.util.Optional;

import org.apache.catalina.connector.Response;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.github.ryanlcampos.zupeat.domain.exceptions.EntidadeEmUsoException;
import io.github.ryanlcampos.zupeat.domain.exceptions.EntidadeNaoEncontradaException;
import io.github.ryanlcampos.zupeat.domain.model.Restaurante;
import io.github.ryanlcampos.zupeat.domain.repository.RestauranteRepository;
import io.github.ryanlcampos.zupeat.domain.service.CadastroRestauranteService;

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
	
	@GetMapping("/{restauranteId}")
	public ResponseEntity<Restaurante> buscar(@PathVariable Long restauranteId){
		Optional<Restaurante> possivelRestaurante = restauranteRepository.findById(restauranteId);
		
		if(possivelRestaurante.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		Restaurante restauranteEncontrado = possivelRestaurante.get();
		
		return ResponseEntity.ok(restauranteEncontrado);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Restaurante adicionar(@RequestBody Restaurante restaurante){
		return cadastroRestaurante.salvar(restaurante);
	}
	
	@PutMapping("/{restauranteId}")
	public ResponseEntity<Restaurante> atualizar(@PathVariable Long restauranteId, @RequestBody Restaurante restaurante){
		Optional<Restaurante> possivelRestaurante = restauranteRepository.findById(restauranteId);
		
		if(possivelRestaurante.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		Restaurante restauranteAtual = possivelRestaurante.get();
		
		BeanUtils.copyProperties(restaurante, restauranteAtual, "id");
		
		cadastroRestaurante.salvar(restauranteAtual);
		
		return ResponseEntity.ok(restauranteAtual);
	}
	
	@DeleteMapping("/{restauranteId}")
	public ResponseEntity<Restaurante> remover(@PathVariable Long restauranteId){
		try {
			
			cadastroRestaurante.remover(restauranteId);
			
			return ResponseEntity.noContent().build();
			
		} catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		} catch (EntidadeNaoEncontradaException en) {
			return ResponseEntity.notFound().build();
		}
	}
	
}
