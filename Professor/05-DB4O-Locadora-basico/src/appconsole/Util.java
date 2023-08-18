/**********************************
 * IFPB - Curso Superior de Tec. em Sist. para Internet
 * POB - Persistencia de Objetos
 * Prof. Fausto Ayres
 *
 */

package appconsole;

import java.lang.reflect.Type;
import java.util.List;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.config.EmbeddedConfiguration;
import com.db4o.query.Query;

import modelo.Aluguel;
import modelo.Carro;
import modelo.Cliente;

public class Util {
	private static ObjectContainer manager;
	
	public static ObjectContainer conectarBanco(){
		if (manager != null)
			return manager;		//ja tem uma conexao
		
		//---------------------------------------------------------------
		//configurar, criar e conectar banco local (na pasta do projeto
		//---------------------------------------------------------------
		
		EmbeddedConfiguration config =  Db4oEmbedded.newConfiguration(); 
		config.common().messageLevel(0);  // mensagens na tela 0(desliga),1,2,3...
		
		// habilitar cascata na alteração, remoção e leitura
		config.common().objectClass(Carro.class).cascadeOnDelete(false);;
		config.common().objectClass(Carro.class).cascadeOnUpdate(true);;
		config.common().objectClass(Carro.class).cascadeOnActivate(true);
		config.common().objectClass(Cliente.class).cascadeOnDelete(false);;
		config.common().objectClass(Cliente.class).cascadeOnUpdate(true);;
		config.common().objectClass(Cliente.class).cascadeOnActivate(true);
		config.common().objectClass(Aluguel.class).cascadeOnDelete(false);;
		config.common().objectClass(Aluguel.class).cascadeOnUpdate(true);;
		config.common().objectClass(Aluguel.class).cascadeOnActivate(true);
		
		//conexao local
		manager = Db4oEmbedded.openFile(config, "banco.db4o");
		return manager;
	}
	
	public static void desconectar() {
		if(manager!=null) {
			manager.close();
			manager=null;
		}
	}
	
	
	public static int gerarIdAluguel() {
		if(manager.query(Aluguel.class).size()==0) 
			//classe nao registrada no banco
			return 1;
		
		Query q = manager.query();
		q.constrain(Aluguel.class);
		q.descend("id").orderDescending();
		List<Aluguel> resultados = q.execute();

		if(resultados.size()>0) {
			Aluguel aluguel = resultados.get(0);    //max
			return aluguel.getId() + 1;
		}
		else
			return 1; 	//nenhum objeto aluguel encontrado
	}

}
