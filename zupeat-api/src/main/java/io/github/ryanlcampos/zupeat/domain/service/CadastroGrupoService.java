package io.github.ryanlcampos.zupeat.domain.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.ryanlcampos.zupeat.domain.exceptions.EntidadeEmUsoException;
import io.github.ryanlcampos.zupeat.domain.exceptions.GrupoNaoEncontradoException;
import io.github.ryanlcampos.zupeat.domain.model.Grupo;
import io.github.ryanlcampos.zupeat.domain.model.Permissao;
import io.github.ryanlcampos.zupeat.domain.repository.GrupoRepository;

@Service
public class CadastroGrupoService {

    private static final String MSG_GRUPO_EM_USO = "Grupo de código %d não pode ser removido, pois está em uso.";
    
    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private CadastroPermissaoService cadastroPermissao;

    @Transactional
    public Grupo salvar(Grupo grupo) {
        return grupoRepository.save(grupo);
    }

    @Transactional
    public void remover(Long grupoId) {
        try {
            grupoRepository.deleteById(grupoId);
            grupoRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new GrupoNaoEncontradoException(grupoId);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                String.format(MSG_GRUPO_EM_USO, grupoId));
        }
    }

    public Grupo obterPorId(Long grupoId) {
        return grupoRepository.findById(grupoId)
                .orElseThrow(() -> new GrupoNaoEncontradoException(grupoId));
    }

    @Transactional
    public Set<Permissao> associarPermissoes(Long grupoId, Long permissaoId) {
        Grupo grupo = obterPorId(grupoId);

        Permissao permissao = cadastroPermissao.obterPorId(permissaoId);

        grupo.adicionarPermissao(permissao);

        return grupo.getPermissoes();
    }

    @Transactional
    public void desassociarPermissoes(Long grupoId, Long permissaoId) {
        Grupo grupo = obterPorId(grupoId);

        Permissao permissao = cadastroPermissao.obterPorId(permissaoId);

        grupo.removerPermissao(permissao);
    }

}
