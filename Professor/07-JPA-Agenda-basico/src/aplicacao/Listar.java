package aplicacao;

/**
 * IFPB - TSI - PERSISTENCIA DE OBJETOS
 * @author Prof Fausto Ayres
 */

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import modelo.Aluno;
import modelo.Pessoa;

public class Listar {
	private EntityManager manager;

	public Listar() {
		try {
			manager = Util.conectarBanco();

			System.out.println("-----Listagem de pessoas-----");
			TypedQuery<Pessoa> query = manager.createQuery("select p from Pessoa p order by p.id", Pessoa.class); // order by p.nome
			List<Pessoa> resultados = query.getResultList();
			for (Pessoa p : resultados)
				System.out.println(p);

			System.out.println("\n-----Listagem de alunos-----");
			TypedQuery<Aluno> query2 = manager.createQuery("select a from Aluno a", Aluno.class); // order by p.nome
			List<Aluno> resultados2 = query2.getResultList();
			for (Aluno a : resultados2)
				System.out.println(a);
	
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
			
		Util.fecharBanco();
		System.out.println("fim do programa");
	}

	// =================================================
	public static void main(String[] args) {
		new Listar();
	}

}
