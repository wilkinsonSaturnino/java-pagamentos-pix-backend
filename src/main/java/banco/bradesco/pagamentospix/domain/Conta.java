package banco.bradesco.pagamentospix.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "conta")
public class Conta implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;	
	
	@Column(name = "numeroAgencia")
	private Integer numeroAgencia;
	
	@Column(name = "numeroConta")
	private Integer numeroConta;
	
	@Column(name = "digito")
	private Integer digito;
	
	@Column(name = "tipoConta")
	private String tipoConta;	
	
	@JsonBackReference // Ignora a serialização da propriedade anotada
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(foreignKey = @ForeignKey(name = "pessoa_conta_fkey"))
	private Pessoa pessoa;
	
	// Gets and Sets
	
	public Conta() {}	
		
	public Conta(Integer numeroAgencia, Integer numeroConta, Integer digito, String tipoConta) {
		super();
		this.numeroAgencia = numeroAgencia;
		this.numeroConta = numeroConta;
		this.digito = digito;
		this.tipoConta = tipoConta;
	}

	public Integer getNumeroAgencia() {
		return numeroAgencia;
	}

	public void setNumeroAgencia(Integer numeroAgencia) {
		this.numeroAgencia = numeroAgencia;
	}

	public Integer getNumeroConta() {
		return numeroConta;
	}

	public void setNumeroConta(Integer numeroConta) {
		this.numeroConta = numeroConta;
	}

	public Integer getDigito() {
		return digito;
	}

	public void setDigito(Integer digito) {
		this.digito = digito;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public String getTipoConta() {
		return tipoConta;
	}

	public void setTipoConta(String tipoConta) {
		this.tipoConta = tipoConta;
	}	
	
}
