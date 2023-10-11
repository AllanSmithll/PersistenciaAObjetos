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


public class DAOTelefone extends DAO<Telefone> {

	public  void create(Telefone t) 	{
		try{
			PreparedStatement st  = manager.prepareStatement("insert into telefone (idpessoa,numero) values (?,?)");
			st.setInt(1, t.getPessoa().getId());
			st.setString(2, t.getNumero());
			st.executeUpdate();
			st.close();

			//obter o id do objeto gravado
			st=manager.prepareStatement("select id from telefone where numero= ?");
			st.setString(1, t.getNumero());
			ResultSet rs = st.executeQuery();
			int id;
			if(rs.next()) {
				id = rs.getInt("id");
				t.setId(id);
			}
			st.close();
		}catch(SQLException e){
			e.printStackTrace();
		}	}

	public  Telefone read(Object chave) {
		try{
			String numero = (String) chave ;
			String sql = "select telefone.*, nome from telefone join pessoa on (pessoa.id=telefone.idpessoa) where numero = ? order by id";
			PreparedStatement st = manager.prepareStatement(sql);
			st.setString(1, numero);
			ResultSet rs = st.executeQuery();
			Telefone t = null;
			if(rs.next()) {
				int idtel = rs.getInt("id");
				int idpessoa = rs.getInt("idpessoa");
				String nome = rs.getString("nome");
				t = new Telefone(idtel,numero, new Pessoa(idpessoa,nome)); //falta ler a pessoa relacionada !!!
			}
			st.close();
			return t;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return null;
	}

	public  Telefone update(Telefone t) 	{
		try{
			String sql="update Telefone set numero = ?  where id = ?";
			PreparedStatement st =	manager.prepareStatement(sql);
			st.setString(1,t.getNumero());
			st.setInt(2,t.getId());
			st.executeUpdate();
			st.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
		return t;
	}

	public  void delete(Telefone t)	{
		try{
			PreparedStatement st =	manager.prepareStatement("delete from Telefone where numero = ?");
			st.setString(1,t.getNumero());
			st.executeUpdate();
			st.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}

	public  List<Telefone> readAll() 	{
		String numero,nome ;
		int id, idpessoa;
		ArrayList<Telefone> resultado = new ArrayList<>();
		String sql="";
		try{
			sql = "select pessoa.id,idpessoa, numero,nome from telefone join pessoa on (pessoa.id=telefone.idpessoa) order by id";
			PreparedStatement st =	manager.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				id = rs.getInt("id");
				idpessoa = rs.getInt("idpessoa");
				numero = rs.getString("numero");
				nome = rs.getString("nome");
				
				Telefone t = new Telefone(id,numero, new Pessoa(idpessoa,nome)); //falta ler a pessoa relacionada !!!
				resultado.add(t);
			}
			st.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
		return resultado;
	}
	
	public  List<Telefone> readAll(String digitos) 	{
		String numero,nome ;
		int id, idpessoa;
		ArrayList<Telefone> resultado = new ArrayList<>();
		String sql="";
		try{
			sql = "select pessoa.id,idpessoa, numero,nome from telefone join pessoa on (pessoa.id=telefone.idpessoa) where nome like ? order by id";
			PreparedStatement st =	manager.prepareStatement(sql);
			st.setString(1,"%"+ digitos + "%");
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				id = rs.getInt("id");
				idpessoa = rs.getInt("idpessoa");
				numero = rs.getString("numero");
				nome = rs.getString("nome");
				
				Telefone t = new Telefone(id,numero, new Pessoa(idpessoa,nome)); //falta ler a pessoa relacionada !!!
				resultado.add(t);
			}
			st.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
		return resultado;
	}
}
