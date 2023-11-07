package aplicacao;

/**
 * IFPB - TSI - PERSISTENCIA DE OBJETOS
 * @author Prof Fausto Ayres
 */
import jakarta.persistence.EntityManager;
import modelo.Pessoa;
import modelo.Telefone;

public class Cadastrar {
	private EntityManager manager;

	public Cadastrar() {
		manager = Util.conectarBanco();
		
		System.out.println("Cadastrando...");
		try {
			Pessoa p;
			manager.getTransaction().begin();
			p = new Pessoa("joao");
			p.adicionar(new Telefone("988881111"));
			p.adicionar(new Telefone("988882222"));
			manager.persist(p);
			manager.getTransaction().commit();

			manager.getTransaction().begin();
			p = new Pessoa("maria");
			p.adicionar(new Telefone("988883333"));
			p.adicionar(new Telefone("988884444"));
			p.adicionar(new Telefone("32470000"));
			manager.persist(p);
			manager.getTransaction().commit();


			manager.getTransaction().begin();
			p = new Pessoa("jose");
			p.adicionar(new Telefone("988885555"));
			p.adicionar(new Telefone("988886666"));
			manager.persist(p);
			manager.getTransaction().commit();
			
			manager.getTransaction().begin();
			p = new Pessoa("paulo");
			manager.persist(p);
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
