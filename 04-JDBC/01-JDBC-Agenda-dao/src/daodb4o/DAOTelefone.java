/**********************************
 * IFPB - SI
 * POB - Persistencia de Objetos
 * Prof. Fausto Ayres
 **********************************/
package daodb4o;


import java.util.List;

import com.db4o.query.Query;

import modelo.Telefone;

public class DAOTelefone  extends DAO<Telefone>{
	public Telefone read (Object chave) {
		String num = (String) chave;
		Query q = manager.query();
		q.constrain(Telefone.class);
		q.descend("numero").constrain(num);
		List<Telefone> resultados = q.execute();
		if (resultados.size()>0)
			return resultados.get(0);
		else
			return null;
	}
	
	/**********************************************************
	 * 
	 * TODAS AS CONSULTAS DE TELEFONE
	 * 
	 **********************************************************/

	public List<Telefone> readAll(String digitos) {
		Query q = manager.query();
		q.constrain(Telefone.class);
		q.descend("numero").constrain(digitos).like();
		List<Telefone> result = q.execute(); 
		return result;
	}

	public List<Telefone> readByNome(String nome) {
		Query q = manager.query();
		q.constrain(Telefone.class);
		q.descend("pessoa").descend("nome").constrain(nome);
		List<Telefone> result = q.execute(); 
		return result;
	}

}
