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

import modelo.Pessoa;
import modelo.Telefone;


public class Localizar {
	protected ObjectContainer manager;

	public Localizar(){
		manager = Util.conectarBanco();
		localizar();
		Util.desconectar();

		System.out.println("\n\n aviso: feche sempre o plugin OME antes de executar aplicação");
	}

	public void localizar(){
		String nom = "joao";
		
		Query q = manager.query();
		q.constrain(Pessoa.class);  				
		q.descend("nome").constrain(nom);		 
		List<Pessoa> resultados = q.execute(); // select p from Pessoa p where p.nome="joao"
		
		if(resultados.size()>0) {
			Pessoa p =  resultados.get(0);
			System.out.println("localizou:" + p);
		}
		else
			System.out.println("inexistente");
	}



	//=================================================
	public static void main(String[] args) {
		new Localizar();
	}
}

