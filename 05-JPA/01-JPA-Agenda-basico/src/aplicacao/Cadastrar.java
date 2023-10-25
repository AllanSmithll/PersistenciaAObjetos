package aplicacao;

/**
 * IFPB - TSI - PERSISTENCIA DE OBJETOS
 * @author Prof Fausto Ayres
 */
import jakarta.persistence.EntityManager;
import modelo.Aluno;
import modelo.Pessoa;

public class Cadastrar {
	private EntityManager manager;

	public Cadastrar() {
		try {
			manager = Util.conectarBanco();
			System.out.println("Cadastrando");

			Pessoa p;
			Aluno a;
			manager.getTransaction().begin();
			p = new Pessoa("joao");
			manager.persist(p);
			manager.getTransaction().commit();

			manager.getTransaction().begin();
			p = new Pessoa("maria");
			manager.persist(p);
			manager.getTransaction().commit();

			manager.getTransaction().begin();
			p = new Pessoa("jose");
			manager.persist(p);
			manager.getTransaction().commit();

			manager.getTransaction().begin();
			a = new Aluno("paulo",9);
			manager.persist(a);
			manager.getTransaction().commit();
			
			manager.getTransaction().begin();
			a = new Aluno("ana",10);
			manager.persist(a);
			manager.getTransaction().commit();

		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		Util.fecharBanco();
		System.out.println("fim do programa");
	}

	// =================================================
	public static void main(String[] args) {
		new Cadastrar();
	}

}
