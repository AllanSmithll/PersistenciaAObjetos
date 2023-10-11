/**
 * IFPB - Curso SI - Disciplina de PERSISTENCIA DE OBJETOS
 * @author Prof Fausto Ayres
 */
package daojdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modelo.Pessoa;
import modelo.Telefone;

public class DAOPessoa extends DAO<Pessoa> {

	public void create(Pessoa p) {
		try {
			PreparedStatement st = manager.prepareStatement("insert into pessoa (nome,dtnascimento) values (?,?)");
			st.setString(1, p.getNome());
			st.setString(2, p.getDtNascimento()); // st.setDate(1, Timestamp.valueOf( p.getNascimento() ));
			st.executeUpdate();

			// obter o id do objeto gravado no banco
			st = manager.prepareStatement("select id from Pessoa where nome=?");
			st.setString(1, p.getNome());
			ResultSet rs = st.executeQuery();
			int id;
			if (rs.next()) {
				id = rs.getInt("id");
				p.setId(id);
			}
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Pessoa read(Object chave) {
		try {
			String nome = (String) chave;
			PreparedStatement st = manager.prepareStatement("select * from Pessoa where nome =  ?");
			st.setString(1, nome);
			Pessoa pess = null;
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				int id = rs.getInt("id");
				String dt = rs.getString("dtnascimento");
				pess = new Pessoa(id, nome, dt);

				// carregar a lista de telefones relacionados
				int idtel;
				String numero;
				st = manager.prepareStatement("select * from telefone where idpessoa = ?");
				st.setInt(1, id);
				rs = st.executeQuery();
				while (rs.next()) {
					idtel = rs.getInt("id");
					numero = rs.getString("numero");
					pess.adicionar(new Telefone(idtel, numero, pess));
				}
			}
			st.close();
			return pess;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

	public Pessoa update(Pessoa p) {
		try {
			PreparedStatement st = manager
					.prepareStatement("update pessoa set nome = ?, dtnascimento = ?  where id = ?");
			st.setString(1, p.getNome());
			st.setString(2, p.getDtNascimento());
			st.setInt(3, p.getId());
			st.executeUpdate();
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return p;
	}

	public void delete(Pessoa p) {
		try {
			PreparedStatement st = manager.prepareStatement("delete from Pessoa where id = ?");
			st.setInt(1, p.getId());
			st.executeUpdate();
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public List<Pessoa> readAll() {
		int id, idtel;
		String dt;
		String nome, numero;
		List<Pessoa> resultados = new ArrayList<>();
		try {
			Pessoa pess;
			// carregar a lista de telefones relacionados
			PreparedStatement st = manager.prepareStatement("select * from pessoa order by id");
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				id = rs.getInt("id");
				nome = rs.getString("nome");
				dt = rs.getString("dtnascimento");
				pess = new Pessoa(id, nome, dt);
				resultados.add(pess);
			}
			st.close();

			// carregar os telefones de cada pessoa
			for (Pessoa p : resultados) {
				st = manager.prepareStatement("select * from telefone where idpessoa = ?");
				st.setInt(1, p.getId());
				rs = st.executeQuery();
				while (rs.next()) {
					idtel = rs.getInt("id");
					numero = rs.getString("numero");
					p.adicionar(new Telefone(idtel, numero, p));
				}
				rs.close();
				st.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return resultados;
	}

	public List<Pessoa> readAll(String caracteres) {
		int id;
		String dt;
		String nome;
		List<Pessoa> resultados = new ArrayList<>();
		try {
			Pessoa pess;
			// carregar a lista de telefones relacionados
			PreparedStatement st = manager.prepareStatement("select * from pessoa where nome like ? order by id");
			st.setString(1, "%" + caracteres + "%");
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				id = rs.getInt("id");
				nome = rs.getString("nome");
				dt = rs.getString("dtnascimento");
				pess = new Pessoa(id, nome, dt);
				resultados.add(pess);
			}
			st.close();

			// carregar os telefones de cada pessoa
			int idtel;
			String numero;
			for (Pessoa p : resultados) {
				st = manager.prepareStatement("select * from telefone where idpessoa = ?");
				st.setInt(1, p.getId());
				rs = st.executeQuery();
				while (rs.next()) {
					idtel = rs.getInt("id");
					numero = rs.getString("numero");
					p.adicionar(new Telefone(idtel, numero, p));
				}
				rs.close();
				st.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return resultados;

	}

	// ----------------------CONSULTAS----------------------------

	public List<Pessoa> readByNTelefones(int n) {
		int id;
		String nome;
		String dt;
		List<Pessoa> resultados = new ArrayList<Pessoa>();
		String sql = "";
		try {
			sql = "select p.id,nome,dtnascimento,count(*) from telefone t join pessoa p on (p.id=t.idpessoa) "
					+ " group by p.id " + " having count(*) = ?";
			PreparedStatement st = manager.prepareStatement(sql);
			st.setInt(1, n);
			ResultSet rs = st.executeQuery();
			Pessoa pess;
			while (rs.next()) {
				id = rs.getInt("id");
				nome = rs.getString("nome");
				dt = rs.getString("dtnascimento");
				pess = new Pessoa(id, nome, dt);
				resultados.add(pess);
			}
			st.close();

			// carregar os telefones de cada pessoa
			int idtel;
			String numero;
			for (Pessoa p : resultados) {
				st = manager.prepareStatement("select * from telefone where idpessoa = ?");
				st.setInt(1, p.getId());
				rs = st.executeQuery();
				while (rs.next()) {
					idtel = rs.getInt("id");
					numero = rs.getString("numero");
					p.adicionar(new Telefone(idtel, numero, p));
				}
				rs.close();
				st.close();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultados;
	}

	public List<Pessoa> readByMes(String mes) {
		int id;
		String nome;
		String dt;
		List<Pessoa> resultados = new ArrayList<Pessoa>();
		String sql = "";
		try {
			PreparedStatement st = manager.prepareStatement("select * from pessoa p where p.dtnascimento like ? ");
			st.setString(1, "%/" + mes + "/%");
			ResultSet rs = st.executeQuery();
			Pessoa pess;
			while (rs.next()) {
				id = rs.getInt("id");
				nome = rs.getString("nome");
				dt = rs.getString("dtnascimento");
				pess = new Pessoa(id, nome, dt);
				resultados.add(pess);
			}
			st.close();

			// carregar os telefones de cada pessoa
			int idtel;
			String numero;
			for (Pessoa p : resultados) {
				st = manager.prepareStatement("select * from telefone where idpessoa = ?" );
				st.setInt(1, p.getId());
				rs = st.executeQuery();
				while (rs.next()) {
					idtel = rs.getInt("id");
					numero = rs.getString("numero");
					p.adicionar(new Telefone(idtel, numero, p));
				}
				rs.close();
				st.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultados;
	}

}
