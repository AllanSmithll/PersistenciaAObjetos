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

import modelo.Aluno;
import modelo.Pessoa;
import modelo.Telefone;

public class DAOAluno extends DAO<Aluno> {

	public void create(Aluno a) {
		try {
			PreparedStatement st = manager.prepareStatement("insert into pessoa (nome,dtnascimento,nota) values (?,?,?)");
			st.setString(1, a.getNome());
			st.setString(2, a.getDtNascimento()); // st.setDate(1, Timestamp.valueOf( p.getNascimento() ));
			st.setDouble(3, a.getNota());
			st.executeUpdate();

			// obter o id do objeto gravado no banco
			st = manager.prepareStatement("select id from Pessoa where nome=?");
			st.setString(1, a.getNome());
			ResultSet rs = st.executeQuery();
			int id;
			if (rs.next()) {
				id = rs.getInt("id");
				a.setId(id);
			}
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Aluno read(Object chave) {
		try {
			String nome = (String) chave;
			PreparedStatement st = manager.prepareStatement("select * from Pessoa where nome =  ? and nota is not null");
			st.setString(1, nome);
			ResultSet rs = st.executeQuery();
			Aluno alu=null;
			if (rs.next()) {
				int id = rs.getInt("id");
				String dt = rs.getString("dtnascimento");
				double nota = rs.getDouble("nota");
				alu = new Aluno(id, nome, dt, nota);

				// carregar a lista de telefones relacionados
				int idtel;
				String numero;
				st = manager.prepareStatement("select * from telefone where idpessoa = ?");
				st.setInt(1, id);
				rs = st.executeQuery();
				while (rs.next()) {
					idtel = rs.getInt("id");
					numero = rs.getString("numero");
					alu.adicionar(new Telefone(idtel, numero, alu));
				}
			}
			st.close();
			return alu;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Aluno update(Aluno a) 	{
		try{
			PreparedStatement st =manager.prepareStatement("update pessoa set nome = ?, dtnascimento = ?, nota = ?  where id = ? and nota is not null");
			st.setString(1, a.getNome());
			st.setString(2, a.getDtNascimento()); 
			st.setDouble(3, a.getNota()); 
			st.setInt(4, a.getId());
			st.executeUpdate();
			st.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
		return a;
	}

	public void delete(Aluno a) {
		try {
			PreparedStatement st = manager.prepareStatement("delete from Pessoa where id = ? and nota is not null");
			st.setInt(1, a.getId());
			st.executeUpdate();
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Aluno> readAll() {
		int id, idtel;
		String dt;
		String nome, numero;
		double nota;
		List<Aluno> resultados = new ArrayList<>();
		try {
			// carregar a lista de telefones relacionados
			PreparedStatement st = manager.prepareStatement("select * from pessoa where nota is not null order by id");
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				id = rs.getInt("id");
				nome = rs.getString("nome");
				dt = rs.getString("dtnascimento");
				nota = rs.getDouble("nota");
				Aluno a = new Aluno(id, nome, dt, nota);
				resultados.add(a);
			}
			st.close();

			// carregar os telefones de cada pessoa
			for (Aluno a : resultados) {
				st = manager.prepareStatement("select * from telefone where idpessoa = " + a.getId());
				rs = st.executeQuery();
				while (rs.next()) {
					idtel = rs.getInt("id");
					numero = rs.getString("numero");
					a.adicionar(new Telefone(idtel, numero, a));
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
