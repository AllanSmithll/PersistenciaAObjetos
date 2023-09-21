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

public class Alterar2 {
	protected ObjectContainer manager;
	
	public Alterar2() {
		try {
			manager = Util.conectarBanco();
			System.out.println("devolvendo carro");
			
			//consultar carro de placa AAA1000
			Query q = manager.query();
			q.constrain(Carro.class);  				
			q.descend("placa").constrain("AAA1000");		 
			List<Carro> resultados = q.execute(); 
			
			if(resultados.size()>0) {
				Carro car =  resultados.get(0);
				//alterar a situa��o do carro 
				car.setAlugado(false);
				// obter o ultimo aluguel do carro
				Aluguel alug = car.getAlugueis().get( car.getAlugueis().size()-1 );
				//alterar a situa��o do alugel 
				alug.setFinalizado(true);
				//atualizar carro no banco
				manager.store(car);
				manager.commit();
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		Util.desconectar();
		System.out.println("\nfim do programa !");
	}

	public static void main(String[] args) {
		new Alterar2();
	}
}
