/**********************************
 * IFPB - Curso Superior de Tec. em Sist. para Internet
 * POB - Persistencia de Objetos
 * Prof. Fausto Ayres
 *
 */

package aplicacao;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.config.EmbeddedConfiguration;

import modelo.Autor;
import modelo.Livro;

public class Util {
	private static ObjectContainer manager;

	public static ObjectContainer conectarBanco(){
		if (manager != null)
			return manager;		//ja tem uma conexao

		//---------------------------------------------------------------
		//configurar, criar e abrir banco local (pasta do projeto)
		//---------------------------------------------------------------
		EmbeddedConfiguration config =  Db4oEmbedded.newConfiguration(); 
		config.common().messageLevel(0);  // mensagens na tela 0(desliga),1,2,3...
		
		// habilitar cascata na altera��o, remo��o e leitura
		config.common().objectClass(Livro.class).cascadeOnDelete(true);;
		config.common().objectClass(Livro.class).cascadeOnUpdate(true);;
		config.common().objectClass(Livro.class).cascadeOnActivate(true);
		config.common().objectClass(Autor.class).cascadeOnDelete(true);;
		config.common().objectClass(Autor.class).cascadeOnUpdate(true);;
		config.common().objectClass(Autor.class).cascadeOnActivate(true);
		
		// nivel de profundidade do grafo para leitura e atualiza��o
//		config.common().objectClass(Livro.class).updateDepth(5);
//		config.common().objectClass(Livro.class).minimumActivationDepth(5);
//		config.common().objectClass(Autor.class).updateDepth(5);
//		config.common().objectClass(Autor.class).minimumActivationDepth(5);
	
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
	
	
}
