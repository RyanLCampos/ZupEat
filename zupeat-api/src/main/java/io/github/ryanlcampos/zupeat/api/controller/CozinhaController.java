package io.github.ryanlcampos.zupeat.api.controller;

import java.util.List;
import java.util.Optional;

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
import io.github.ryanlcampos.zupeat.domain.model.Cozinha;
import io.github.ryanlcampos.zupeat.domain.repository.CozinhaRepository;
import io.github.ryanlcampos.zupeat.domain.service.CadastroCozinhaService;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@Autowired
	private CadastroCozinhaService cadastroCozinha;
	
	@GetMapping
	public List<Cozinha> listar(){
		return cadastroCozinha.listar();
	}

	@GetMapping("/{cozinhaId}")
	public ResponseEntity<Cozinha> buscar(@PathVariable("cozinhaId") Long id) {
		Optional<Cozinha> possivelCozinha = cozinhaRepository.findById(id);
		
		if(possivelCozinha.isEmpty()) {
			
			return ResponseEntity.notFound().build();
		}
		
		Cozinha cozinhaEncontrada = possivelCozinha.get();
		
		return ResponseEntity.ok(cozinhaEncontrada);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cozinha adicionar(@RequestBody Cozinha cozinha) {
		return cadastroCozinha.salvar(cozinha);
	}
	
	@PutMapping("/{cozinhaId}")
	public ResponseEntity<Cozinha> atualizar(@PathVariable Long cozinhaId, @RequestBody Cozinha cozinha) {
		Optional<Cozinha> possivelCozinha = cozinhaRepository.findById(cozinhaId);
		
		if(possivelCozinha.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		Cozinha cozinhaAtual = possivelCozinha.get();
		
		BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");
		
		cadastroCozinha.salvar(cozinhaAtual);
		
		return ResponseEntity.ok(cozinhaAtual);
	}
	
	@DeleteMapping("/{cozinhaId}")
	public ResponseEntity<Cozinha> remover(@PathVariable Long cozinhaId){	
		try {
			
			cadastroCozinha.remover(cozinhaId);
			
			return ResponseEntity.noContent().build();
			
		}catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
			
		}catch(EntidadeNaoEncontradaException en) {
			return ResponseEntity.notFound().build();
		}
		
	}
	
	
}
