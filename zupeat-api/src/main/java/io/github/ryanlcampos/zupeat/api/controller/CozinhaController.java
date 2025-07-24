package io.github.ryanlcampos.zupeat.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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

import io.github.ryanlcampos.zupeat.domain.model.Cozinha;
import io.github.ryanlcampos.zupeat.domain.repository.CozinhaRepository;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@GetMapping
	public List<Cozinha> listar(){
		return cozinhaRepository.findAll();
	}

	@GetMapping("/{cozinhaId}")
	public ResponseEntity<Cozinha> buscar(@PathVariable("cozinhaId") Long id) {
		Optional<Cozinha> possivelCozinha = cozinhaRepository.findById(id);
		
		if(possivelCozinha.isPresent()) {
			
			Cozinha cozinhaEncontrada = possivelCozinha.get();
			
			return ResponseEntity.ok(cozinhaEncontrada);
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cozinha adicionar(@RequestBody Cozinha cozinha) {
		return cozinhaRepository.save(cozinha);
	}
	
	@PutMapping("/{cozinhaId}")
	public ResponseEntity<Cozinha> atualizar(@PathVariable Long cozinhaId, @RequestBody Cozinha cozinha) {
		Optional<Cozinha> possivelCozinha = cozinhaRepository.findById(cozinhaId);
		
		if(possivelCozinha.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		Cozinha cozinhaAtual = possivelCozinha.get();
		
		BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");
		
		cozinhaRepository.save(cozinhaAtual);
		
		return ResponseEntity.ok(cozinhaAtual);
	}
	
	@DeleteMapping("/{cozinhaId}")
	public ResponseEntity<Cozinha> remover(@PathVariable Long cozinhaId){	
		try {
			
			Optional<Cozinha> possivelCozinha = cozinhaRepository.findById(cozinhaId);
			
			if(possivelCozinha.isEmpty()) {
				return ResponseEntity.notFound().build();
			}
			
			Cozinha cozinhaEncontrada = possivelCozinha.get();
			
			cozinhaRepository.delete(cozinhaEncontrada);
			
			return ResponseEntity.noContent().build();
			
		}catch (DataIntegrityViolationException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
		
	}
	
	
}
