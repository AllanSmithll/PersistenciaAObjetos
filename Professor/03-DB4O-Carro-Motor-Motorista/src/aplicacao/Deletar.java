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


public class Deletar {
	protected ObjectContainer manager;

	public Deletar(){
		manager = Util.conectarBanco();
		apagar();
		Util.desconectar();
		
		System.out.println("\n\n aviso: feche sempre o plugin OME antes de executar aplicação");
	}

	public void apagar(){
		//localizar o carro placa BBB2200 
		Query q = manager.query();
		q.constrain(Carro.class);  				
		q.descend("placa").constrain("AAA1100");		 
		List<Carro> resultados = q.execute(); 
	
		if(resultados.size()>0) {
			//apagar o carro
			Carro c =  resultados.get(0);
			manager.delete(c);
			manager.commit();
			System.out.println("apagou carro AAA1100, mas não apagou o seu motor nem o motorista");
		}
		else
			System.out.println("carro inexistente");
	}



	//=================================================
	public static void main(String[] args) {
		new Deletar();
	}
}

