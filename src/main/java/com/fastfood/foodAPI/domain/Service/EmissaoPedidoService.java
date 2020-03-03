package com.fastfood.foodAPI.domain.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fastfood.foodAPI.domain.exception.NegocioException;
import com.fastfood.foodAPI.domain.exception.PedidoNaoEncontradoException;
import com.fastfood.foodAPI.domain.model.Cidade;
import com.fastfood.foodAPI.domain.model.FormaPagamento;
import com.fastfood.foodAPI.domain.model.Pedido;
import com.fastfood.foodAPI.domain.model.Produto;
import com.fastfood.foodAPI.domain.model.Restaurante;
import com.fastfood.foodAPI.domain.model.Usuario;
import com.fastfood.foodAPI.domain.repository.PedidoRepository;


@Service
public class EmissaoPedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private CadastroRestauranteService cadastroRestaurante;

	@Autowired
	private CadastroCidadeService cadastroCidade;

	@Autowired
	private CadastroUsuarioService cadastroUsuario;

	@Autowired
	private CadastroProdutoService cadastroProduto;

	@Autowired
	private CadastroFormaPagamentoService cadastroFormaPagamento;

	@Transactional
	public Pedido emitir(Pedido pedido) {
		validarPedido(pedido);
		validarItens(pedido);

		pedido.setTaxa_frete(pedido.getRestaurante().getTaxaFrete());

		pedido.calcularValorTotal();

		return pedidoRepository.save(pedido);
	}

	private void validarPedido(Pedido pedido) {
		Cidade cidade = cadastroCidade.BuscarOuFalhar(pedido.getEnderecoEntrega().getCidade().getId());
		Usuario cliente = cadastroUsuario.BuscarOuFalhar(pedido.getCliente().getId());
		Restaurante restaurante = cadastroRestaurante.BuscarOuFalhar(pedido.getRestaurante().getId());
		FormaPagamento formaPagamento = cadastroFormaPagamento.BuscarOuFalhar(pedido.getFormaPagamento().getId());

		pedido.getEnderecoEntrega().setCidade(cidade);
		pedido.setCliente(cliente);
		pedido.setRestaurante(restaurante);
		pedido.setFormaPagamento(formaPagamento);
		
		if (restaurante.naoAceitaFormaPagamento(formaPagamento)) {
			throw new NegocioException(String.format("Forma de pagamento '%s' não é aceita por esse restaurante.",
					formaPagamento.getDescricao()));
		}
	}

	private void validarItens(Pedido pedido) {
		pedido.getItens().forEach(item -> {
			Produto produto = cadastroProduto.BuscarOuFalhar(
					pedido.getRestaurante().getId(), item.getProduto().getId());
			
			item.setPedido(pedido);
			item.setProduto(produto);
			item.setPreco_unitario(produto.getPreco());
		});
	}
	
	public Pedido buscarOuFalhar(Long pedidoId) {
		return pedidoRepository.findById(pedidoId)
			.orElseThrow(() -> new PedidoNaoEncontradoException(pedidoId));
	}
}
