package com.fastfood.foodAPI.api.controller;

import java.util.List;

import javax.validation.Valid;

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

import com.fastfood.foodAPI.api.assembler.FormaPagamentoInputDisassembler;
import com.fastfood.foodAPI.api.assembler.FormaPagamentoModelAssembler;
import com.fastfood.foodAPI.api.model.FormaPagamentoDTO;
import com.fastfood.foodAPI.api.model.input.FormaPagamentoInput;
import com.fastfood.foodAPI.domain.Service.CadastroFormaPagamentoService;
import com.fastfood.foodAPI.domain.exception.EstadoNaoEncontradoException;
import com.fastfood.foodAPI.domain.exception.NegocioException;
import com.fastfood.foodAPI.domain.model.FormaPagamento;
import com.fastfood.foodAPI.domain.repository.FormaPagamentoRepository;

@RestController
@RequestMapping("/FormasPagamentos")
public class FormaPagamentoController {

	@Autowired
	private FormaPagamentoRepository formaPagamentoRepository;

	@Autowired
	private CadastroFormaPagamentoService cadastroFormaPagamento;

	@Autowired
	private FormaPagamentoModelAssembler formaPagamentoModelAssembler;

	@Autowired
	public FormaPagamentoInputDisassembler formaPagamentoInputDisassembler;

	@GetMapping
	public List<FormaPagamentoDTO> Listar() {

		List<FormaPagamento> formasPagamento = formaPagamentoRepository.findAll();

		return formaPagamentoModelAssembler.toCollectionModel(formasPagamento);

	}

	@GetMapping("/{formaPagamentoId}")
	public FormaPagamentoDTO Buscar(@PathVariable Long formaPagamentoId) {

		FormaPagamento formaPagamento = cadastroFormaPagamento.BuscarOuFalhar(formaPagamentoId);

		return formaPagamentoModelAssembler.toModel(formaPagamento);

		/*
		 * if (cidade.isPresent()) { return ResponseEntity.ok(cidade.get()); }
		 * 
		 * return ResponseEntity.notFound().build();
		 */
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public FormaPagamentoDTO Adicionar(@RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {
		try {
			FormaPagamento formaPagamento = formaPagamentoInputDisassembler.toDomainObject(formaPagamentoInput);
			formaPagamento = cadastroFormaPagamento.Salvar(formaPagamento);
			return formaPagamentoModelAssembler.toModel(formaPagamento);

		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}

	}

	@PutMapping("/{formaPagamentoId}")
	public FormaPagamentoDTO Atualizar(@PathVariable Long formaPagamentoId, @RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {

		try {
			FormaPagamento formaPagamentoAtual = cadastroFormaPagamento.BuscarOuFalhar(formaPagamentoId);

			formaPagamentoInputDisassembler.copyToDomainObject(formaPagamentoInput, formaPagamentoAtual);

			formaPagamentoAtual = cadastroFormaPagamento.Salvar(formaPagamentoAtual);

			return formaPagamentoModelAssembler.toModel(formaPagamentoAtual);
			
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}

	}

	@DeleteMapping("/{formaPagamentoI}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void Remover(@PathVariable Long formaPagamentoId) {

		cadastroFormaPagamento.Remover(formaPagamentoId);

		/*
		 * try { cadastroCidade.Remover(estadoId); return
		 * ResponseEntity.noContent().build(); } catch (EntidadeNaoEncontradaException
		 * e) { return ResponseEntity.notFound().build(); }
		 */
	}

}
