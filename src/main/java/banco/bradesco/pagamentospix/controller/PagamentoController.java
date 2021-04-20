package banco.bradesco.pagamentospix.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import banco.bradesco.pagamentospix.domain.Pagamento;
import banco.bradesco.pagamentospix.domain.Pessoa;
import banco.bradesco.pagamentospix.exception.ResourceNotFoundException;
import banco.bradesco.pagamentospix.repository.PagamentoRepository;
import banco.bradesco.pagamentospix.repository.PessoaRepository;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class PagamentoController {

	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	/* Listar todos os Pagamentos */
	@GetMapping("/pagamentos")
	public List<Pagamento> listarTodos() {
		return pagamentoRepository.findAll();
	}
	
	/* Salvar Pagamento para Usuário */
	@PostMapping("/pagamentos/{idUsuario}")
	public Pagamento salvarPagamento(@PathVariable Long idUsuario, @RequestBody Pagamento pagamentoDetails) {
		
		Pessoa pessoa = pessoaRepository.findById(idUsuario)
				.orElseThrow(() -> new ResourceNotFoundException("[salvarPagamento] Não existe uma pessoa com o id: " + idUsuario));

		pagamentoDetails.setPessoa(pessoa);
		return pagamentoRepository.save(pagamentoDetails);
	}
	
	/* Buscar Pagamentos por Usuário */
	@GetMapping("/pagamentos/por-usuario/{idUsuario}")
	public List<Pagamento> buscarPagamentoPorUsuario(@PathVariable Long idUsuario) {
		return pagamentoRepository.findPagamentoByUsuario(idUsuario);
	}	
	
	/* Buscar Pagamento por id */
	@GetMapping("/pagamentos/{id}")
	public ResponseEntity<Pagamento> buscarPagamentoPorId(@PathVariable Long id) {
		Pagamento pagamento = pagamentoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Não existe um pagamento com o id: " + id));
		return ResponseEntity.ok(pagamento);
	}
	
	/* Atualizar Pagamento para Usuário */
	@PutMapping("/pagamentos/{idPagamento}/{idUsuario}")
	public ResponseEntity<Pagamento> atualizarPagamento(@PathVariable Long idPagamento, @PathVariable Long idUsuario, @RequestBody Pagamento pagamentoDetails) {
		
		Pessoa pessoa = pessoaRepository.findById(idUsuario)
				.orElseThrow(() -> new ResourceNotFoundException("[atualizarPagamento] Não existe uma pessoa com o id: " + idUsuario));		
		
		Pagamento pagamento = pagamentoRepository.findById(idPagamento)
				.orElseThrow(() -> new ResourceNotFoundException("[atualizarPagamento] Não existe um pagamento com o id: " + idPagamento));
		
		pagamento.setChavePix(pagamentoDetails.getChavePix());
		pagamento.setCpf(pagamentoDetails.getCpf());
		pagamento.setDataPagamento(pagamentoDetails.getDataPagamento());
		pagamento.setDescricao(pagamentoDetails.getDescricao());
		pagamento.setNomeDestinatario(pagamentoDetails.getNomeDestinatario());
		pagamento.setValor(pagamentoDetails.getValor());
		pagamento.setPessoa(pessoa);
		
		Pagamento pagamentoUpdate = pagamentoRepository.save(pagamento);
		return ResponseEntity.ok(pagamentoUpdate);
	}
	
	/* Deletar Pagamento do Usuário */
	@DeleteMapping("/pagamentos/{id}")
	public ResponseEntity<Map<String, Boolean>> deletarPagamento(@PathVariable Long id) {
		Pagamento pagamento = pagamentoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("[deletarPagamento] Não existe um pagamento com o id: " + id));
		
		pagamentoRepository.delete(pagamento);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}	
	
}
