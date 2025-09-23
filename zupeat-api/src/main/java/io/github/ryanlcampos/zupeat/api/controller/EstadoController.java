package io.github.ryanlcampos.zupeat.api.controller;

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

import io.github.ryanlcampos.zupeat.api.assembler.EstadoMapper;
import io.github.ryanlcampos.zupeat.api.model.EstadoModel;
import io.github.ryanlcampos.zupeat.api.model.input.EstadoInput;
import io.github.ryanlcampos.zupeat.domain.model.Estado;
import io.github.ryanlcampos.zupeat.domain.repository.EstadoRepository;
import io.github.ryanlcampos.zupeat.domain.service.CadastroEstadoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/estados")
public class EstadoController {
	
	@Autowired
	private CadastroEstadoService cadastroEstado;
	
	@Autowired
	private EstadoRepository estadoRepository;

	@Autowired
	private EstadoMapper estadoMapper;
	
	@GetMapping
	public List<EstadoModel> listar(){
		return estadoMapper.toCollectionModel(estadoRepository.findAll());
	}
	
	@GetMapping("/{estadoId}")
	public EstadoModel buscar(@PathVariable Long estadoId){
		return estadoMapper.toModel(cadastroEstado.obterPorId(estadoId));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EstadoModel adicionar(@RequestBody @Valid EstadoInput estadoInput){

		Estado estado = estadoMapper.toDomainObject(estadoInput);

		return estadoMapper.toModel(cadastroEstado.salvar(estado));
	}
	
	@PutMapping("/{estadoId}")
	public EstadoModel atualizar(@PathVariable Long estadoId, @RequestBody @Valid EstadoInput estadoInput){
			
		Estado estadoEncontrado = cadastroEstado.obterPorId(estadoId);

		estadoMapper.copyToDomainObject(estadoInput, estadoEncontrado);

		return estadoMapper.toModel(cadastroEstado.salvar(estadoEncontrado));

	}
	
	@DeleteMapping("/{estadoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long estadoId){
		cadastroEstado.remover(estadoId);
	} 
	
	
}
