/**********************************
 * IFPB - Curso Superior de Tec. em Sist. para Internet
 * POB - Persistencia de Objetos
 * Prof. Fausto Ayres
 *
 */

package appconsole;

import java.util.List;

import com.db4o.ObjectContainer;
import com.db4o.query.Query;

import modelo.Aluguel;
import modelo.Carro;
import modelo.Cliente;

public class Listar {
	protected ObjectContainer manager;

	public Listar() {
		try {
			manager = Util.conectarBanco();
			
			System.out.println("\n---listagem de carro:");
			Query q = manager.query();
			q.constrain(Carro.class);  				
			List<Carro> resultados1 = q.execute();
			for(Carro c: resultados1)
				System.out.println(c);

			System.out.println("\n---listagem de clientes:");
			q = manager.query();
			q.constrain(Cliente.class);  				
			List<Cliente> resultados2 = q.execute();
			for(Cliente c: resultados2)
				System.out.println(c);
			
			System.out.println("\n---listagem de alugueis:");
			q = manager.query();
			q.constrain(Aluguel.class);  				
			List<Aluguel> resultados3 = q.execute();
			for(Aluguel c: resultados3)
				System.out.println(c);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		Util.desconectar();
		System.out.println("\nfim do programa !");
	}

	public static void main(String[] args) {
		new Listar();
	}
}
