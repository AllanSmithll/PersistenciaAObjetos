package modelo;


public class Motorista {
	private String cnh;
	private String nome;

	public Motorista(String cnh, String nome) {
		this.cnh = cnh;
		this.nome = nome;
	}

	public String getCnh() {
		return cnh;
	}
	public void setCnh(String cnh) {
		this.cnh = cnh;
	}

	@Override
	public String toString() {
		return "[CNH=" + cnh + ", nome=" + nome + "]";
	}
	
	
}
