package com.fastfood.foodAPI.domain.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fastfood.foodAPI.domain.exception.CozinhaNaoEncontradaException;
import com.fastfood.foodAPI.domain.exception.EntidadeEmUsoException;
import com.fastfood.foodAPI.domain.model.Cozinha;
import com.fastfood.foodAPI.domain.repository.CozinhaRepository;

@Service
public class CadastroCozinhaService {

	private static final String MSG_COZINHA_EM_USO = "Cozinha de código %d não pode ser removida, pois está em uso.";
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@Transactional
	public Cozinha Salvar(Cozinha cozinha) {
		return cozinhaRepository.save(cozinha);
	}
	
	@Transactional
	public void Remover(Long cozinhaId) {
		try 
		{
			cozinhaRepository.deleteById(cozinhaId);
			cozinhaRepository.flush();
			
		}catch (EmptyResultDataAccessException e) {
			throw new CozinhaNaoEncontradaException(cozinhaId); 
		} 
		
		catch (DataIntegrityViolationException e) 
		{
			throw new EntidadeEmUsoException(String.format(MSG_COZINHA_EM_USO, cozinhaId));
		}
	
	}
	
	public Cozinha BuscarOuFalhar(Long cozinhaId) {
		return cozinhaRepository.findById(cozinhaId)
				.orElseThrow(() -> new CozinhaNaoEncontradaException(cozinhaId));
	}
	
}
