/**********************************
 * IFPB - SI
 * POB - Persistencia de Objetos
 * Prof. Fausto Ayres
 **********************************/
package daodb4o;


import java.util.List;

import com.db4o.query.Candidate;
import com.db4o.query.Evaluation;
import com.db4o.query.Query;

import modelo.Pessoa;

public class DAOPessoa  extends DAO<Pessoa>{

	//nome é usado como campo unico 
	public Pessoa read (Object chave) {
		String nome = (String) chave;	//casting para o tipo da chave
		Query q = manager.query();
		q.constrain(Pessoa.class);
		q.descend("nome").constrain(nome);
		List<Pessoa> resultados = q.execute();
		if (resultados.size()>0)
			return resultados.get(0);
		else
			return null;
	}


	/**********************************************************
	 * 
	 * TODAS AS CONSULTAS DE PESSOA
	 * 
	 **********************************************************/

	public  List<Pessoa> readAll(String caracteres) {
		Query q = manager.query();
		q.constrain(Pessoa.class);
		q.descend("nome").constrain(caracteres).like();		//insensitive
		List<Pessoa> result = q.execute(); 
		return result;
	}
	public Pessoa readByNumero(String n){
		Query q = manager.query();
		q.constrain(Pessoa.class);
		q.descend("telefones").descend("numero").constrain(n);
		List<Pessoa> resultados = q.execute();
		if(resultados.size()==0)
			return null;
		else
			return resultados.get(0);
	}

	public List<Pessoa>  readByNTelefones(int n) {
		Query q = manager.query();
		q.constrain(Pessoa.class);
		q.constrain(new Filtro(n));
		List<Pessoa> result = q.execute(); 
		return result;
	}

	public List<Pessoa>  readByMes(String mes) {
		Query q = manager.query();
		q.constrain(Pessoa.class);  
		q.descend("dtnascimento").constrain("/"+mes+"/").contains();
		return q.execute();
	}
	
	public boolean temTelefoneCelular(String nome) {
		Query q = manager.query();
		q.constrain(Pessoa.class);
		q.descend("nome").constrain(nome);
		q.descend("telefones").descend("numero").constrain("9").startsWith(true);
		return q.execute().size()>0;
	}

	public boolean temTelefoneFixo(String nome) {
		Query q = manager.query();
		q.constrain(Pessoa.class);
		q.descend("nome").constrain(nome);
		q.descend("telefones").descend("numero").constrain("3").startsWith(true);;
		return q.execute().size()>0;
	}



	/*-------------------------------------------------*/
	@SuppressWarnings("serial")
	class Filtro  implements Evaluation {
		private int n;
		public Filtro (int n) {this.n=n;}
		public void evaluate(Candidate candidate) {
			Pessoa p = (Pessoa) candidate.getObject();
			candidate.include( p.getTelefones().size()==n );
		}
	}

}

