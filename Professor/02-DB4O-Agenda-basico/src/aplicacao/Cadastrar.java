package aplicacao;
/**********************************
 * IFPB - Curso Superior de Tec. em Sist. para Internet
 * POB - Persistencia de Objetos
 * Prof. Fausto Ayres
 *
 */

import com.db4o.ObjectContainer;

import modelo.Aluno;
import modelo.Pessoa;
import modelo.Professor;
import modelo.Telefone;


public class Cadastrar {
	protected ObjectContainer manager;

	public Cadastrar(){
		manager = Util.conectarBanco();
		cadastrar();
		Util.desconectar();
		
		System.out.println("fim da aplicacao");
	}

	
	public void cadastrar(){
		System.out.println("cadastrando...");
		Pessoa p;
	
		p = new Pessoa("joao");
		p.setDtnascimento("01/01/1980");
		p.adicionar(new Telefone("88880000"));
		p.adicionar(new Telefone("88881100"));		
		manager.store(p);	
		manager.commit();

		p = new Pessoa("maria");
		p.setDtnascimento("02/02/1980");
		p.adicionar(new Telefone("87882200"));
		p.adicionar(new Telefone("87003300"));
		p.adicionar(new Telefone("32470000"));
		manager.store(p);
		manager.commit();

		p = new Pessoa("jose");
		p.setDtnascimento("01/01/1990");
		p.adicionar(new Telefone("87884400"));
		manager.store(p);		
		manager.commit();

		p = new Aluno("paulo",9);
		p.setDtnascimento("02/02/1990");
		manager.store(p);		
		manager.commit();

		p = new Professor ("antonio", 1000.00);
		p.setDtnascimento("01/03/1990");
		manager.store(p);		
		manager.commit();
		
		System.out.println("cadastrou.");

	}	


	//=================================================
	public static void main(String[] args) {
		new Cadastrar();
	}
}


