package modelo;

public class Professor extends Pessoa {
	private double salario;

	public Professor(String nome, double sal){ 
		super(nome);
		this.salario = sal;
	}

	public double getSalario() {
		return salario;
	}
	public void setSalario(double salario) {
		this.salario = salario;
	}
	@Override
	public String toString() {
		return super.toString() + "  (salario=" + salario +")";
	}

	

	

	
}
