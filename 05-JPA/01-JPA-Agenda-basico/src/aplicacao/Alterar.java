package aplicacao;

/**
 * IFPB - TSI - PERSISTENCIA DE OBJETOS
 * @author Prof Fausto Ayres
 */

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.TypedQuery;
import modelo.Pessoa;

public class Alterar {
	private EntityManager manager;

	public Alterar() {
		manager = Util.conectarBanco();
		
		try {
			manager.getTransaction().begin();
			TypedQuery<Pessoa> q = manager.createQuery(
					"select p from Pessoa p where p.nome = 'joao' ", Pessoa.class);
			Pessoa p = q.getSingleResult();

			p.setNome("joana");
			
			manager.getTransaction().commit();
			System.out.println("alterou o joao para joana");
		} 
		catch (NoResultException e) {
			System.out.println("nao encontrou");
		}
		catch (NonUniqueResultException e) {
			System.out.println("encontrou nome duplicado ");
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}

		
		Util.conectarBanco();
		System.out.println("fim do programa");
	}

	// =================================================
	public static void main(String[] args) {
		new Alterar();
	}

}
