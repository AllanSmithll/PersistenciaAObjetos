/**********************************
 * IFPB - Curso Superior de Tec. em Sist. para Internet
 * POB - Persistencia de Objetos
 * Prof. Fausto Ayres
 *
 */

package modelo;

import java.util.ArrayList;

public class Cliente {

	private String cpf;
	private String nome;

	private ArrayList <Aluguel> alugueis = new ArrayList<>();

	public Cliente(String nome, String cpf) {
		this.nome = nome;
		this.cpf = cpf;
	}

	public Cliente() {}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public ArrayList<Aluguel> getAlugueis() {
		return alugueis;
	}

	public void adicionar(Aluguel a){
		alugueis.add(a);
	}

	public void remover(Aluguel a){
		alugueis.remove(a);
	}

	@Override
	public String toString() {
		String texto = "nome=" + nome + ", cpf=" + cpf;
		
		for(Aluguel a : alugueis)
			texto+= "\n    aluguel: " + a ;
		
		return texto;
	}

}
