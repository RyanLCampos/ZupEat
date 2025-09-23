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

import io.github.ryanlcampos.zupeat.api.assembler.UsuarioMapper;
import io.github.ryanlcampos.zupeat.api.model.UsuarioModel;
import io.github.ryanlcampos.zupeat.api.model.input.SenhaInput;
import io.github.ryanlcampos.zupeat.api.model.input.UsuarioComSenhaInput;
import io.github.ryanlcampos.zupeat.api.model.input.UsuarioInput;
import io.github.ryanlcampos.zupeat.domain.model.Usuario;
import io.github.ryanlcampos.zupeat.domain.repository.UsuarioRepository;
import io.github.ryanlcampos.zupeat.domain.service.CadastroUsuarioService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CadastroUsuarioService cadastroUsuario;

    @Autowired
    private UsuarioMapper usuarioMapper;

    @GetMapping
    public List<UsuarioModel> listar() {
        List<Usuario> usuarios = usuarioRepository.findAll();

        return usuarioMapper.toCollectionModel(usuarios);
    }

    @GetMapping("/{usuarioId}")
    public UsuarioModel buscar(@PathVariable Long usuarioId) {
        Usuario usuario = cadastroUsuario.obterPorId(usuarioId);

        return usuarioMapper.toModel(usuario);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioModel adicionar(@RequestBody @Valid UsuarioComSenhaInput usuarioInput) {
        Usuario usuario = usuarioMapper.toDomainObject(usuarioInput);

        usuario = cadastroUsuario.salvar(usuario);

        return usuarioMapper.toModel(usuario);
    }

    @PutMapping("/{usuarioId}")
    public UsuarioModel atualizar(@PathVariable Long usuarioId,
            @RequestBody @Valid UsuarioInput usuarioInput) {
                
        Usuario usuarioAtual = cadastroUsuario.obterPorId(usuarioId);

        usuarioMapper.copyToDomainObject(usuarioInput, usuarioAtual);

        usuarioAtual = cadastroUsuario.salvar(usuarioAtual);

        return usuarioMapper.toModel(usuarioAtual);
    }

    @PutMapping("/{usuarioId}/senha")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizarSenha(@PathVariable Long usuarioId,
            @RequestBody @Valid SenhaInput senhaInput) {
        cadastroUsuario.alterarSenha(usuarioId, senhaInput.getSenha(), senhaInput.getNovaSenha());
    }
    
    @DeleteMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long usuarioId) {
        cadastroUsuario.excluir(usuarioId);
    }
}
