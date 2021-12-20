package roteirizador.dao.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import roteirizador.dao.FuncionarioDAO;
import roteirizador.domain.Funcionario;
import roteirizador.domain.Roteirizador;
import roteirizador.domain.Vendedor;

public class FuncionarioDAODatabase implements FuncionarioDAO {

	@Override
	public void save(Funcionario funcionario) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Funcionario funcionario) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Funcionario> getAll() {
		
		List<Funcionario> funcionarios = new ArrayList <>();
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		
		try {
			conn = ConexaoJDBC.getConnection();
			st = conn.createStatement();
			rs = st.executeQuery("SELECT * FROM table_funcionarios");
				
			
				while(rs.next()){
					if (rs.getInt("funcao") == 1) {
						Funcionario funcionario = (Roteirizador) new Roteirizador(rs.getInt("matricula"), rs.getInt("unidade"), rs.getString("nome"), rs.getString("senha"),rs.getString("unidade"));
						funcionarios.add(funcionario);
					} else {
						Funcionario funcionario = (Vendedor) new Vendedor(rs.getInt("matricula"), rs.getInt("unidade"), rs.getString("nome"), rs.getString("senha"), 100, 0.5);
						funcionarios.add(funcionario);
				}
			
			}
		} 
		catch (SQLException e) {

			throw new DbIntegrityException(e.getMessage());
			
		}
		finally {
			ConexaoJDBC.closeStatement(st);
			ConexaoJDBC.closeConnectiopn();
		}
	
		return new ArrayList<Funcionario>(funcionarios);
		
		
		
	}

	@Override
	public Funcionario getFuncionario(int id) {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			conn = ConexaoJDBC.getConnection();
			st = conn.prepareStatement("SELECT * FROM table_funcionarios where matricula = ? ");
			st.setInt(1, id);
			rs = st.executeQuery();
			if(rs.next()) {
				if (rs.getInt("funcao") == 1) {
					Funcionario funcionario = (Roteirizador) new Roteirizador(rs.getInt("matricula"), rs.getInt("unidade"), rs.getString("nome"), rs.getString("senha"),rs.getString("unidade"));
					return funcionario;
				} else {

					Funcionario funcionario = (Vendedor) new Vendedor(rs.getInt("matricula"), rs.getInt("unidade"), rs.getString("nome"), rs.getString("senha"), 100, 0.5);
					return funcionario;

				}
			}

			return null;

		} 
		catch (SQLException e) {
			e.printStackTrace();
			throw new DbIntegrityException(e.getMessage());

		}
		finally {
			ConexaoJDBC.closeStatement(st);
			ConexaoJDBC.closeConnectiopn();
		}

	}


	@Override
	public Funcionario getEstab(int id) {
		return null;
		
	}
	
	

	}
