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
			Livro livroJava = resultados1.get(0);
            int quantidadeExemplares = livroJava.getQuant();
            livroJava.setQuant(quantidadeExemplares-1);
            manager.store(livroJava);
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
			Livro livroPhp = resultados2.get(0);
            Autor autorMaria = resultados3.get(0);

            livroPhp.getAutores().add(autorMaria);
            manager.store(livroPhp);
            manager.commit();
			System.out.println("alterou autor livro java");
		}
		else
			System.out.println("nao encontrado");
		
		
		/*
		 * Tarefa3: 
		 * remover autor de nome "antonio" do livro titulo "php" (vice-versa)
		 * 
		 */
		Query q4 = manager.query();
        q4.constrain(Livro.class);
        q4.descend("titulo").constrain("php");
        List<Livro> resultados4 = q4.execute();

        Query q5 = manager.query();
        q5.constrain(Autor.class);
        q5.descend("nome").constrain("antonio");
        List<Autor> resultados5 = q5.execute();

        if (resultados4.size() > 0 && resultados5.size() > 0) {
            Livro livroPhp = resultados4.get(0);
            Autor autorAntonio = resultados5.get(0);

            livroPhp.getAutores().remove(autorAntonio);
            manager.store(livroPhp);
            manager.delete(autorAntonio);
            manager.commit();
            System.out.println("Removeu autor 'antonio' do livro 'php'");
        } else {
            System.out.println("Livro 'php' ou autor 'antonio' não encontrado(s)");
        }
	}



	//=================================================
	public static void main(String[] args) {
		new Alterar();
	}
}

