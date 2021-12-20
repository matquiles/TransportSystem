package roteirizador.dao.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import roteirizador.dao.ClientsDAO;
import roteirizador.domain.Client;


public class ClientDAODatabase implements ClientsDAO{

	@Override
	public void save(Client cliente) {
		Connection conn = null;
		PreparedStatement st = null;
		
		try {
			conn = ConexaoJDBC.getConnection();
			st = conn.prepareStatement(
					"INSERT INTO table_client (codcliente, nome, endereco, cidade, estado, unidade) VALUES ((select max(codcliente)+1 from table_client),?,?,?,?,?)");
			
			//st.setInt(1, sku.getCodSKU());
			st.setString(1, cliente.getNomecliente());
			st.setString(2, cliente.getEndereco());
			st.setString(3, cliente.getCidade());
			st.setString(4, cliente.getEstado());
			st.setInt(5, cliente.getUnidade());
			
			
			int rowsAffected = st.executeUpdate();
			
			System.out.println("Cliente inserido no BD. Linhas afetadas: " + rowsAffected);
			
			
		} 
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			ConexaoJDBC.closeStatement(st);
			ConexaoJDBC.closeConnectiopn();
		}
		
	}

	@Override
	public void delete(Client cliente) {
		Connection conn = null;
		PreparedStatement st = null;
		
		try {
			conn = ConexaoJDBC.getConnection();
			st = conn.prepareStatement(
					"DELETE FROM table_client WHERE codcliente = ?");

			st.setInt(1, cliente.getCodcliente());
			
			int rowsAffected = st.executeUpdate();
			
			System.out.println("Cliente excluido do BD. Linhas afetadas: " + rowsAffected);
			
			
		} 
		catch (SQLException e) {
			throw new DbIntegrityException(e.getMessage());
		}
		finally {
			ConexaoJDBC.closeStatement(st);
			ConexaoJDBC.closeConnectiopn();
		}
		
	}

	@Override
	public List<Client> getAll() {
		List<Client> clientes = new ArrayList <>();
		
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		
		try {
			conn = ConexaoJDBC.getConnection();
			st = conn.createStatement();
			rs = st.executeQuery("SELECT * FROM table_client");
				

			while(rs.next()){
				Client cliente = new Client(rs.getInt("codcliente"), rs.getString("nome"), rs.getString("endereco"), rs.getString("cidade"), rs.getString("estado"), rs.getInt("unidade"));
				clientes.add(cliente);
			}
			
		} 
		catch (SQLException e) {

			throw new DbIntegrityException(e.getMessage());
			
		}
		finally {
			ConexaoJDBC.closeStatement(st);
			ConexaoJDBC.closeConnectiopn();
		}
	
		return new ArrayList<Client>(clientes);
	}

	@Override
	public Client getClient(int id) {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			conn = ConexaoJDBC.getConnection();
			st = conn.prepareStatement("SELECT * FROM table_client where codcliente = ? ");
			st.setInt(1, id);
			
			rs = st.executeQuery();
			
			while(rs.next()){
				Client cliente = new Client(rs.getInt("codcliente"),rs.getString("nome"), rs.getString("endereco"), rs.getString("cidade"), rs.getString("estado"), rs.getInt("unidade"));
				return cliente;
			}
			
			return null;
			
		} 
		catch (SQLException e) {

			throw new DbIntegrityException(e.getMessage());
			
		}
		finally {
			ConexaoJDBC.closeStatement(st);
			ConexaoJDBC.closeConnectiopn();
		}
			
	}
	

}
