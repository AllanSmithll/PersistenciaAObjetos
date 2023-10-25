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

public class Deletar {
	private EntityManager manager;

	public Deletar() {
		try {
			manager = Util.conectarBanco();

			manager.getTransaction().begin();
			TypedQuery<Pessoa> q = manager.createQuery(
					"select p from Pessoa p where p.nome = 'joana' ", 	Pessoa.class);
			Pessoa p = q.getSingleResult();

			manager.remove(p);
			manager.getTransaction().commit();
			System.out.println("deletou joana");
		}
		catch (NonUniqueResultException e) {
			System.out.println("encontrou nome repetido ");
		}
		catch (NoResultException e) {
			System.out.println("nao encontrou");
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
			
		Util.fecharBanco();
		System.out.println("fim do programa");
	}

	// =================================================
	public static void main(String[] args) {
		new Deletar();
	}

}
