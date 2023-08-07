package aplicacao;
/**********************************
 * IFPB - Curso Superior de Tec. em Sist. para Internet
 * POB - Persistencia de Objetos
 * Prof. Fausto Ayres
 *
 */

import java.util.List;

import com.db4o.ObjectContainer;
import com.db4o.query.Query;

import modelo.Carro;
import modelo.Motor;
import modelo.Motorista;


public class Listar {
	protected ObjectContainer manager;

	public Listar(){
		manager = Util.conectarBanco();
		listar();
		Util.desconectar();
		
		System.out.println("\n\n aviso: feche sempre o plugin OME antes de executar aplicação");
	}

	public void listar(){
		System.out.println("\n---listagem de carros:");
		Query q = manager.query();
		q.constrain(Carro.class);  				
		List<Carro> resultados = q.execute();
		for(Carro p: resultados)
			System.out.println(p);
		
		System.out.println("\n---listagem de motores:");
		Query q2 = manager.query();
		q2.constrain(Motor.class);  				
		List<Motor> resultados2 = q2.execute();
		for(Motor mt: resultados2)
			System.out.println(mt);
		
		System.out.println("\n---listagem de motoristas:");
		Query q3 = manager.query();
		q3.constrain(Motorista.class);  				
		List<Motorista> resultados3 = q3.execute();
		for(Motorista m: resultados3)
			System.out.println(m);
	}
	




	//=================================================
	public static void main(String[] args) {
		new Listar();
	}
}

