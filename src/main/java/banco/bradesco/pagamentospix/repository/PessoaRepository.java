package banco.bradesco.pagamentospix.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import banco.bradesco.pagamentospix.domain.Pessoa;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
	
	public final static String FIND_PESSOA_BY_NAME = "SELECT p FROM "
			+ "Pessoa p WHERE p.nome like %:nomePesquisa%";
	
	@Query(FIND_PESSOA_BY_NAME)
	List<Pessoa> findPessoaByNomeLike(@Param("nomePesquisa") String nomePesquisa);
	
	//List<Pessoa> findPessoaByNome(String nome);

}
