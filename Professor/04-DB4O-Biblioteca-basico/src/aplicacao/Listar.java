package aplicacao;
/**********************************
 * IFPB - Curso Superior de Tec. em Sist. para Internet
 * POB - Persistencia de Objetos
 * Prof. Fausto Ayres
 *
 */

import java.util.List;

import com.db4o.ObjectContainer;
import com.db4o.query.Query;

import modelo.Autor;
import modelo.Livro;

public class Listar {
	protected ObjectContainer manager;

	public Listar(){
		manager = Util.conectarBanco();
		listar();
		Util.desconectar();
		
		System.out.println("\n\n aviso: feche sempre o plugin OME antes de executar aplicação");
	}

	public void listar(){
		System.out.println("-------Lista de Livros--------");
		Query q = manager.query();
		q.constrain(Livro.class);  				
		List<Livro> resultados = q.execute();
		for (Livro liv : resultados ) {
			System.out.println(liv);
		}
		
		System.out.println("\n-------Lista de Autores--------");
		Query q2 = manager.query();
		q2.constrain(Autor.class);  				
		List<Autor> resultados2 = q2.execute();
		for (Autor aut : resultados2 ) {
			System.out.println(aut);
		}
	}

	
	//=================================================
	public static void main(String[] args) {
		new Listar();
	}
}


