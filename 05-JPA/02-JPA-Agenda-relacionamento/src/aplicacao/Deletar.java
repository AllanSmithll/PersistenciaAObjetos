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
import modelo.Telefone;

public class Deletar {
	private EntityManager manager;

	public Deletar() {
		manager = Util.conectarBanco();

		try {
			System.out.println("deletando joao em cascata ");
			manager.getTransaction().begin();
			TypedQuery<Pessoa> q = manager.createQuery("select p from Pessoa p where p.nome = 'joao' ", Pessoa.class);
			Pessoa p = q.getSingleResult();

			for (Telefone t : p.getTelefones()) {
				t.setPessoa(null);
			}
			manager.remove(p);
			manager.getTransaction().commit();
			System.out.println("deletou com cascata");
		} catch (NonUniqueResultException e) {
			manager.getTransaction().rollback();
			System.out.println("encontrou nome repetido ");
		} catch (NoResultException e) {
			manager.getTransaction().rollback();
			System.out.println("nao encontrou pessoa");
		} catch (Exception e) {
			manager.getTransaction().rollback();
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
