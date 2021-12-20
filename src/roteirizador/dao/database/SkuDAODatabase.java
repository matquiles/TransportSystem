package roteirizador.dao.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import roteirizador.dao.SkuDAO;
import roteirizador.domain.SKU;

public class SkuDAODatabase implements SkuDAO {
	
	

	@Override
	public void save(SKU sku) {
		Connection conn = null;
		PreparedStatement st = null;
		
		try {
			conn = ConexaoJDBC.getConnection();
			st = conn.prepareStatement(
					"INSERT INTO table_sku (cod_sku, nome, paletizacao, valor) VALUES ((select max(cod_sku)+1 from table_sku),?,?,?)");
			
			//st.setInt(1, sku.getCodSKU());
			st.setString(1, sku.getNomeSku());
			st.setInt(2, sku.getPaletizacao());
			st.setDouble(3, sku.getValor());

			
			int rowsAffected = st.executeUpdate();
			
			System.out.println("Valores inseridos no BD. Linhas afetadas: " + rowsAffected);
			
			
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
	public void delete(SKU sku) {
		
		
		Connection conn = null;
		PreparedStatement st = null;
		
		try {
			conn = ConexaoJDBC.getConnection();
			st = conn.prepareStatement(
					"DELETE FROM table_sku WHERE cod_sku = ?");

			st.setInt(1, sku.getCodSKU());
			
			int rowsAffected = st.executeUpdate();
			
			System.out.println("Valores excluidos do BD. Linhas afetadas: " + rowsAffected);
			
			
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
	public List<SKU> getAll() {
		
		List<SKU> skus = new ArrayList <>();
		
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		
		try {
			conn = ConexaoJDBC.getConnection();
			st = conn.createStatement();
			rs = st.executeQuery("SELECT * FROM table_sku");
				

			while(rs.next()){
				SKU sku = new SKU(rs.getInt("cod_sku"), rs.getString("nome"), rs.getInt("paletizacao"), rs.getDouble("valor"));
				skus.add(sku);
			}
			
		} 
		catch (SQLException e) {

			throw new DbIntegrityException(e.getMessage());
			
		}
		finally {
			ConexaoJDBC.closeStatement(st);
			ConexaoJDBC.closeConnectiopn();
		}
	
		return new ArrayList<SKU>(skus);
	}
	
	
	@Override
	public SKU getSKU(int id) {
		
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			conn = ConexaoJDBC.getConnection();
			st = conn.prepareStatement("SELECT * FROM table_sku where cod_sku = ? ");
			st.setInt(1, id);
			
			rs = st.executeQuery();
			
			while(rs.next()){
				SKU sku = new SKU(rs.getInt("cod_sku"), rs.getString("nome"), rs.getInt("paletizacao"), rs.getDouble("valor"));
				return sku;
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


	public int qtdRegistros() {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		int retorno = 0;
		
		try {
			conn = ConexaoJDBC.getConnection();
			st = conn.createStatement();
			rs = st.executeQuery("SELECT COUNT(DISTINCT cod_sku) AS contagem FROM table_sku");
			
			rs.next();
			
			retorno = rs.getInt("contagem")+1;
		
			return retorno;

		} 
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			ConexaoJDBC.closeStatement(st);
			ConexaoJDBC.closeConnectiopn();
		}
	}
	
	

}
