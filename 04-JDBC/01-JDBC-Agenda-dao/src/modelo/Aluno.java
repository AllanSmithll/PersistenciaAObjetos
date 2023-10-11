package modelo;

/**********************************
 * IFPB - SI
 * POB - Persistencia de Objetos
 * Prof. Fausto Ayres
 **********************************/

public class Aluno extends Pessoa {
	double nota;
	
	public Aluno(String nome, double nota){
		super(nome);
		this.nota = nota;
	}
	
	public Aluno(int id, String nome, String dt, double nota){
		super(id, nome, dt);
		this.nota = nota;
	}

	@Override
	public String toString() {
		return super.toString()+ "  nota=" + nota;
	}

	public double getNota() {
		return nota;
	}

	public void setNota(double nota) {
		this.nota = nota;
	}










}
