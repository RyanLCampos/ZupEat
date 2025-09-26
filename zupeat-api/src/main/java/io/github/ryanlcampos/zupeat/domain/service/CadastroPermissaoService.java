package io.github.ryanlcampos.zupeat.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.ryanlcampos.zupeat.domain.exceptions.EntidadeEmUsoException;
import io.github.ryanlcampos.zupeat.domain.exceptions.PermissaoNaoEncontradaException;
import io.github.ryanlcampos.zupeat.domain.model.Permissao;
import io.github.ryanlcampos.zupeat.domain.repository.PermissaoRepository;

@Service
public class CadastroPermissaoService {

    private static final String MSG_PERMISSAO_EM_USO
            = "Permissão de código %d não pode ser removida, pois está em uso";

    @Autowired
    private PermissaoRepository permissaoRepository;

    @Transactional
    public Permissao salvar(Permissao permissao) {
        return permissaoRepository.save(permissao);
    }

    @Transactional
    public void remover(Long permissaoId) {
        try {
            permissaoRepository.deleteById(permissaoId);
            permissaoRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new PermissaoNaoEncontradaException(permissaoId);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(MSG_PERMISSAO_EM_USO, permissaoId));
        }
    }

    public Permissao obterPorId(Long permissaoId) {
        return permissaoRepository.findById(permissaoId)
                .orElseThrow(() -> new PermissaoNaoEncontradaException(permissaoId));
    }

}
