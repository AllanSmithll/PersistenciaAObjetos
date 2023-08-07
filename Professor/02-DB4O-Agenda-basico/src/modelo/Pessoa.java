package modelo;

import java.util.ArrayList;
import java.util.List;
/**********************************
 * IFPB - Curso Superior de Tec. em Sist. para Internet
 * POB - Persistencia de Objetos
 * Prof. Fausto Ayres
 *
 */
public class Pessoa {
	private String nome;
	private String dtnascimento;
	private List<Telefone> telefones = new ArrayList<>();
	
	public Pessoa(String nome) {
		super();
		this.nome = nome;
	}
	

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDtnascimento() {
		return dtnascimento;
	}

	public void setDtnascimento(String dtnascimento) {
		this.dtnascimento = dtnascimento;
	}

	//relacionamento com Telefone
	public void adicionar(Telefone t){
		t.setPessoa(this);
		this.telefones.add(t);
	}
	public void remover(Telefone t){
		t.setPessoa(null);
		this.telefones.remove(t);
	}
	public Telefone localizar(String num){
		for(Telefone t : telefones)
			if (t.getNumero().equals(num))
				return t;
		return null;
	}

	public List<Telefone> getTelefones() {
		return telefones;
	}

	public String toString() {
		String texto =  "nome=" +  nome + 
				", nascimento=" + dtnascimento;

		texto += "  telefones: ";
		for(Telefone t : telefones)
			texto+= t.getNumero() + ",";

		return texto;
	}



}
