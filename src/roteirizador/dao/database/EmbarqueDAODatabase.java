package roteirizador.dao.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import roteirizador.dao.DaoFactory;
import roteirizador.dao.EmbarqueDAO;
import roteirizador.domain.Embarque;
import roteirizador.domain.ItemPedido;
import roteirizador.domain.Pedido;
import roteirizador.validations.Sessao;

public class EmbarqueDAODatabase implements EmbarqueDAO {

	@Override
	public void save(Embarque embarque) {

		Connection conn = null;
		PreparedStatement st = null;
		
		
		try {
			conn = ConexaoJDBC.getConnection();
			st = conn.prepareStatement(
						"INSERT INTO table_embarques(cod_embarque, cod_roteirizador, cod_unidade, "
						+ "paletizacao, valor_total, veiculo, ocupacao, data_criacao) VALUES "
						+ "((select max(cod_embarque)+1 from table_embarques),?,?,?,?,?,?, ?)");
						
				st.setInt(1, Sessao.getInstance().getUsuario().getMatricula());
				st.setInt(2, Sessao.getInstance().getUsuario().getEstabelecimento());
				st.setDouble(3, embarque.getPaletizacaoTotal());
				st.setDouble(4, embarque.getValorTotal());
				st.setString(5, embarque.getVeiculo());
				st.setDouble(6, embarque.getOcupacao());
				st.setDate(7, new java.sql.Date(new Date().getTime()));
				
				st.executeUpdate();
				
				
				for(Pedido pedidos : embarque.getAll()){
					st = conn.prepareStatement(
						"INSERT INTO pedidos_embarques (cod_sql, cod_embarque, cod_pedido) "
						+ "VALUES ((select max(cod_sql)+1 from pedidos_embarques),?,?)");
				
					st.setInt(1, embarque.getCodEmbarque());
					st.setInt(2, pedidos.getCodPedido());
					
					st.executeUpdate();
					
				}
				

				}	
				catch (SQLException e) {
					e.printStackTrace();
					throw new DbException(e.getMessage());
				}
				finally {
					ConexaoJDBC.closeStatement(st);
					ConexaoJDBC.closeConnectiopn();
				}  
		
	}

	@Override
	public void deleteEmbarque(Embarque embarque) {
		
		Connection conn = null;
		PreparedStatement st = null;
		PreparedStatement stItem = null;
		
		try {
			conn = ConexaoJDBC.getConnection();
			stItem = conn.prepareStatement(
					"DELETE FROM pedidos_embarques WHERE cod_embarque = ?");
			stItem.setInt(1, embarque.getCodEmbarque());
			stItem.executeUpdate();
			
			
			st = conn.prepareStatement(
					"DELETE FROM table_embarques WHERE cod_embarque = ?");

			st.setInt(1, embarque.getCodEmbarque());
			
			st.executeUpdate();
			
			embarque.desalocarTodos();
			
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
	public List<Embarque> getAll() {
		List<Embarque> listaEmbarques = new ArrayList <>();

		Connection conn = null;
		PreparedStatement st = null;
		PreparedStatement stPedido = null;
		ResultSet rs = null;
		ResultSet rsPedido = null;
	

		try {
			conn = ConexaoJDBC.getConnection();
			st = conn.prepareStatement("SELECT * FROM table_embarques");
			rs = st.executeQuery();

			while(rs.next()){

					Embarque embarque = new Embarque(rs.getInt("cod_embarque"), 
							DaoFactory.getFuncionarioDAO().getFuncionario(rs.getInt("cod_roteirizador")) , 
							rs.getInt("cod_unidade"),rs.getDouble("paletizacao"),rs.getDouble("valor_total"), 
							rs.getString("veiculo"), rs.getDouble("ocupacao"), rs.getDate("data_criacao"));
							

					listaEmbarques.add(embarque);
				
				stPedido = conn.prepareStatement("SELECT * FROM pedidos_embarques WHERE cod_embarque = ?");
				stPedido.setInt(1, embarque.getCodEmbarque());
				rsPedido = stPedido.executeQuery();
			
		
				while (rsPedido.next()) {
					Pedido pd = DaoFactory.getOrderDAO().getPedido(rsPedido.getInt("cod_pedido"));
					embarque.addPedidoSQL(pd);
							
					
				}
				ConexaoJDBC.closeStatement(stPedido);
				ConexaoJDBC.closeConnectiopn();
				
			}
			
			
		} 
		catch (SQLException e) {
			e.printStackTrace();
			throw new DbIntegrityException(e.getMessage());
		}
		finally {
			ConexaoJDBC.closeStatement(st);
			ConexaoJDBC.closeConnectiopn();
		}
		return new ArrayList<Embarque>(listaEmbarques);

	}

	@Override
	public Embarque getEmbarque(int id) {
		Connection conn = null;
		PreparedStatement st = null;
		PreparedStatement stPedido = null;
		ResultSet rs = null;
		ResultSet rsPedido = null;

		try {
			conn = ConexaoJDBC.getConnection();
			st = conn.prepareStatement("SELECT * FROM table_embarques where cod_embarque = ?");
			st.setInt(1, id);
			rs = st.executeQuery();
			rs.next();
			
			Embarque embarque = new Embarque(rs.getInt("cod_embarque"), 
					DaoFactory.getFuncionarioDAO().getFuncionario(rs.getInt("cod_roteirizador")) , 
					rs.getInt("cod_unidade"),rs.getDouble("paletizacao"),rs.getDouble("valor_total"), 
					rs.getString("veiculo"), rs.getDouble("ocupacao"), rs.getDate("data_criacao"));


			stPedido = conn.prepareStatement("SELECT * FROM pedidos_embarques WHERE cod_embarque = ?");
			stPedido.setInt(1, embarque.getCodEmbarque());
			rsPedido = stPedido.executeQuery();


			while (rsPedido.next()) {
				Pedido pd = DaoFactory.getOrderDAO().getPedido(rsPedido.getInt("cod_pedido"));
				embarque.addPedidoSQL(pd);
				

			}
			ConexaoJDBC.closeStatement(stPedido);
			ConexaoJDBC.closeConnectiopn();
			
			return embarque;
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
	
	public int ultimoEmbarqueCriado() {

		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		int retorno = 1000;


		try {
			conn = ConexaoJDBC.getConnection();
			st = conn.createStatement();
			rs = st.executeQuery("SELECT * FROM table_embarques");

			while(rs.next()) {
				retorno++;
			}

			return retorno+1;


		} 
		catch (SQLException e) {
			throw new DbIntegrityException(e.getMessage());
		}
		finally {
			ConexaoJDBC.closeStatement(st);
			ConexaoJDBC.closeConnectiopn();
		}
	}

	public void savePedido(Embarque embarque, Pedido pedido) {

		Connection conn = null;
		PreparedStatement st = null;


		try {
			conn = ConexaoJDBC.getConnection();
			st = conn.prepareStatement(
					"INSERT INTO pedidos_embarques (cod_sql, cod_embarque, cod_pedido) "
					+ "VALUES ((select max(cod_sql)+1 from pedidos_embarques),?,?)");
			
				st.setInt(1, embarque.getCodEmbarque());
				st.setInt(2, pedido.getCodPedido());
				
				st.executeUpdate();
				
				embarque.addPedido(pedido);
				
				
				
				
			
		}	
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			ConexaoJDBC.closeStatement(st);
			ConexaoJDBC.closeConnectiopn();
		}  

	}
	
	public void deleteItemPedido(Embarque embarque, Pedido pedido) {
		Connection conn = null;
		PreparedStatement st = null;


		try {
			conn = ConexaoJDBC.getConnection();
			st = conn.prepareStatement(
					"DELETE FROM pedidos_embarques WHERE cod_embarque = ? AND cod_pedido = ?");

			st.setInt(1, embarque.getCodEmbarque());
			st.setInt(2, pedido.getCodPedido());
			st.executeUpdate();
			
			embarque.removerPedido(pedido);
			
			
		}	
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			ConexaoJDBC.closeStatement(st);
			ConexaoJDBC.closeConnectiopn();
		}  

		
	}
	
	public void atualizarEmbarque(Embarque embarque) {

		Connection conn = null;
		PreparedStatement st = null;

		try {
			conn = ConexaoJDBC.getConnection();
			st = conn.prepareStatement(
					"UPDATE table_embarques SET valor_total = ?, paletizacao = ?,  veiculo = ? , ocupacao = ? WHERE cod_embarque = ?");

			st.setDouble(1, embarque.getValorTotal());
			st.setDouble(2, embarque.getPaletizacaoTotal());
			st.setString(3, embarque.getVeiculo());
			st.setDouble(4, embarque.getOcupacao());
			st.setInt(5, embarque.getCodEmbarque());
			

			st.executeUpdate();

			System.out.println("\nEmbarque atualizado com sucesso!");

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
