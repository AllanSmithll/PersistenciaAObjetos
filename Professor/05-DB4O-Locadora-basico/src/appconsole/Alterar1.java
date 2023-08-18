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

public class Alterar1 {
	protected ObjectContainer manager;
	
	public Alterar1() {
		try {
			manager = Util.conectarBanco();
			System.out.println("devolvendo carro");
			
			//consultar carro 
			Query q1 = manager.query();
			q1.constrain(Carro.class);  				
			q1.descend("placa").constrain("AAA1000");		 
			List<Carro> resultados1 = q1.execute(); 
			
			//consultar cliente
			Query q2 = manager.query();
			q2.constrain(Cliente.class);  				
			q2.descend("nome").constrain("joao");		 
			List<Cliente> resultados2 = q2.execute(); 
			
			if(resultados1.size()>0 && resultados2.size()>0) {
				Carro carro =  resultados1.get(0);
				Cliente cliente =  resultados2.get(0);
				
				int id;
				Aluguel aluguel = new Aluguel("01/05/2023", "10/05/2023",100.0);
				id = Util.gerarIdAluguel();
				aluguel.setId(id);
				aluguel.setCarro(carro);
				aluguel.setCliente(cliente);
				
				carro.adicionar(aluguel);
				cliente.adicionar(aluguel);
				carro.setAlugado(true);

				manager.store(aluguel);
				manager.commit();
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		Util.desconectar();
		System.out.println("\nfim do programa !");
	}

	public static void main(String[] args) {
		new Alterar1();
	}
}
