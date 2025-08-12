package io.github.ryanlcampos.zupeat.domain.service;


import io.github.ryanlcampos.zupeat.domain.exceptions.CidadeNaoEncontradoException;
import io.github.ryanlcampos.zupeat.domain.exceptions.EntidadeEmUsoException;
import io.github.ryanlcampos.zupeat.domain.model.Cidade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import io.github.ryanlcampos.zupeat.domain.model.Estado;
import io.github.ryanlcampos.zupeat.domain.repository.CidadeRepository;

@Service
public class CadastroCidadeService {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private CadastroEstadoService cadastroEstado;


    public Cidade obterPorId(Long cidadeId){
        return cidadeRepository.findById(cidadeId)
                .orElseThrow(() -> new CidadeNaoEncontradoException(cidadeId));
    }


    public Cidade salvar(Cidade cidade){
        Long estadoId = cidade.getEstado().getId();

        Estado estado = cadastroEstado.obterPorId(estadoId);

        cidade.setEstado(estado);

        return cidadeRepository.save(cidade);
    }

    public void remover(Long cidadeId){
        try {
            obterPorId(cidadeId);

            cidadeRepository.deleteById(cidadeId);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format("Cidade com codigo %d não pode ser removido, pois está em uso", cidadeId));
        }

    }

}
