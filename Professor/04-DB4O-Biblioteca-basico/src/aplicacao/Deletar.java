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

import modelo.Livro;

public class Deletar {
	protected ObjectContainer manager;

	public Deletar() {
		manager = Util.conectarBanco();
		apagar();
		Util.desconectar();

		System.out.println("\n\n aviso: feche sempre o plugin OME antes de executar aplicação");
	}

	public void apagar() {
		/*
		 * Tarefa: 
		 * apagar livro "c" e apagar os autores do livro que ficarem orfao
		 */
		// consulta
		Query q = manager.query();
		q.constrain(Livro.class);
		q.descend("titulo").constrain("c");
		List<Livro> resultados = q.execute();

		if (resultados.size() > 0) {
			Livro livro = resultados.get(0);

			// remover manualmente o relacionamento dos autores do livro
//			for(Autor a : livro.getAutores()) {
//				a.remover(livro);
//				manager.store(a);
//				
//				if(a.getLivros().isEmpty())
//					manager.delete(a);	//apagar o autor caso seja orfão
//			}

			manager.delete(livro);
			manager.commit();
			System.out.println("apagou livro");
		} 
		else
			System.out.println("inexistente");
	}

	// =================================================
	public static void main(String[] args) {
		new Deletar();
	}
}
