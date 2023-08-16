package aplicacao;
/**********************************
 * IFPB - Curso Superior de Tec. em Sist. para Internet
 * POB - Persistencia de Objetos
 * Prof. Fausto Ayres
 *
 */

import java.util.List;

import com.db4o.ObjectContainer;
import com.db4o.query.Candidate;
import com.db4o.query.Evaluation;
import com.db4o.query.Query;

import modelo.Livro;


public class Consultar {
	protected ObjectContainer manager;

	public Consultar(){
		manager = Util.conectarBanco();
		consultar();
		Util.desconectar();
		
		System.out.println("\n\n aviso: feche sempre o plugin OME antes de executar aplicação");
	}

	public void consultar(){
		System.out.println("\n---listar  livro com autor jo");
		
		Query q = manager.query();
		q.constrain(Livro.class);  
		q.descend("autores").descend("nome").constrain("jo").like();
		q.descend("ano").constrain(2015);
		
		List<Livro> resultados = q.execute();
		for(Livro livro: resultados)
			System.out.println(livro);
		
		System.out.println("\n---listar livro que possui autor do livro c");
	
		Query q2 = manager.query();
		q2.constrain(Livro.class);  
		q2.descend("autores").descend("livros").descend("titulo").constrain("c");
		q2.descend("titulo").constrain("c").not();
		resultados = q2.execute();
		
		for(Livro livro: resultados)
			System.out.println(livro);
	
		System.out.println("\n---listar livros sem autor");
		Query q3 = manager.query();
		q3.constrain(Livro.class);  
		q3.constrain(new Filtro());
		resultados = q3.execute();
		
		for(Livro livro: resultados)
			System.out.println(livro);
	}
	
	public static void main(String[] args) {
		new Consultar();
	}
}

//classe interna 
class Filtro implements Evaluation {

	public void evaluate(Candidate candidate) {
		//destacar o objeto que esta sendo consultado no banco
		Livro livro = (Livro) candidate.getObject();
		
		if(livro.getAutores().size()==0) 
			candidate.include(true); 	//incluir objeto no resultado da consulta
		else		
			candidate.include(false);	//excluir objeto do resultado da consulta
	}
}

