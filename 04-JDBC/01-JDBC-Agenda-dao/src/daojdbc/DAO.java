/**
 * IFPB - Curso SI - Disciplina de PERSISTENCIA DE OBJETOS
 * @author Prof Fausto Ayres
 */

package daojdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public abstract class DAO<T> implements DAOInterface<T> {
	protected static Connection manager;

	public static void open() {
		manager = Util.conectarBanco();
	}
	public static void close() {
		Util.desconectar();

	}

	public abstract void create(T t);

	public abstract T read(Object chave);

	public abstract T update(T t);

	public abstract void delete(T t);

	public abstract List<T> readAll();

	/******************
	 * TRANSAÇÃO
	 ******************/
	public static void begin() {
		try {
			manager.setAutoCommit(false);
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void commit() {
		try {
			manager.commit();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void rollback() {
		try {
			manager.rollback();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
//	public void deleteAll() {
//	try {
//		Class<T> type = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass())
//				.getActualTypeArguments()[0];
//
//		PreparedStatement st = manager.prepareStatement("delete from " + type.getSimpleName());
//		st.executeUpdate();
//		st.close();
//
//		String sql = "";
//		// String sgbd = con.getMetaData().getDatabaseProductName() ;
//		if (Util.sgbd.equalsIgnoreCase("postgresql"))
//			sql = "ALTER SEQUENCE " + type.getSimpleName() + "_id_seq RESTART WITH 1";
//		else if (Util.sgbd.equalsIgnoreCase("mysql"))
//			sql = "ALTER TABLE " + type.getSimpleName() + " AUTO_INCREMENT = 1";
//
//		st = manager.prepareStatement(sql);
//		st.executeUpdate();
//		st.close();
//	} catch (SQLException e) {
//		e.printStackTrace();
//	}
//}
}
