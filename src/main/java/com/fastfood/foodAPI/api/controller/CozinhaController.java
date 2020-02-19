package com.fastfood.foodAPI.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fastfood.foodAPI.domain.Service.CadastroCozinhaService;
import com.fastfood.foodAPI.domain.model.Cozinha;
import com.fastfood.foodAPI.domain.repository.CozinhaRepository;

@RestController
@RequestMapping(value = "/cozinhas", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_ATOM_XML_VALUE})
public class CozinhaController {
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@Autowired 
	private CadastroCozinhaService cadastroCozinha;
	
	@GetMapping
	public List<Cozinha> listar(){
		
		return cozinhaRepository.findAll();
	}

	@GetMapping("/{cozinhaId}")
	public Cozinha Buscar(@PathVariable Long cozinhaId) {
	
		return cadastroCozinha.BuscarOuFalhar(cozinhaId);
		
		//Optional <Cozinha> cozinha = cozinhaRepository.findById(cozinhaId);
		
		//HttpHeaders headers = new HttpHeaders();
		//headers.add(HttpHeaders.LOCATION, "Local para onde a url foi movida ex: http://api.foodapi.food/cozinhas");
		//return ResponseEntity.status(HttpStatus.FOUND).headers(headers).build();
		/*
		 * if (cozinha.isPresent()) { return ResponseEntity.ok(cozinha.get()); } return
		 * ResponseEntity.notFound().build();
		 */
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cozinha Adicionar(@RequestBody @Valid Cozinha cozinha) {
		
		return cadastroCozinha.Salvar(cozinha);
	}
	
	
	@PutMapping("/{cozinhaId}")
	public Cozinha Atualizar(@PathVariable Long cozinhaId, @RequestBody Cozinha cozinha) {
		
		Cozinha cozinhaAtual = cadastroCozinha.BuscarOuFalhar(cozinhaId);
				

			//terceiro paramentro são propriedade que deseja ignorar 
			BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");
			
			return cadastroCozinha.Salvar(cozinhaAtual);

	}
	
	/*
	 * @DeleteMapping("/{cozinhaId}") public ResponseEntity<Cozinha>
	 * Remover( @PathVariable Long cozinhaId) {
	 * 
	 * try { cadastroCozinha.Remover(cozinhaId); return
	 * ResponseEntity.noContent().build();
	 * 
	 * }catch (EntidadeNaoEncontradaException e) { return
	 * ResponseEntity.notFound().build(); }
	 * 
	 * catch (EntidadeEmUsoException e) { return
	 * ResponseEntity.status(HttpStatus.CONFLICT).build(); }
	 * 
	 * }
	 */
	
	@DeleteMapping("/{cozinhaId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void Remover( @PathVariable Long cozinhaId) {

		cadastroCozinha.Remover(cozinhaId);
		
	}

}
