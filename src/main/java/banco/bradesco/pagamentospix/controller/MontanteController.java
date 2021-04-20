package banco.bradesco.pagamentospix.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import banco.bradesco.pagamentospix.domain.Pagamento;
import banco.bradesco.pagamentospix.repository.PagamentoRepository;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class MontanteController {
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	/* Busca maior e menor ano das datas de pagamentos por usuário */
	@GetMapping("/montante/maior-menor-ano/{idUsuario}")
	public int[] buscarMaiorMenorAno(@PathVariable Long idUsuario) {

		List<Pagamento> pagamentos = pagamentoRepository.findPagamentoByUsuario(idUsuario);
		List<LocalDate> datasPagamentos = new LinkedList<LocalDate>();
		
		for(Pagamento p : pagamentos)
			datasPagamentos.add(p.getDataPagamento());
		
		int maiorAno = Collections.max(datasPagamentos).getYear();
		int menorAno = Collections.min(datasPagamentos).getYear();
		int[] anos = { maiorAno, menorAno };
		
		return anos;
	}
	
}
