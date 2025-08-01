package io.github.ryanlcampos.zupeat.domain.service;


import io.github.ryanlcampos.zupeat.domain.exceptions.EntidadeEmUsoException;
import io.github.ryanlcampos.zupeat.domain.exceptions.EntidadeNaoEncontradaException;
import io.github.ryanlcampos.zupeat.domain.model.Cidade;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import io.github.ryanlcampos.zupeat.domain.model.Estado;
import io.github.ryanlcampos.zupeat.domain.repository.CidadeRepository;
import io.github.ryanlcampos.zupeat.domain.repository.EstadoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CadastroCidadeService {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private EstadoRepository estadoRepository;

    public List<Cidade> listar(){
        return cidadeRepository.findAll();
    }

    public Cidade obterPorId(Long cidadeId){
        Optional<Cidade> possivelcidade = cidadeRepository.findById(cidadeId);

        if(possivelcidade.isEmpty()){
            throw new EntidadeNaoEncontradaException(String.format("Cidade de código %d não foi encontrado.", cidadeId));
        }

        return possivelcidade.get();
    }


    public Cidade salvar(Cidade cidade){

        Long estadoId = cidade.getEstado().getId();

        Optional<Estado> possivelEstado = estadoRepository.findById(estadoId);

        if(possivelEstado.isEmpty()){
            throw new EntidadeNaoEncontradaException(String.format("Estado de codigo %d não foi encontrado", estadoId));
        }

        return cidadeRepository.save(cidade);
    }

    public Cidade atualizar(Long cidadeId, Cidade cidade){

        Cidade cidadeAtual = obterPorId(cidadeId);

        BeanUtils.copyProperties(cidade, cidadeAtual, "id");

        cidadeAtual = salvar(cidadeAtual);

        return cidadeAtual;
    }

    public void remover(Long cidadeId){

        obterPorId(cidadeId);

        cidadeRepository.deleteById(cidadeId);

    }

}
