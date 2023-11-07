package aplicacao;

/**
 * IFPB - TSI - PERSISTENCIA DE OBJETOS
 * @author Prof Fausto Ayres
 */

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import modelo.Pessoa;
import modelo.Telefone;

public class Listar {
	private  EntityManager manager;

	public Listar() {
		manager = Util.conectarBanco();
		
		try {
			System.out.println("\nListagem de pessoas");
			TypedQuery<Pessoa> query1 = manager.createQuery("select p from Pessoa p", Pessoa.class); // order by p.nome
			List<Pessoa> resultados1 = query1.getResultList();
			for (Pessoa p : resultados1)
				System.out.println(p);


			System.out.println("\nListagem de telefones");
			TypedQuery<Telefone> query2 = manager.createQuery("select t from Telefone t order by t.id", Telefone.class); // order by p.nome
			List<Telefone> resultados2 = query2.getResultList();
			for (Telefone t : resultados2)
				System.out.println(t);

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
