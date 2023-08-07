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

import modelo.Aluno;
import modelo.Pessoa;
import modelo.Professor;
import modelo.Telefone;


public class Listar {
	protected ObjectContainer manager;

	public Listar(){
		manager = Util.conectarBanco();
		listarPessoas();
		listarAlunos();
		listarProfessores();
		listarTelefones();
		Util.desconectar();
		
		System.out.println("\n\n aviso: feche sempre o plugin OME antes de executar aplicação");
	}

	public void listarPessoas(){
		System.out.println("\n---listagem das pessoas:");
		
		Query q = manager.query();
		q.constrain(Pessoa.class);  				
		List<Pessoa> resultados = q.execute();
		for(Pessoa p: resultados)
			System.out.println(p);
	}
	public void listarAlunos(){
		System.out.println("\n---listagem dos alunos:");
		
		Query q = manager.query();
		q.constrain(Aluno.class);  				
		List<Aluno> resultados = q.execute();
		for(Aluno a: resultados)
			System.out.println(a);
	}

	public void listarProfessores(){
		System.out.println("\n---listagem dos professores:");
		
		Query q = manager.query();
		q.constrain(Professor.class);  				
		List<Professor> resultados = q.execute();
		for(Professor p: resultados)
			System.out.println(p);
	}

	public void listarTelefones(){
		System.out.println("\n---listagem dos telefones:");
		
		Query q = manager.query();
		q.constrain(Telefone.class);  				
		List<Telefone> resultados = q.execute();
		for(Telefone t: resultados)
			System.out.println(t);
	}



	//=================================================
	public static void main(String[] args) {
		new Listar();
	}
}

