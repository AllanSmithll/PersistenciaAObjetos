/**
 * IFPB - TSI - PERSISTENCIA DE OBJETOS
 * @author Prof Fausto Ayres
 */
package modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity 
public class Pessoa {
	@Id	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String nome;	
	
	//construtor vazio obrigatorio
	public Pessoa (){}

	public Pessoa(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public int getId() {
		return id;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String toString() {
		return "id="+ id + ", nome=" + nome ;
	}

}
