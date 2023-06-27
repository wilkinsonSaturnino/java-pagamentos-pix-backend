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

import banco.bradesco.pagamentospix.domain.Pessoa;
import banco.bradesco.pagamentospix.exception.ResourceNotFoundException;
import banco.bradesco.pagamentospix.repository.PessoaRepository;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class PessoaController {

	@Autowired
	private PessoaRepository pessoaRepository;
	
	/* Listar todos os Usuários */
	@GetMapping("/usuarios")
	public List<Pessoa> listarTodos() {
		return pessoaRepository.findAll();
	}
	
	/* Salvar Usuário */
	@PostMapping("/usuarios")
	public Pessoa salvarPessoa(@RequestBody Pessoa pessoa) {
		return pessoaRepository.save(pessoa);
	}
	
	/* Buscar Usuário por id */
	@GetMapping("/usuarios/{id}")
	public ResponseEntity<Pessoa> buscarPessoaPorId(@PathVariable Long id) {
		Pessoa pessoa = pessoaRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("[buscarPessoaPorId] Não existe um usuário com o id: " + id));
		return ResponseEntity.ok(pessoa);
	}

	/* Atualizar Usuário */
	@PutMapping("/usuarios/{id}")
	public ResponseEntity<Pessoa> atualizarPessoa(@PathVariable Long id, @RequestBody Pessoa pessoaDetails) {
		Pessoa pessoa = pessoaRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("[atualizarPessoa] Não existe um usuário com o id: " + id));
		
		pessoa.setNome(pessoaDetails.getNome());
		pessoa.setAgencia(pessoaDetails.getAgencia());
		pessoa.setConta(pessoaDetails.getConta());
		pessoa.setDigito(pessoaDetails.getDigito());
		
		Pessoa pessoaUpdate = pessoaRepository.save(pessoa);
		return ResponseEntity.ok(pessoaUpdate);
	}
	
	/* Deletar Usuário */
	@DeleteMapping("/usuarios/{id}")
	public ResponseEntity<Map<String, Boolean>> deletarPessoa(@PathVariable Long id) {
		Pessoa pessoa = pessoaRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("[deletarPessoa] Não existe um usuário com o id: " + id));
		
		pessoaRepository.delete(pessoa);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
	
	/* Buscar Usuário por nome */
	@GetMapping("/usuarios/por-nome/{nomePesquisa}")
	public List<Pessoa> buscarPessoaPorNome(@PathVariable String nomePesquisa) {
		return pessoaRepository.findPessoaByNomeLike(nomePesquisa);
	}	
	
}
