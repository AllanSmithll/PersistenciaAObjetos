package modelo;

public class Telefone {
	private int id;
	private String numero;	
	private Pessoa pessoa;			//lado inverso do relacionamento
	
	public Telefone (){}
	public Telefone(String numero) {
		this.numero = numero;
	}
	public Telefone(int id, String numero) {
		this.id=id;
		this.numero = numero;
	}

	public Telefone(int id, String numero, Pessoa pessoa) {
		this.id = id;
		this.numero = numero;
		this.pessoa = pessoa;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}

	
	//	--------------------RELACIONAMENTO--------------------------------
	public Pessoa getPessoa() {
		return pessoa;
	}
	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}
	
	
	@Override
	public String toString() {
		return "id="+id+ ", numero=" + numero +
			 (pessoa != null ? " pessoa=" + pessoa.getNome() : " sem nome") ;
	}

	public boolean ehCelular() {
		return numero.startsWith("9");
	}
	
	public boolean ehFixo() {
		return numero.startsWith("3");
	}
	
	
}
