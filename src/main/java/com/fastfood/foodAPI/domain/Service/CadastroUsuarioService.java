package com.fastfood.foodAPI.domain.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fastfood.foodAPI.domain.exception.NegocioException;
import com.fastfood.foodAPI.domain.exception.UsuarioNaoEncontradoException;
import com.fastfood.foodAPI.domain.model.Grupo;
import com.fastfood.foodAPI.domain.model.Usuario;
import com.fastfood.foodAPI.domain.repository.UsuarioRepository;


@Service
public class CadastroUsuarioService {

	//private static final String MSG_USUARIO_EM_USO = "Usuário de código %d não pode ser removido, pois está em uso.";

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private CadastroGrupoService cadastroGrupo;
	
	@Transactional
	public Usuario Salvar(Usuario usuario) {
		
		usuarioRepository.detach(usuario);
		
		Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(usuario.getEmail());
		
		if(usuarioExistente.isPresent() && !usuarioExistente.get().equals(usuario)) {
			throw new NegocioException(
					String.format("Já existe um usuário com o e-mail %s", usuario.getEmail()));
		}
		
		return usuarioRepository.save(usuario);
	}
	
	@Transactional
	public void alterarSenha(Long usuarioId, String senhaAtual, String novaSenha) {
		Usuario usuario = BuscarOuFalhar(usuarioId);
		
		if (usuario.senhaNaoCoincideCom(senhaAtual)) {
			throw new NegocioException("Senha atual informada não coincide com a senha do usuário.");
		}
		
		usuario.setSenha(novaSenha);
	}

	@Transactional
	public void desassociarGrupo(Long usuarioId, Long grupoId) {
		Usuario usuario = BuscarOuFalhar(usuarioId);
		Grupo grupo = cadastroGrupo.BuscarOuFalhar(grupoId);
		
		usuario.removerGrupo(grupo);
	}
	
	@Transactional
	public void associarGrupo(Long usuarioId, Long grupoId) {
		Usuario usuario = BuscarOuFalhar(usuarioId);
		Grupo grupo = cadastroGrupo.BuscarOuFalhar(grupoId);
		
		usuario.adicionarGrupo(grupo);
	}
	
	public Usuario BuscarOuFalhar(Long usuarioId) {
		return usuarioRepository.findById(usuarioId)
			.orElseThrow(() -> new UsuarioNaoEncontradoException(usuarioId));
	}
}
