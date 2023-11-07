package modelo;


import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity 
public class Pessoa {
	@Id		
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String nome;
	@OneToMany(mappedBy="pessoa", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
//	@OneToMany(mappedBy="pessoa", cascade={CascadeType.PERSIST,CascadeType.MERGE}, orphanRemoval=false)
	private List<Telefone> telefones = new ArrayList<>();


	public Pessoa (){}

	public Pessoa(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	//---relacionamentos---------
	
	public void adicionar(Telefone t){
		telefones.add(t);
		t.setPessoa(this);
	}
	public void remover(Telefone t){
		telefones.remove(t);
		t.setPessoa(null);
	}
	public Telefone localizar(String num) {
		for(Telefone t : telefones)
			if (t.getNumero().equals(num))
				return t;
		return null;
	}
	
	
	
	public String toString() {
		String texto ;
		texto = id + ", nome=" + nome +  ", telefones: ";
		for(Telefone t : telefones)
			texto+= t.getNumero() + ",";
		return texto;
	}

	public List<Telefone> getTelefones() {
		return telefones;
	}

}
