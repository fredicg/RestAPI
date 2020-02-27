package com.fastfood.foodAPI.domain.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fastfood.foodAPI.domain.exception.EntidadeEmUsoException;
import com.fastfood.foodAPI.domain.exception.EstadoNaoEncontradoException;
import com.fastfood.foodAPI.domain.model.Estado;
import com.fastfood.foodAPI.domain.repository.EstadoRepository;

@Service
public class CadastroEstadosService {

	private static final String MSG_ESTADO_EM_USO = "Estado de código %d não pode ser removido, pois está em uso";
	//private static final String MSG_ESTADO_NAO_ENCONTRADA = "Estado de código %d não encontrado";
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Transactional
	public Estado Salvar(Estado estado) {
		
		return estadoRepository.save(estado);
	}
	
	@Transactional
	public void Remover(Long estadoId) {
		try {
			estadoRepository.deleteById(estadoId);
			estadoRepository.flush();
			
		}
		catch (EmptyResultDataAccessException e) {
			throw new EstadoNaoEncontradoException(estadoId);
		}
		catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format(MSG_ESTADO_EM_USO, estadoId));
			}
	}
	
	public Estado BuscarOuFalhar(Long estadoId) {
		return estadoRepository.findById(estadoId)
				.orElseThrow(() -> new EstadoNaoEncontradoException(estadoId));
	}
	
}
