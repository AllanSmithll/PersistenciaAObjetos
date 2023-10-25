package modelo;
/**********************************
 * IFPB - SI
 * POB - Persistencia de Objetos
 * Prof. Fausto Ayres
 **********************************/

import jakarta.persistence.Entity;

@Entity
public class Aluno extends Pessoa {
	private double nota;
	
	public Aluno() {}
	
	public Aluno(String nome, double nota) {
		super(nome);
		this.nota = nota;
	}

	public double getNota() {
		return nota;
	}

	public void setNota(double nota) {
		this.nota = nota;
	}

	@Override
	public String toString() {
		return super.toString() + "  nota="+ nota;
	}

	
}
