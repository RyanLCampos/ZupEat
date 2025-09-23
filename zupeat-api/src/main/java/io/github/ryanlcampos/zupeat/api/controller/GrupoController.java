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

import io.github.ryanlcampos.zupeat.api.assembler.GrupoMapper;
import io.github.ryanlcampos.zupeat.api.model.GrupoModel;
import io.github.ryanlcampos.zupeat.api.model.input.GrupoInput;
import io.github.ryanlcampos.zupeat.domain.model.Grupo;
import io.github.ryanlcampos.zupeat.domain.repository.GrupoRepository;
import io.github.ryanlcampos.zupeat.domain.service.CadastroGrupoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/grupos")
public class GrupoController {

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private CadastroGrupoService cadastroGrupo;

    @Autowired
    private GrupoMapper grupoMapper;

    @GetMapping
    public List<GrupoModel> listar() {
        List<Grupo> grupos = grupoRepository.findAll();

        return grupoMapper.toCollectionModel(grupos);
    }

    @GetMapping("/{grupoId}")
    public GrupoModel buscar(@PathVariable Long grupoId) {
        Grupo grupo = cadastroGrupo.obterPorId(grupoId);

        return grupoMapper.toModel(grupo);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GrupoModel adicionar(@RequestBody @Valid GrupoInput grupoInput) {
        Grupo grupo = grupoMapper.toDomainObject(grupoInput);

        grupo = cadastroGrupo.salvar(grupo);

        return grupoMapper.toModel(grupo);
    }

    @PutMapping("/{grupoId}")
    public GrupoModel atualizar(@PathVariable Long grupoId,
            @RequestBody @Valid GrupoInput grupoInput) {
        
        Grupo grupoAtual = cadastroGrupo.obterPorId(grupoId);

        grupoMapper.copyToDomainObject(grupoInput, grupoAtual);

        grupoAtual = cadastroGrupo.salvar(grupoAtual);

        return grupoMapper.toModel(grupoAtual);
    }

    @DeleteMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long grupoId){
        cadastroGrupo.excluir(grupoId);
    }
}
