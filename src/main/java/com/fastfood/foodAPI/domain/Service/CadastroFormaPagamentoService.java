package com.fastfood.foodAPI.domain.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fastfood.foodAPI.domain.exception.EntidadeEmUsoException;
import com.fastfood.foodAPI.domain.exception.FormaPagamentoNaoEncontradoException;
import com.fastfood.foodAPI.domain.model.FormaPagamento;
import com.fastfood.foodAPI.domain.repository.FormaPagamentoRepository;


@Service
public class CadastroFormaPagamentoService {

	//private static final String MSG_ESTADO_NAO_ENCONTRADO = "Não existe cadastro de estado com o código %d";
	
	private static final String MSG_FORMA_PAGAMENTO_EM_USO = "Forma de pagemento de código %d não pode ser removida, pois está em uso.";

	@Autowired
	private FormaPagamentoRepository formaPagamentoRepository;
	
	@Transactional
	public FormaPagamento Salvar(FormaPagamento formaPagamento) {
		
		return formaPagamentoRepository.save(formaPagamento);
	}
	
	@Transactional
	public void Remover(Long formaPagamentoId) {
		try {
			formaPagamentoRepository.deleteById(formaPagamentoId);
			formaPagamentoRepository.flush();
			
		}catch (EmptyResultDataAccessException e) {
			throw new FormaPagamentoNaoEncontradoException(formaPagamentoId);
		}catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_FORMA_PAGAMENTO_EM_USO, formaPagamentoId));
		}
	}
	
	
	public FormaPagamento BuscarOuFalhar(Long formaPagamentoId) {
		return formaPagamentoRepository.findById(formaPagamentoId)
				.orElseThrow(() -> new FormaPagamentoNaoEncontradoException(formaPagamentoId));
				
	}
}
