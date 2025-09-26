package io.github.ryanlcampos.zupeat.api.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.github.ryanlcampos.zupeat.api.assembler.PermissaoMapper;
import io.github.ryanlcampos.zupeat.api.model.PermissaoModel;
import io.github.ryanlcampos.zupeat.domain.model.Grupo;
import io.github.ryanlcampos.zupeat.domain.model.Permissao;
import io.github.ryanlcampos.zupeat.domain.service.CadastroGrupoService;

@RestController
@RequestMapping("grupos/{grupoId}/permissoes")
public class GrupoPermissaoController {

    @Autowired
    private CadastroGrupoService cadastroGrupo;

    @Autowired
    private PermissaoMapper permissaoMapper;

    @GetMapping
    public List<PermissaoModel> listar(@PathVariable Long grupoId) {
        Grupo grupo = cadastroGrupo.obterPorId(grupoId);

        return permissaoMapper.toCollectionModel(grupo.getPermissoes());
    }

    @DeleteMapping("/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
        cadastroGrupo.desassociarPermissoes(grupoId, permissaoId);
    }

    @PutMapping("/{permissaoId}")
    public List<PermissaoModel> associar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
        Set<Permissao> permissoes = cadastroGrupo.associarPermissoes(grupoId, permissaoId);

        return permissaoMapper.toCollectionModel(permissoes);
    }
}
