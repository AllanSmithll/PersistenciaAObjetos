package modelo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class Pessoa {
	private int id;
	private String nome;
	private String dtnascimento;
	private List<Telefone> telefones = new ArrayList<>();


	public Pessoa (){}
	public Pessoa(String nome) {
		this.nome = nome;
	}
	
	public Pessoa(int id, String nome) {
		this.id=id;
		this.nome = nome;
	}
	
	public Pessoa(int id, String nome, String nascimento) {
		super();
		this.id = id;
		this.nome = nome;
		this.dtnascimento = nascimento;
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public LocalDate getNascimento() {
		return LocalDate.parse(dtnascimento, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	}
	public String getDtNascimento() {
		return this.dtnascimento;
	}
	public void setDtNascimento(String data) {
		this.dtnascimento = data;
	}
	public void setNascimento(LocalDate nascimento) {
		this.dtnascimento = nascimento.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	}

	public void adicionar(Telefone t){
		telefones.add(t);
		t.setPessoa(this);
	}
	public void remover(Telefone t){
		telefones.remove(t);
		t.setPessoa(null);
	}


	public String toString() {
		String texto = "id=" +id + ", nome=" +  nome 
		+ ", nascimento=" + dtnascimento;

		
		texto += ",   telefones: ";
		for(Telefone t : telefones)
			texto+= t.getNumero() + ",";

		return texto;
	}


	public List<Telefone> getTelefones() {
		return telefones;
	}

}
