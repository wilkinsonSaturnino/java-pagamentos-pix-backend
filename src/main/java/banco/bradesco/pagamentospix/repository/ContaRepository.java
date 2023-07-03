package banco.bradesco.pagamentospix.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import banco.bradesco.pagamentospix.domain.Conta;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Long> {

	public final static String FIND_CONTA_BY_USUARIO = "SELECT c FROM "
			+ "Conta c WHERE c.pessoa.id = :idUsuario";
	
	@Query(FIND_CONTA_BY_USUARIO)
	List<Conta> findContaByUsuario(@Param("idUsuario") Long idUsuario);	
	
}
