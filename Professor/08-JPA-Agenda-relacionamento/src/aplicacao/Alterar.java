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

public class Alterar {
	private EntityManager manager;

	public Alterar() {
		manager = Util.conectarBanco();

		try {
			System.out.println("alterando relacionamentos do joao");

			manager.getTransaction().begin();
			TypedQuery<Pessoa> q = manager.createQuery(
					"select p from Pessoa p where p.nome = 'joao' ", Pessoa.class);
			Pessoa p = q.getSingleResult();

			//alteração da lista telefones
			if (p.getTelefones().isEmpty())
				System.out.println("joao nao tem telefone ");
			else {
				Telefone t = new Telefone("999999999");
				p.adicionar(t);
				t.setPessoa(p);
				System.out.println("adicionou 99999999 a joao ");
			}
			manager.getTransaction().commit();
		} 
		catch (NonUniqueResultException e) {
			manager.getTransaction().rollback();
			System.out.println("encontrou nome duplicado ");
		}
		catch (NoResultException e) {
			manager.getTransaction().rollback();
			System.out.println("nao encontrou nome");
		}
		catch (Exception e) {
			manager.getTransaction().rollback();
			System.out.println(e.getMessage());
		}


		Util.fecharBanco();
		System.out.println("fim do programa");
	}

	// =================================================
	public static void main(String[] args) {
		new Alterar();
	}

}
