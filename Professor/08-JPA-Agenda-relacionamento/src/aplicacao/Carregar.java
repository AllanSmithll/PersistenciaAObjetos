package aplicacao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import modelo.Pessoa;
import modelo.Telefone;

public class Carregar {
	private EntityManager manager;

	public Carregar() {
		manager = Util.conectarBanco();

		try {
			System.out.println("\nCarregar pessoa e a coleção telefones");
			TypedQuery<Pessoa> q1 = manager.createQuery("select p from Pessoa p where p.nome = 'maria' ", Pessoa.class);
			
			Pessoa pessoa = q1.getSingleResult();
			System.out.println(pessoa.getNome());
			for(Telefone t : pessoa.getTelefones())
				System.out.println(t.getNumero());

			manager.clear(); //  o objeto deixa de ser gerenciado pelo manager

			System.out.println("\nCarregar telefone e pessoa referenciada");
			TypedQuery<Telefone> q2 = manager.createQuery("select t from Telefone t where t.numero='988883333'", Telefone.class); // order
			
			Telefone telefone = q2.getSingleResult();
			System.out.println(telefone.getNumero());
			System.out.println(telefone.getPessoa().getNome());

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		
//		try {
//			System.out.println("\nCarregar pessoa e a coleção telefones - imediato com join fetch");
//			TypedQuery<Pessoa> q1 = manager.createQuery("select p from Pessoa p join fetch p.telefones where p.nome = 'maria' ", Pessoa.class);
//			
//			Pessoa pessoa = q1.getSingleResult();
//			System.out.println(pessoa.getNome());
//			for(Telefone t : pessoa.getTelefones())
//				System.out.println(t.getNumero());
//
//			manager.clear(); //  o objeto deixa de ser gerenciado pelo manager
//
//			System.out.println("\nCarregar telefone e pessoa referenciada - imediato com join fetch");
//			TypedQuery<Telefone> q2 = manager.createQuery("select t from Telefone t join fetch t.pessoa where t.numero='988883333'", Telefone.class); // order
//			
//			Telefone telefone = q2.getSingleResult();
//			System.out.println(telefone.getNumero());
//			System.out.println(telefone.getPessoa().getNome());
//
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}

		
		Util.fecharBanco();
		System.out.println("fim do programa");
	}

	// =================================================
	public static void main(String[] args) {
		new Carregar();
	}

}
