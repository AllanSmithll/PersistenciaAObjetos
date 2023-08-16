package modelo;
import java.util.ArrayList;
import java.util.List;

/**********************************
 * IFPB - Curso Superior de Tec. em Sist. para Internet
 * Persistencia de Objetos
 * Prof. Fausto Maranhão Ayres
 **********************************/

public class Autor   {   
	private String nome;
	private List<Livro> livros = new ArrayList<>();

	public Autor(String nome) {
		super();
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Livro> getLivros() {
		return livros;
	}

	public void adicionar(Livro l){
		livros.add(l);
	}
	public void remover(Livro l){
		livros.remove(l);
	}
	
	
	@Override
	public String toString() {
		String texto= " nome:" + String.format("%8s", nome) + " livros:" ;
		if (livros.isEmpty())
			texto += "Sem livros";
		else 	
			for(Livro a: livros) 
				texto += a.getTitulo() + ", "; 
		return texto;

	}		
	
}