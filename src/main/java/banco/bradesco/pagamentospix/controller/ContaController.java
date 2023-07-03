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

import banco.bradesco.pagamentospix.domain.Conta;
import banco.bradesco.pagamentospix.domain.Pessoa;
import banco.bradesco.pagamentospix.exception.ResourceNotFoundException;
import banco.bradesco.pagamentospix.repository.ContaRepository;
import banco.bradesco.pagamentospix.repository.PessoaRepository;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class ContaController {

	@Autowired
	private ContaRepository contaRepository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	/* Listar todas as Contas */
	@GetMapping("/contas")
	public List<Conta> listarTodos() {
		return contaRepository.findAll();
	}
	
	/* Salvar Conta para Usuário */
	@PostMapping("/contas/{idUsuario}")
	public Conta salvarConta(@PathVariable Long idUsuario, @RequestBody Conta contaDetails) {
		
		Pessoa pessoa = pessoaRepository.findById(idUsuario)
				.orElseThrow(() -> new ResourceNotFoundException("[salvarConta] Não existe uma pessoa com o id: " + idUsuario));

		contaDetails.setPessoa(pessoa);
		return contaRepository.save(contaDetails);
	}
	
	/* Buscar Contas por Usuário */
	@GetMapping("/contas/por-usuario/{idUsuario}")
	public List<Conta> buscarContaPorUsuario(@PathVariable Long idUsuario) {
		return contaRepository.findContaByUsuario(idUsuario);
	}	
	
	/* Buscar Conta por id */
	@GetMapping("/contas/{id}")
	public ResponseEntity<Conta> buscarContaPorId(@PathVariable Long id) {
		Conta conta = contaRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Não existe uma conta com o id: " + id));
		return ResponseEntity.ok(conta);
	}
	
	/* Atualizar Conta para Usuário */
	@PutMapping("/contas/{idConta}/{idUsuario}")
	public ResponseEntity<Conta> atualizarConta(@PathVariable Long idConta, @PathVariable Long idUsuario, @RequestBody Conta contaDetails) {
		
		Pessoa pessoa = pessoaRepository.findById(idUsuario)
				.orElseThrow(() -> new ResourceNotFoundException("[atualizarConta] Não existe uma pessoa com o id: " + idUsuario));		
		
		Conta conta = contaRepository.findById(idConta)
				.orElseThrow(() -> new ResourceNotFoundException("[atualizarConta] Não existe uma conta com o id: " + idConta));
		
		conta.setNumeroAgencia(contaDetails.getNumeroAgencia());
		conta.setNumeroConta(contaDetails.getNumeroConta());
		conta.setDigito(contaDetails.getDigito());
		conta.setTipoConta(contaDetails.getTipoConta());
		conta.setPessoa(pessoa);
		
		Conta contaUpdate = contaRepository.save(conta);
		return ResponseEntity.ok(contaUpdate);
	}
	
	/* Deletar Conta do Usuário */
	@DeleteMapping("/contas/{id}")
	public ResponseEntity<Map<String, Boolean>> deletarConta(@PathVariable Long id) {
		Conta conta = contaRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("[deletarConta] Não existe uma conta com o id: " + id));
		
		contaRepository.delete(conta);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
	
}
