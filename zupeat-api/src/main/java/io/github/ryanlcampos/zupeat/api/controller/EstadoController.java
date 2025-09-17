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

import io.github.ryanlcampos.zupeat.api.assembler.EstadoInputDisassembler;
import io.github.ryanlcampos.zupeat.api.assembler.EstadoModelAssembler;
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
	private EstadoModelAssembler estadoModelAssembler;

	@Autowired
	private EstadoInputDisassembler estadoInputDisassembler;
	
	@GetMapping
	public List<EstadoModel> listar(){
		return estadoModelAssembler.toCollectionModel(estadoRepository.findAll());
	}
	
	@GetMapping("/{estadoId}")
	public EstadoModel buscar(@PathVariable Long estadoId){
		return estadoModelAssembler.toModel(cadastroEstado.obterPorId(estadoId));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EstadoModel adicionar(@RequestBody @Valid EstadoInput estadoInput){

		Estado estado = estadoInputDisassembler.toDomainObject(estadoInput);

		return estadoModelAssembler.toModel(cadastroEstado.salvar(estado));
	}
	
	@PutMapping("/{estadoId}")
	public EstadoModel atualizar(@PathVariable Long estadoId, @RequestBody @Valid EstadoInput estadoInput){
			
		Estado estadoEncontrado = cadastroEstado.obterPorId(estadoId);

		estadoInputDisassembler.copyToDomainObject(estadoInput, estadoEncontrado);

		return estadoModelAssembler.toModel(cadastroEstado.salvar(estadoEncontrado));

	}
	
	@DeleteMapping("/{estadoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long estadoId){
		cadastroEstado.remover(estadoId);
	} 
	
	
}
