/**********************************
 * IFPB - Curso Superior de Tec. em Sist. para Internet
 * POB - Persistencia de Objetos
 * Prof. Fausto Ayres
 *
 */

package appconsole;

import java.util.List;

import com.db4o.ObjectContainer;
import com.db4o.query.Candidate;
import com.db4o.query.Evaluation;
import com.db4o.query.Query;

import modelo.Aluguel;
import modelo.Carro;

public class Consultar {
	protected ObjectContainer manager;

	public Consultar() {
		try {
			manager = Util.conectarBanco();
			
			System.out.println("consultas... \n");
			System.out.println("\nAlugueis de carro palio ordenados por placa");
			Query q;
			q = manager.query();
			q.constrain(Aluguel.class);
			q.descend("carro").descend("modelo").constrain("palio");
			q.descend("carro").descend("placa").orderAscending();	//ordena��o
			List<Aluguel> resultados = q.execute();
			for(Aluguel a : resultados)
				System.out.println(a);
			
			
			System.out.println("\nAlugueis nao finalizados");
			q = manager.query();
			q.constrain(Aluguel.class);
			q.descend("finalizado").constrain(false);
			List<Aluguel> resultados2 = q.execute();
			for(Aluguel a : resultados2)
				System.out.println(a);
			
			System.out.println("\nCarros que possuem alugueis de 9 dias");
			q = manager.query();
			q.constrain(Carro.class);
			q.descend("alugueis").descend("dias").constrain(9);
			List<Carro> resultados3 = q.execute();
			for(Carro c : resultados3)
				System.out.println(c);
			
			System.out.println("\nCarros que possuem 1 alugueis");
			q = manager.query();
			q.constrain(Carro.class);
			q.constrain(new Filtro1());
			List<Carro> resultados4 = q.execute();
			for(Carro c : resultados4)
				System.out.println(c);
			
			
			//System.out.println("\nClientes que possuem 2 alugueis");
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		Util.desconectar();
		System.out.println("\nfim do programa !");
	}

	public static void main(String[] args) {
		new Consultar();
	}
}

//classe interna
class Filtro1 implements Evaluation {
	public void evaluate(Candidate candidate) {
		Carro car = (Carro) candidate.getObject();
		if(car.getAlugueis().size()== 1) 
			candidate.include(true); 
		else		
			candidate.include(false);
	}
}

class Filtro2 implements Evaluation {
	public void evaluate(Candidate candidate) {
		Carro car = (Carro) candidate.getObject();
		//verificar se todos os alugueis do carro estao finalizados
		boolean finalizado = true;
		for(Aluguel a : car.getAlugueis())
			if(!a.isFinalizado())
				finalizado=false;
				
		if(finalizado)
			candidate.include(true);
		else		
			candidate.include(false);
	}
}