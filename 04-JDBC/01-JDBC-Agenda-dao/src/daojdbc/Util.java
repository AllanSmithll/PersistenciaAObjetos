/**********************************
 * IFPB - Curso Superior de Tec. em Sist. para Internet
 * POB - Persistencia de Objetos
 * Prof. Fausto Ayres
 *
 */
package daojdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
	private static Connection manager;
	public static String sgbd;

	public static Connection conectarBanco() {
		if (manager != null)
			return manager; // ja tem uma conexao

		try {
			Properties configuracao = new Properties();
			configuracao.load(DAO.class.getResourceAsStream("/daojdbc/util.properties"));
			sgbd = configuracao.getProperty("sgbd");
			String ip = configuracao.getProperty("ip");
			String nomebanco = configuracao.getProperty("banco");
			if (sgbd.equals("postgresql"))
				abrirBancoPostgres(ip, nomebanco);
			if (sgbd.equals("mysql"))
				abrirBancoMysql(ip, nomebanco);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return manager;
	}

	public static void abrirBancoPostgres(String ip, String nomebanco) {
		try {
			//conexao com sgbd
			String url = "jdbc:postgresql://" + ip + ":5432/" + nomebanco;
			manager = DriverManager.getConnection(url, "postgres", "ifpb"); // autocommit é true
			
			/********************************
			 * CRIAR TABELAS
			 ********************************/
			PreparedStatement st;
			ResultSet rs;
			//verificar se tabela pessoa ja existe
			st = manager.prepareStatement("select * from pg_tables where tableowner = 'postgres' and tablename= 'pessoa'");
			rs = st.executeQuery();
			
			if (!rs.next()) {// tabela nao existe
				st = manager.prepareStatement(
						"create table pessoa("
						+ "id SERIAL, "
						+ "nome varchar(30), "
						+ "dtnascimento varchar(10), "
						+ "nota numeric, "
						+ "primary key (id) ); ");
				st.executeUpdate();
				st = manager.prepareStatement(
						"create table telefone("
						+ "id SERIAL, "
						+ "idpessoa integer, "
						+ "numero varchar(30), "
						+ "primary key (id), "
						+ "foreign key (idpessoa) references pessoa );");
				st.executeUpdate();
			}
			st.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void abrirBancoMysql(String ip, String nomebanco) {
		try {
			//conexao com sgbd
			String url = "jdbc:mysql://" + ip + ":3306/" + nomebanco;
			manager = DriverManager.getConnection(url, "root", ""); // autocommit true

			/********************************
			 * CRIAR TABELAS
			 ********************************/
			PreparedStatement st;
			st = manager.prepareStatement(
					"create table IF NOT EXISTS pessoa("
					+ "id integer not null AUTO_INCREMENT, "
					+ "nome varchar(30), "
					+ "dtnascimento varchar(10),"
					+ "nota numeric, "
					+ "primary key (id) ); ");
			st.executeUpdate();
			st = manager.prepareStatement(
					"create table IF NOT EXISTS telefone("
					+ "id integer not null AUTO_INCREMENT, "
					+ "idpessoa integer, "
					+ "numero varchar(30), "
					+ "primary key (id), "
					+ "foreign key (idpessoa) references pessoa(id) );");
			st.executeUpdate();
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void desconectar() {
		if (manager != null) {
			try {
				manager.close();
				manager = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
