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

public class Apagar {
	protected ObjectContainer manager;

	public Apagar() {
		try {
			manager = Util.conectarBanco();
			System.out.println("excluindo");
			
			//localizar o carro BBB2000 no banco
			Query q = manager.query();
			q.constrain(Carro.class);  				
			q.descend("placa").constrain("BBB2000");		 
			List<Carro> resultados = q.execute(); // select p from Pessoa p where p.nome="joao"

			if(resultados.size()>0) {
				Carro car = resultados.get(0);

				// remover  o cliente relacionado a cada aluguel do carro e
				// apagar cada aluguel (orfao)
				for (Aluguel a : car.getAlugueis()) {
					Cliente cli = a.getCliente();
					//remover o aluguel do cliente
					cli.remover(a);
					//atualizar o cliente do aluguel
					manager.store(cli);
					manager.delete(a);  //apagar aluguel para nao ficar orfao
				}

				// apagar o carro
				manager.delete(car);
				manager.commit();
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		Util.desconectar();
		System.out.println("\nfim do programa !");
	}

	public static void main(String[] args) {
		new Apagar();
	}
}
