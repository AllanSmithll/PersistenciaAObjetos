package modelo;
/**********************************
 * IFPB - SI
 * POB - Persistencia de Objetos
 * Prof. Fausto Ayres
 **********************************/


import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;


@Entity 
public class Telefone {
	@Id		
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String numero;	
	
	@ManyToOne(fetch=FetchType.EAGER)
	private Pessoa pessoa;			//lado inverso do relacionamento
	
	
	public Telefone (){}
	public Telefone(String numero) {
		this.numero = numero;
	}

	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}

	
	public int getId() {
		return id;
	}
	//	--------------------RELACIONAMENTO--------------------------------
	public Pessoa getPessoa() {
		return pessoa;
	}
	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}
	
	
	@Override
	public String toString() {
		return "id=" + id + ", numero=" + numero +
			 (pessoa != null ? ", pessoa=" + pessoa.getNome() : "sem nome") ;
	}

	public boolean ehCelular() {
		return numero.startsWith("9");
	}
	
	public boolean ehFixo() {
		return numero.startsWith("3");
	}
	
	
}
