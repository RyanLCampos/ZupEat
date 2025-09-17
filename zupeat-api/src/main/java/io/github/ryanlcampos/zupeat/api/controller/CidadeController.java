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

import io.github.ryanlcampos.zupeat.api.assembler.CidadeInputDisassembler;
import io.github.ryanlcampos.zupeat.api.assembler.CidadeModelAssembler;
import io.github.ryanlcampos.zupeat.api.model.CidadeModel;
import io.github.ryanlcampos.zupeat.api.model.input.CidadeInput;
import io.github.ryanlcampos.zupeat.domain.exceptions.EstadoNaoEncontradoException;
import io.github.ryanlcampos.zupeat.domain.exceptions.NegocioException;
import io.github.ryanlcampos.zupeat.domain.model.Cidade;
import io.github.ryanlcampos.zupeat.domain.repository.CidadeRepository;
import io.github.ryanlcampos.zupeat.domain.service.CadastroCidadeService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/cidades")
public class CidadeController {

    @Autowired
    private CadastroCidadeService cadastroCidade;

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private CidadeModelAssembler cidadeModelAssembler;

    @Autowired
    private CidadeInputDisassembler cidadeInputDisassembler;

    @GetMapping
    public List<CidadeModel> listar(){
        return cidadeModelAssembler.toCollectionModel(cidadeRepository.findAll());
    }

    @GetMapping("/{cidadeId}")
    public CidadeModel buscar(@PathVariable Long cidadeId){
        return cidadeModelAssembler.toModel(cadastroCidade.obterPorId(cidadeId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CidadeModel adicionar(@RequestBody @Valid CidadeInput cidadeInput){
        try {

            Cidade cidade = cidadeInputDisassembler.toDomainObject(cidadeInput);

            return cidadeModelAssembler.toModel(cadastroCidade.salvar(cidade));
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @PutMapping("/{cidadeId}")
    public CidadeModel atualizar(@PathVariable Long cidadeId, 
    		@RequestBody @Valid CidadeInput cidadeInput){
        try {
            Cidade cidadeAtual = cadastroCidade.obterPorId(cidadeId);
            
            cidadeInputDisassembler.copyToDomainObject(cidadeInput, cidadeAtual);

            return cidadeModelAssembler.toModel(cadastroCidade.salvar(cidadeAtual));
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @DeleteMapping("/{cidadeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long cidadeId){
        cadastroCidade.remover(cidadeId);
    }

}
