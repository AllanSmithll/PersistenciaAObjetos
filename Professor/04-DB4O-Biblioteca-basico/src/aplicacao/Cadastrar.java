package aplicacao;
/**********************************
 * IFPB - Curso Superior de Tec. em Sist. para Internet
 * POB - Persistencia de Objetos
 * Prof. Fausto Ayres
 *
 */

import com.db4o.ObjectContainer;

import modelo.Autor;
import modelo.Livro;


public class Cadastrar {
	protected ObjectContainer manager;

	public Cadastrar(){
		manager = Util.conectarBanco();
		cadastrar();
		Util.desconectar();
		
		System.out.println("fim da aplicacao");
	}


	public void cadastrar(){
		System.out.println("Cadastrando...");

		Autor joao,maria, paulo, antonio;
		joao = new Autor("joao");
		maria = new Autor("maria");
		paulo = new Autor("paulo");
		antonio = new Autor("antonio");

		Livro java, c, php;
		java = new Livro("java", 10, 2016);
		java.adicionar(joao);
		java.adicionar(maria);
		joao.adicionar(java);
		maria.adicionar(java);
		manager.store(java);
		manager.commit();


		c = new Livro("c", 10, 2015);
		c.adicionar(joao);
		c.adicionar(paulo);
		joao.adicionar(c);
		paulo.adicionar(c);
		manager.store(c);
		manager.commit();
		
		
		php = new Livro("php", 10, 2016);
		php.adicionar(joao);
		php.adicionar(antonio);
		joao.adicionar(php);
		antonio.adicionar(php);
		manager.store(php);
		manager.commit();	

	}

	//=================================================
	public static void main(String[] args) {
		new Cadastrar();
	}
}


