package com.fastfood.foodAPI.domain.Service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fastfood.foodAPI.domain.exception.EntidadeEmUsoException;
import com.fastfood.foodAPI.domain.exception.RestauranteNaoEncontradoException;
import com.fastfood.foodAPI.domain.model.Cidade;
import com.fastfood.foodAPI.domain.model.Cozinha;
import com.fastfood.foodAPI.domain.model.FormaPagamento;
import com.fastfood.foodAPI.domain.model.Restaurante;
import com.fastfood.foodAPI.domain.model.Usuario;
import com.fastfood.foodAPI.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {

	private static final String MSG_RESTAURANTE_EM_USO = "Restaurante de código %d não pode ser removido, pois está em uso.";

	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CadastroCozinhaService cadastroCozinha;
	
	@Autowired
	private CadastroFormaPagamentoService cadastroFormaPagamento;
	
	@Autowired
	private CadastroUsuarioService CadastroUsuario;
	
	@Autowired
	private CadastroCidadeService cadastroCidade;

	
	@Transactional
	public Restaurante Salvar(Restaurante restaurante) {
		
		Long cozinhaId = restaurante.getCozinha().getId();
		Long cidadeId = restaurante.getEndereco().getCidade().getId();
		
		Cozinha cozinha = cadastroCozinha.BuscarOuFalhar(cozinhaId);
		Cidade cidade = cadastroCidade.BuscarOuFalhar(cidadeId);

		restaurante.setCozinha(cozinha);
		restaurante.getEndereco().setCidade(cidade);
		
		return restauranteRepository.save(restaurante);
	}
	
	@Transactional
	public void Remover(Long restauranteId) {
		try 
		{
			restauranteRepository.deleteById(restauranteId);
			restauranteRepository.flush();
			
		}catch (EmptyResultDataAccessException e) {
			throw new RestauranteNaoEncontradoException(restauranteId); 
		} 
		
		catch (DataIntegrityViolationException e) 
		{
			throw new EntidadeEmUsoException(String.format(MSG_RESTAURANTE_EM_USO, restauranteId));
		}
	
	}
	
	public void ativar(Long restauranteId) {
		
		Restaurante restauranteAtual = BuscarOuFalhar(restauranteId);
		
		restauranteAtual.ativar();
	}
	
	public void inativar(Long restauranteId) {
		
		Restaurante restauranteAtual = BuscarOuFalhar(restauranteId);
		
		restauranteAtual.inativar();
	}
	

	@Transactional
	public void ativar(List<Long> restauranteId) {
		restauranteId.forEach(this::ativar);
	}
	
	@Transactional
	public void inativar(List<Long> restauranteId) {
		restauranteId.forEach(this::inativar);
	}

	
	@Transactional
	public void abrir(Long restauranteId) {
		Restaurante restauranteAtual = BuscarOuFalhar(restauranteId);
		
		restauranteAtual.abrir();
	}
	
	@Transactional
	public void fechar(Long restauranteId) {
		Restaurante restauranteAtual = BuscarOuFalhar(restauranteId);
		
		restauranteAtual.fechar();
	}
	
	@Transactional
	public void desassociarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
		
		//O próprio jpa faz a atualização automaticamente, não precisa chamar o save
		Restaurante restaurante = BuscarOuFalhar(restauranteId);
		FormaPagamento formaPagamento = cadastroFormaPagamento.BuscarOuFalhar(formaPagamentoId);
		
		restaurante.desassociarFormaPagamento(formaPagamento);
	}

	@Transactional
	public void adicionarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
		
		//O próprio jpa faz a atualização automaticamente, não precisa chamar o save
		Restaurante restaurante = BuscarOuFalhar(restauranteId);
		FormaPagamento formaPagamento = cadastroFormaPagamento.BuscarOuFalhar(formaPagamentoId);
		
		restaurante.adicionarFormaPagamento(formaPagamento);
	}
	
	@Transactional
	public void adicionarResponsavel(Long restauranteId, Long usuarioId) {
		
		//O próprio jpa faz a atualização automaticamente, não precisa chamar o save
		Restaurante restaurante = BuscarOuFalhar(restauranteId);
		Usuario usuario = CadastroUsuario.BuscarOuFalhar(usuarioId);
		
		restaurante.adicionarResponsavel(usuario);
	}
	
	@Transactional
	public void removerResponsavel(Long restauranteId, Long usuarioId) {
		
		//O próprio jpa faz a atualização automaticamente, não precisa chamar o save
		Restaurante restaurante = BuscarOuFalhar(restauranteId);
		Usuario usuario = CadastroUsuario.BuscarOuFalhar(usuarioId);
		
		restaurante.removerResponsavel(usuario);
	}
	
	
	public Restaurante BuscarOuFalhar(Long restauranteId) {
		return restauranteRepository.findById(restauranteId)
				.orElseThrow(() -> new RestauranteNaoEncontradoException(restauranteId));
	}
	
}
