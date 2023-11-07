/**********************************
 * IFPB - TSI
 * POB - Persistencia de Objetos
 * Prof. Fausto Ayres
 *
 */
package aplicacao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
/**
 * IFPB - TSI - PERSISTENCIA DE OBJETOS
 * @author Prof Fausto Ayres
 */

public class Util {
	private static EntityManager manager;
	private static EntityManagerFactory factory;

	public static EntityManager conectarBanco(){
		if(manager == null) {
			//processar arquivo persistence.xml
			factory = Persistence.createEntityManagerFactory("hibernate-postgresql");
			//criar instancia do manager
			manager = factory.createEntityManager();
		}
		return manager;
	}

	public static void fecharBanco(){
		if(manager != null && manager.isOpen()) {
			manager.close();
			factory.close();
			manager=null;
		}
	}
}
