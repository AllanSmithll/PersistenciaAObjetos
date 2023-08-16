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


public class Alterar {
	protected ObjectContainer manager;

	public Alterar(){
		manager = Util.conectarBanco();
		atualizar();
		Util.desconectar();
		System.out.println("\n\n aviso: feche sempre o plugin OME antes de executar aplica��o");
	}

	public void atualizar(){
		/*
		 * Tarefa1: 
		 * decrementar a quantidade de exemplares do livro java
		 * 
		 */

		//consulta
		Query q1 = manager.query();
		q1.constrain(Livro.class);  				
		q1.descend("titulo").constrain("java");		 
		List<Livro> resultados1 = q1.execute(); 

		if(resultados1.size()>0) {
			resultados1.remove(-1);
			manager.commit();
			System.out.println("alterou quantidade livro java");
		}
		else
			System.out.println("nao encontrado");
		
		/*
		 * Tarefa2: 
		 * adicionar autor de nome "maria" ao livro titulo "php" (vice-versa)
		 * 
		 */

		//consultas
		Query q2 = manager.query();
		q2.constrain(Livro.class);  				
		q2.descend("titulo").constrain("php");		 
		List<Livro> resultados2 = q2.execute(); 

		Query q3 = manager.query();
		q3.constrain(Autor.class);  				
		q3.descend("nome").constrain("maria");		 
		List<Autor> resultados3 = q3.execute(); 

		if(resultados2.size()>0 && resultados3.size()>0) {
			//adicionar autor ao livro
			//atualizar livro no banco
			System.out.println("alterou autor livro java");
		}
		else
			System.out.println("nao encontrado");
		
		
		/*
		 * Tarefa3: 
		 * remover autor de nome "antonio" do livro titulo "php" (vice-versa)
		 * 
		 */
		
		
	}



	//=================================================
	public static void main(String[] args) {
		new Alterar();
	}
}

