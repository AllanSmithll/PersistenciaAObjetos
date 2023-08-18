/**********************************
 * IFPB - Curso Superior de Tec. em Sist. para Internet
 * POB - Persistencia de Objetos
 * Prof. Fausto Ayres
 *
 */

package modelo;

import java.util.ArrayList;

public class Carro {
	private String placa;
	private String modelo;
	boolean alugado=false;

	ArrayList <Aluguel> alugueis = new ArrayList<>();

	public Carro(String placa, String modelo) {
		this.placa = placa;
		this.modelo = modelo;
	}
	public Carro() {}
	
	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public boolean isAlugado() {
		return alugado;
	}

	public void setAlugado(boolean alugado) {
		this.alugado = alugado;
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
		String texto = "placa=" + placa + ", modelo=" + modelo + ", alugado=" + alugado ;
		
		for(Aluguel a : alugueis)
			texto+= "\n    aluguel: " + a ;
		
		return texto;
	}


}
